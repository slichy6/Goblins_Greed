package main;

import characters.*;
import object.OBJ_Heart;



import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {

        GamePanel gp;
        Graphics2D g2;
        Font maruMonica;
        BufferedImage heart_full, heart_half, heart_blank;
        public boolean meassgeOn = false;
        ArrayList<String> message = new ArrayList<>();
        ArrayList<Integer> messageCounter = new ArrayList<>();
        public boolean gameFinished = false;
        public String currentDialogue = "";
        public int commandNum = 0;
        public int titleScreenState = 0; // State 0 is the first screen, 1 is the second, etc.
        public int slotCol = 0;
        public int slotRow = 0;
        public UI(GamePanel gp) {
            this.gp = gp;

            try {
                InputStream is = getClass().getResourceAsStream("/images/font/x12y16pxMaruMonica.ttf");
                maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }

            // Create the HUD
            Characters heart = new OBJ_Heart(gp);
            heart_full = heart.image1;
            heart_half = heart.image2;
            heart_blank = heart.image3;

        }

        public void addMessage(String text) {
//            message = text;
//            messageOn = true;

            message.add(text);
            messageCounter.add(0);
        }


        public void draw(Graphics2D g2) {

            this.g2 = g2;

            g2.setFont(maruMonica);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setColor(Color.white);
            // title state

            if(gp.gameState == gp.titleState) {
                drawTitleScreen();
            }
            // play state
            if(gp.gameState == gp.playState) {
                // TODO playstate stuff
                drawPlayerLife();
                drawMessage();
            }

            // pause state
            if(gp.gameState == gp.pauseState) {
                // TODO pause state stuff
                drawPlayerLife();
                drawPauseScreen();
            }

            // dialogue state
            if(gp.gameState == gp.dialogueState) {
                // TODO dialogue state stuff
                drawPlayerLife();
                drawDialogueScreen();
            }

            // stat screen
            if(gp.gameState == gp.characterState){
                drawStatScreen();
                drawInventory();
            }
        }

        public void drawPlayerLife() {
            int x = gp.tileSize/2;
            int y = gp.tileSize/2;
            int i = 0;


            // Draw empty heart
            while(i < gp.player.maxLife/2) {
                g2.drawImage(heart_blank, x, y, null);
                i++;
                x += gp.tileSize;
            }

            // reset

            x = gp.tileSize/2;
            y = gp.tileSize/2;
            i = 0;

            // draw current life
            while(i < gp.player.life){
                g2.drawImage(heart_half, x, y, null);
                i++;
                if(i < gp.player.life) {
                    g2.drawImage(heart_full,x,y,null);
                }
                i++;
                x += gp.tileSize;
            }

        }
        public void drawMessage(){

            int messageX = gp.tileSize;
            int messageY = gp.tileSize*8;
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

            for(int i = 0; i < message.size(); i++){

                if(message.get(i) != null){
                    g2.setColor(Color.black);
                    g2.drawString(message.get(i), messageX+2, messageY+2);
                    g2.setColor(Color.white);
                    g2.drawString(message.get(i), messageX, messageY);

                    int counter = messageCounter.get(i) + 1; // same as messageCounter++ but can't do what with a basic method
                    messageCounter.set(i, counter);
                    messageY += 50;

                    if(messageCounter.get(i) > 90) {
                        message.remove(i);
                        messageCounter.remove(i);
                    }
                }
            }
        }
        public void drawTitleScreen() {


           // if(titleScreenState == 0) {
                g2.setColor(new Color(0,0,0));
                g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

                // title name
                g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
                String text = "Goblin's Greed";
                int x = getXforCenteredText(text);
                int y = gp.tileSize*3;

                // Shadow
                g2.setColor(Color.gray);
                g2.drawString(text, x+5, y+5); // moves the title over and down 5 spaces allowing for a shadow

                // com.ignore.Main Color
                g2.setColor(Color.white);
                g2.drawString(text, x, y);

                // Goblin Image
                x =  gp.screenWidth/2 - (gp.tileSize*2)/2;
                y += gp.tileSize*2;
                g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

                // Menu
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

                text = "New Game";
                x = getXforCenteredText(text);
                y += gp.tileSize*3.5;
                g2.drawString(text,x, y);

                if(commandNum == 0) {
                    g2.drawString(">", x-gp.tileSize, y);
                }

                text = "Continue";
                x = getXforCenteredText(text);
                y += gp.tileSize;
                g2.drawString(text,x, y);

                if(commandNum == 1) {
                    g2.drawString(">", x-gp.tileSize, y);
                }

                text = "Quit Game";
                x = getXforCenteredText(text);
                y += gp.tileSize;
                g2.drawString(text,x, y);

                if(commandNum == 2) {
                    g2.drawString(">", x-gp.tileSize, y);
                }
            //}
//            else if(titleScreenState == 1) {
//
//                //Starting Screen
//                g2.setColor(Color.white);
//                g2.setFont(g2.getFont().deriveFont(42F));
//
//                String text = "What do you call yourself?";
//                int x = getXforCenteredText(text);
//                int y = gp.tileSize*3;
//                g2.drawString(text, x, y);
//
//                // TODO name enter here
//                text = "Filler info";
//                y += gp.tileSize*3;
//                g2.drawString(text, x,y );
//                if (commandNum == 0) {
//                    g2.drawString(">", x-gp.tileSize, y);
//                }
//            }
//
        }

        public void drawPauseScreen() {

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
            String text = "PAUSED";
            int x = getXforCenteredText(text);
            int y = gp.screenHeight/2;

            g2.drawString(text, x, y);
        }

        public void drawDialogueScreen() {

            // dialogue window

            int x = gp.tileSize*2;
            int y = gp.tileSize/2;
            int width = gp.screenWidth - (gp.tileSize*4);
            int height = gp.tileSize*4;
            drawSubWindow(x, y, width, height);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
            x += gp.tileSize;
            y += gp.tileSize;

            // jpanel has to have special code for line breaks
            for(String line : currentDialogue.split("\n")) {
                g2.drawString(line, x, y);
                y += 40;
            }
        }

        public void drawStatScreen() {

            // create the stat frame
            final int frameX = gp.tileSize*2;
            final int frameY = gp.tileSize;
            final int frameWidth = gp.tileSize*5;
            final int frameHeight = gp.tileSize*10;
            drawSubWindow(frameX,frameY,frameWidth,frameHeight);

            // stat text
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(32F));

            int textX = frameX + 20;
            int textY = frameY + gp.tileSize;
            final int lineHeight = 36;

            // STAT Names
            g2.drawString("Level", textX, textY);
            textY += lineHeight;
            g2.drawString("Life", textX, textY);
            textY += lineHeight;
            g2.drawString("Strength", textX, textY);
            textY += lineHeight;
            g2.drawString("Dexterity", textX, textY);
            textY += lineHeight;
            g2.drawString("Attack", textX, textY);
            textY += lineHeight;
            g2.drawString("Defense", textX, textY);
            textY += lineHeight;
            g2.drawString("Current EXP", textX, textY);
            textY += lineHeight;
            g2.drawString("Next Level", textX, textY);
            textY += lineHeight;
            g2.drawString("Gil", textX, textY);
            textY += lineHeight +20;
            g2.drawString("Weapon", textX, textY);
            textY += lineHeight +15;
            g2.drawString("Armor", textX, textY);
            textY += lineHeight;

            // STAT Values
            int tailX = (frameX + frameWidth) - 30; // location to the right of the frame
            // Reset Y axis
            textY = frameY + gp.tileSize;
            String value;

            // each value will increase or decrease depending on actions/equipped items/level
            value = String.valueOf(gp.player.level);
            textX = getXforAlignToRightText(value, tailX);
            g2.drawString(value, textX, textY);
            textY += lineHeight;

            value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
            textX = getXforAlignToRightText(value, tailX);
            g2.drawString(value, textX, textY);
            textY += lineHeight;

            value = String.valueOf(gp.player.strength);
            textX = getXforAlignToRightText(value, tailX);
            g2.drawString(value, textX, textY);
            textY += lineHeight;

            value = String.valueOf(gp.player.dexterity);
            textX = getXforAlignToRightText(value, tailX);
            g2.drawString(value, textX, textY);
            textY += lineHeight;

            value = String.valueOf(gp.player.attack);
            textX = getXforAlignToRightText(value, tailX);
            g2.drawString(value, textX, textY);
            textY += lineHeight;

            value = String.valueOf(gp.player.defense);
            textX = getXforAlignToRightText(value, tailX);
            g2.drawString(value, textX, textY);
            textY += lineHeight;

            value = String.valueOf(gp.player.exp);
            textX = getXforAlignToRightText(value, tailX);
            g2.drawString(value, textX, textY);
            textY += lineHeight;

            value = String.valueOf(gp.player.nextLevelExp);
            textX = getXforAlignToRightText(value, tailX);
            g2.drawString(value, textX, textY);
            textY += lineHeight;

            value = String.valueOf(gp.player.gil);
            textX = getXforAlignToRightText(value, tailX);
            g2.drawString(value, textX, textY);
            textY += lineHeight;

            // draws the image of the equipped items
            g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY-14, null);
            textY += gp.tileSize;
            g2.drawImage(gp.player.currentArmor.down1, tailX - gp.tileSize, textY-11, null);



        }
        public void drawInventory(){

            // inventory window
            int frameX = gp.tileSize * 12;
            int frameY = gp.tileSize;
            int frameWidth = gp.tileSize * 6;
            int frameHeight = gp.tileSize *5;
            drawSubWindow(frameX, frameY, frameWidth, frameHeight);

            // inventory slots
            final int slotXstart = frameX + 20;
            final int slotYstart = frameY + 20;
            int slotX = slotXstart;
            int slotY = slotYstart;
            int slotSize = gp.tileSize+3;

            // draw player inventory items
            for(int i =0; i < gp.player.inventory.size(); i++){

                // Hightlights the currently equipped items in the inventory
                if(gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentArmor){
                    g2.setColor(new Color(173,216,230));
                    g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
                }
                // draws an image of what was picked up in the overworld
                g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);

                slotX += slotSize;

                if(i == 4 || i == 9 || i == 14) {
                    slotX = slotXstart;
                    slotY += slotSize;
                }
            }

            // inventory select cursor
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            // draw the select cursor
            g2. setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2. drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // Description window
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize*3;


            // description text
            int textX = dFrameX +20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(28f));
            int itemIndex = getItemIndexOfSlot();

            if(itemIndex < gp.player.inventory.size()) {

                drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);

                for(String line: gp.player.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }

        }

        public int getItemIndexOfSlot(){
            int itemIndex = slotCol + (slotRow*5);
            return itemIndex;
        }
        public void drawSubWindow (int x, int y, int width, int height) {

            Color c = new Color(0,0,0,210);
            g2.setColor(c);
            g2.fillRoundRect(x, y, width, height,35, 35);

            c = new Color(255, 255, 255);
            g2.setColor(c);
            g2.setStroke(new BasicStroke(5));
            g2.drawRoundRect(x+5, y+5, width-10, height-10, 25,25);


        }

        public int getXforCenteredText(String text) {

            int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = gp.screenWidth/2 - length/2;
            return x;
        }

        public int getXforAlignToRightText(String text, int tailX) {

            int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = tailX - length;
            return x;
        }
}
