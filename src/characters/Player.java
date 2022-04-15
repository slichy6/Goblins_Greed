package characters;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_CMail;
import object.OBJ_Katana;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Characters{

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCanceled = false;


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

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues(){
        //start position
        worldX = gp.tileSize * 2;
        worldY = gp.tileSize * 2;
        speed = 4;
        direction = "down";

        // Player Stats
        level = 1;
        maxLife = 6;
        life = maxLife;
        strength = 1; // attack value
        dexterity = 1; // defense level
        exp = 0;
        nextLevelExp = 5;
        gil = 0;
        currentWeapon = new OBJ_Katana(gp);
        currentArmor = new OBJ_CMail(gp);
        attack = getAttack(); // factor of strength and equipped weapon
        defense = getDefense(); // factor of dex and equipped armor
    }

    public int getAttack() {
        return attack = strength * currentWeapon.attackStrength;
    }

    public int getDefense() {
        return defense = dexterity * currentArmor.defenseLevel;
    }

    public void getPlayerImage(){

        up1 = setup("/images/player/goblin_up1", gp.tileSize, gp.tileSize);
        up2 = setup("/images/player/goblin_up2", gp.tileSize, gp.tileSize);
        down1 = setup("/images/player/goblin_dn1", gp.tileSize, gp.tileSize);
        down2 = setup("/images/player/goblin_dn2", gp.tileSize, gp.tileSize);
        right1 = setup("/images/player/goblin_rt1", gp.tileSize, gp.tileSize);
        right2 = setup("/images/player/goblin_rt2", gp.tileSize, gp.tileSize);
        left1 = setup("/images/player/goblin_lf1", gp.tileSize, gp.tileSize);
        left2 = setup("/images/player/goblin_lf2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage(){

        // TODO Need to find good images for attacking here
        // the pixel size for an attack has 2 times the width or height of the walking sprite depending on facing side
        attackUp1 = setup("/images/player/goblin_up1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/images/player/goblin_up2", gp.tileSize, gp.tileSize *2);
        attackDown1 = setup("/images/player/goblin_dn1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/images/player/goblin_dn2", gp.tileSize, gp.tileSize*2);
        attackRight1 = setup("/images/player/goblin_rt1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setup("/images/player/goblin_rt2", gp.tileSize*2, gp.tileSize);
        attackLeft1 = setup("/images/player/goblin_lf1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setup("/images/player/goblin_lf2", gp.tileSize*2, gp.tileSize);
    }

    public void update() {

        if (attacking == true) {
            attacking();
        } else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // check obj collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // check npc collision
            int npcIndex = gp.cChecker.checkCharacter(this, gp.npc);
            interactNPC(npcIndex);

            // check monster collision
            int monsterIndex = gp.cChecker.checkCharacter(this, gp.monster);
            contactMonster(monsterIndex);

            // check event status
            gp.eHandler.checkEvent();

            //if collision is false, then player can move
            if (collisionOn == false && keyH.enterPressed == false) {

                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                }
            }

            if(keyH.enterPressed == true && attackCanceled == false){
                //TODO figure out why this soundFX doesn't work
                gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
            }
            attackCanceled = false;
            gp.keyH.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            standCounter++;
            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }
        if (invincible == true) {  // TODO TEST THIS
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void attacking() {
        spriteCounter++;

        if(spriteCounter <= 5) {
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            // checking hit detection based on where the weapon sprite is
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // adjust to see attack area
            switch (direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }

            // attack area becomes solid
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // check if the monster gets hit
            int monsterIndex = gp.cChecker.checkCharacter(this,gp.monster);
            damageMonster(monsterIndex);

            // restores the original hit box
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void pickUpObject (int i) {

        if (i != 999) {
            System.out.println("Touching an object");
        }
    }

    public void interactNPC(int i) {

        if (gp.keyH.enterPressed == true) {

            if (i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void contactMonster(int i){
        if(i != 999){
            if(invincible = false) {
                gp.playSE(6);
                int damage = gp.monster[i].attack - defense;
                if(damage < 0) {
                    damage =0;
                }
                life -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i){
        if(i != 999) {
            if(gp.monster[i].invincible == false){
                gp.playSE(5);

                int damage = attack - gp.monster[i].defense;
                if(damage < 0) {
                    damage =0;
                }
                gp.monster[i].life -= damage;
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();;

                if(gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                }
            }
        }
    }

    public void draw (Graphics2D g2){

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if(attacking == false) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                if(attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                }
                break;

            case "down":
                if(attacking == false) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                if(attacking == true) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                break;

            case "right":
                if(attacking == false) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                if(attacking == true) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                break;

            case "left":
                if(attacking == false) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                if(attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
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

        if(invincible == true){
            // make our player look opaque after being damaged 1 second
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // reset player opacity
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


    }
}
