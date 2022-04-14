package characters;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Characters{

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    int standCounter = 0;
    boolean moving = false;
    int pixelCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);


        this.keyH = keyH;

        screenX = 100;
        screenY = 100;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        //start position
        worldX = gp.tileSize * 2;
        worldY = gp.tileSize * 2;
        speed = 4;
        direction = "down";

        // Player Stats
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage(){

        up1 = setup("/images/player/goblin_up1");
        up2 = setup("/images/player/goblin_up2");
        down1 = setup("/images/player/goblin_dn1");
        down2 = setup("/images/player/goblin_dn2");
        right1 = setup("/images/player/goblin_rt1");
        right2 = setup("/images/player/goblin_rt2");
        left1 = setup("/images/player/goblin_lf1");
        left2 = setup("/images/player/goblin_lf2");
    }

    public void update(){

        if(moving == false) {
            if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
                if (keyH.upPressed == true) {
                    direction = "up";
                } else if (keyH.downPressed == true) {
                    direction = "down";
                } else if (keyH.rightPressed == true) {
                    direction = "right";
                } else if (keyH.leftPressed == true) {
                    direction = "left";
                }

                moving = true;

                //CHECK TILE COLLISION
                collisionOn = false;
                gp.cChecker.checkTile(this);

                // check obj collision
                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);
            } else {
                standCounter++;
                if (standCounter == 20) {
                    spriteNum = 1;
                    standCounter = 0;
                }
            }
        }
            if(moving == true){


                // check npc collision
                int npcIndex = gp.cChecker.checkCharacter(this, gp.npc);
                interactNPC(npcIndex);

                // check monster collision
                int monsterIndex = gp.cChecker.checkCharacter(this, gp.monster);
                contactMonster(monsterIndex);

                // check event status
                gp.eHandler.checkEvent();

                gp.keyH.enterPressed = false;

                //if collision is false, then player can move
                if(collisionOn == false){

                    switch(direction){
                        case "up": worldY -= speed;
                            break;

                        case "down": worldY += speed;
                            break;

                        case "right": worldX += speed;
                            break;

                        case "left": worldX -= speed;
                            break;
                    }
                }
                spriteCounter++;

                if(spriteCounter > 20){
                    if(spriteNum == 1){
                        spriteNum = 2;
                    } else if(spriteNum == 2){
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }

                pixelCounter += speed;

                if(pixelCounter == 48) {
                    moving = false;
                    pixelCounter = 0;
                }
            }

            if(invincible == true){  // TODO TEST THIS
                invincibleCounter++;
                if(invincibleCounter > 60) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
        }

    public void pickUpObject (int i) {

        if (i != 999) {
            System.out.println("Touching an object");
        }
    }

    public void interactNPC(int i) {
        if(i != 999) {

            if(gp.keyH.enterPressed){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void contactMonster(int i){
        if(i != 999){
            if(invincible = false) {
                life -= 1;
                invincible = true;
            }
        }
    }

    public void draw (Graphics2D g2){
            // g2.setColor(Color.GREEN);
            // g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;

            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;

            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;

            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
        }

        int x = screenX;
        int y = screenY;

        if (screenX > worldX) {
            x = worldX;
        }

        if (screenY > worldY) {
            y = worldY;
        }
        int rightOffset = gp.screenWidth - screenX;
        if(rightOffset > gp.worldWidth - worldX) {
            x = gp.screenWidth - (gp.worldWidth - worldX);
        }
        int bottomOffset = gp.screenHeight - screenY;
        if(bottomOffset > gp.worldHeight - worldY){
            y = gp.screenHeight - (gp.worldHeight - worldY);
        }

        g2.drawImage(image, x, y, null);
    }
}
