package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener{

    GamePanel gp;
    public boolean upPressed, downPressed, rightPressed, leftPressed, enterPressed;


    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e){

    }
    @Override
    public void keyPressed(KeyEvent e){

        int code = e.getKeyCode();

        // Title State
        if(gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.gameState = gp.playState;
                    gp.playMusic(1);
                }
                if(gp.ui.commandNum == 1) {
                    // TODO later continue game
                    System.out.println("Continue Pressed");
                }
                if(gp.ui.commandNum == 2) {
                    // Exit Game
                    System.exit(0);
                }
            }

        }

        // Play State
        if(gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
        }

        // Pause State
        else if (gp.gameState == gp.pauseState) {
            if(code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }

        // dialogue State
        else if(gp.gameState == gp.dialogueState) {
            if(code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_UP){
            upPressed = false;
        }
        if(code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if(code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }
    }
}


