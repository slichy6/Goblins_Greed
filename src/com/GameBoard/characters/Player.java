package com.GameBoard.characters;
import com.GameBoard.main.GamePanel;
import com.GameBoard.main.KeyHandler;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Objects;

import javax.imageio.ImageIO;

public class Player extends Characters {
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        //start position
        x = 100;
        y = 100;
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
            left2 = ImageIO.read(getClass().getResourceAsStream("images/player/goblin_lf2.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update(){

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed ){
            if(keyH.upPressed == true){
                direction = "up";
                y -= speed;
            }
            else if(keyH.downPressed == true){
                direction = "down";
                y += speed;
            }
            else if(keyH.rightPressed == true){
                direction = "right";
                x += speed;
            }
            else if(keyH.leftPressed == true){
                direction = "left";
                x -= speed;
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

    public void repaint(Graphics2D g2){
        // g2.setColor(Color.GREEN);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);
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
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
