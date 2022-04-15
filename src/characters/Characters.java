package characters;

import main.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Characters {
    GamePanel gp;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image1, image2, image3;
    public Rectangle solidArea = new Rectangle(1,2,46,46);
    public Rectangle attackArea = new Rectangle(0,0,0,0); // default, adjusted in the child class
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    String[] dialogues = new String[40];

    // state of the world
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collision = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;

    // some action counters
    public int spriteCounter = 0;
    public int invincibleCounter = 0;
    public int actionLockCounter = 0;
    public int dyingCounter = 0;
    public int hpBarCounter = 0;

    // Character Stats
    public String name;
    public int speed;
    public int maxLife;
    public int life;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int gil; // Final Fantasy money is called 'gil'
    public Characters currentWeapon;
    public Characters currentArmor;

    // Weapon and Item attributes
    // TODO adjust these levels based on how difficult we want the game - monsters need higher attributes too
    public int attackStrength;
    public int defenseLevel;
    public String description = "";

    // what kind of object are we dealing with based on integer index
    public int type; //0 player 1 npc 2 monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_weapon = 3;
    public final int type_armor = 4;
    public final int type_consumable = 5;



    public Characters(GamePanel gp){
        this.gp = gp;
    }

    public BufferedImage setup(String imagePath, int width, int height) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaledImage(image, width, height);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void setAction() {}
    public void damageReaction() {}
    public void update() {

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkCharacter(this, gp.npc);
        gp.cChecker.checkCharacter(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPLayer(this);

        if(this.type == type_monster && contactPlayer == true) {
            if(gp.player.invincible == false) {
                // damage
                gp.playSE(6);

                int damage = attack - gp.player.defense;
                if(damage < 0) {
                    damage =0;
                }
                gp.player.life -= damage;
                gp.player.invincible = true;
            }
        }
        if(collisionOn == false) {
            switch(direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }

            spriteCounter++;
            if(spriteCounter > 12) {
                if(spriteNum == 1) {
                    spriteNum =2;
                }else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

            if (invincible == true) {  // TODO TEST THIS
                invincibleCounter++;
                if (invincibleCounter > 40) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize * 15 > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize * 15 < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize * 15 > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize * 15 < gp.player.worldY + gp.player.screenY){

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

            // Monster Health Bar
            if(type == 2 && hpBarOn == true){
                // incrementing the amount of health displayed by reducing color inside HP bar every time monster loses some hp from attack
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;
                // border of the health bar
                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY - 13, gp.tileSize+2, 12);

                // health bar
                g2.setColor(new Color(34, 139,34));
                g2.fillRect(screenX, screenY - 12, (int)hpBarValue, 10);

                hpBarCounter++;

                // if you wait (number of seconds) after attack, monster hp disappears
                if(hpBarCounter > 200) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if(invincible == true){
                hpBarOn = true;
                hpBarCounter = 0;
                // make our player look opaque after being damaged 1 second
                changeAlpha(g2,0.4F);
            }
            if(dying == true) {
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            changeAlpha(g2,1F);
        }

    }
    // This is basically a blinking animation so you can see the monster or player dying
    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;

        int i = 5;

        if(dyingCounter <= i){
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > i && dyingCounter <= i*2){
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > i*2 && dyingCounter <= i*3){
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > i*3 && dyingCounter <= i*4){
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > i*4 && dyingCounter <= i*5){
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > i*5 && dyingCounter <= i*6){
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > i*6 && dyingCounter <= i*7){
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > i*7 && dyingCounter <= i*8){
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > i*8) {
            dying = false;
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public void speak() {

        if(dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }

    }

}

