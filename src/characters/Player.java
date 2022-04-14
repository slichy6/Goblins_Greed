package characters;

import main.GamePanel;
import main.KeyHandler;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Player extends Characters{
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasTreasure = 0;
    public int hasWeapon = 0;
    public int hasArmor = 0;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
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
    }

    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/images/player/goblin_up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/images/player/goblin_up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/images/player/goblin_dn1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/images/player/goblin_dn2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/images/player/goblin_rt1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/images/player/goblin_rt2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/images/player/goblin_lf1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/images/player/goblin_lf2.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update(){

        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true){
            if(keyH.upPressed == true){
                direction = "up";
            }

            else if(keyH.downPressed == true){
                direction = "down";
            }

            else if(keyH.rightPressed == true){
                direction = "right";
            }

            else if(keyH.leftPressed == true){
                direction = "left";
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.checker.checkTile(this);

            //check object collision
            int objIndex = gp.checker.checkObject(this, true);
            pickUpObject(objIndex);

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
        }
        
    }

    public void pickUpObject(int i){

        if(i != 999){
            String objectName = gp.obj[i].name;

            switch(objectName){
                case "gold_coins":
                    hasTreasure++;
                    gp.obj[i] = null;
                    System.out.println("Treasure: " + hasTreasure);
                    break;

                case "chain_mail":
                    hasArmor++;
                    gp.obj[i] = null;
                    System.out.println("Armor: " + hasArmor);
                    break;

                case "axe":
                    hasWeapon++;
                    gp.obj[i] = null;
                    System.out.println("Weapon: " + hasWeapon);
                    break;
            }
        }
    }

    public void repaint(Graphics2D g2){

        BufferedImage image = null;

        switch(direction){
            case "up":
                if(spriteNum == 1){
                   image = up1; 
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;

            case "down":
            if(spriteNum == 1){
                image = down1; 
             }
             if(spriteNum == 2){
                 image = down2;
             }
                break;

            case "right":
                if(spriteNum == 1){
                   image = right1; 
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;

            case "left":
                if(spriteNum == 1){
                   image = left1; 
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;            
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
