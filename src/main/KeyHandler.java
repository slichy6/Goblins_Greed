package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener{

    GamePanel gp;
    public boolean upPressed, downPressed, rightPressed, leftPressed, enterPressed;
    //Debugging
    boolean showDebugText = false;



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
            titleState(code);
        }

        // Play State
        else if(gp.gameState == gp.playState) {
            playState(code);
        }

        // Pause State
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }

        // dialogue State
        else if(gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }
        // character stat screen
        else if(gp.gameState == gp.characterState){
            characterState(code);
        }
        else if(gp.gameState == gp.nameEnterState){
            enterNameState(code);
        }
    }
    public void titleState(int code) {

        if (code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        }
        if (code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.nameEnterState;
//                gp.playMusic(0);
            }
            if (gp.ui.commandNum == 1) {
                // TODO later continue game
                System.out.println("Continue Pressed");
            }
            if (gp.ui.commandNum == 2) {
                // Exit Game
                System.exit(0);
            }
        }
    }

    public void enterNameState(int code){
        if (code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
        }
        if (code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
//                gp.stopMusic();
//                gp.playMusic(1);
            }
            if (gp.ui.commandNum ==1) {
                gp.gameState = gp.titleState;
                gp.stopMusic();
            }
        }
    }
    public void playState(int code){
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
        if (code == KeyEvent.VK_I){
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        // DEBUGGING and showing locations of tiles x,y axis
        if(code == KeyEvent.VK_T) {
            if(showDebugText == false) {
                showDebugText = true;
            }
            else if(showDebugText == true) {
                showDebugText = false;
            }
        }
        if(code == KeyEvent.VK_Y) {
            gp.tileM.loadMap("/images/maps/world1.txt");
            System.out.println("Resetting");
        }
    }

    public void pauseState(int code){
        if(code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }

    public void dialogueState(int code){
        if(code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code){
        if(code == KeyEvent.VK_I) {
            gp.gameState = gp.playState;
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



