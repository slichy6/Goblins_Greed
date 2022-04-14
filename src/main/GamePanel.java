package main;
import javax.swing.JPanel;

import characters.*;
import object.SuperObject;
import tile.*;


import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //screen settings
    final int orginalTileSize = 16;  //16x16 tile
    final int scale = 3;

    public final int tileSize = orginalTileSize * scale;  //48 x 48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;  //768 pixels
    public final int screenHeight = tileSize * maxScreenRow;  //576 pixels

    //WORLD settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS setting
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);

    Sound sound = new Sound();
    Thread gameThread;  //starts and stops game clock

    // Character and Objects
    public Player player = new Player(this, keyH);
    public SuperObject[] obj = new SuperObject[10];
    public Characters[] npc = new Characters[10];

    // Game States
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
        stopMusic();
        gameState = playState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);  //instantiate the thread
        gameThread.start();
    }

    public void run(){
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer =0;
        int drawCount = 0;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update(){
        if(gameState == playState) {
            // player
            player.update();
            // npc
            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    npc[i].update();
                }
            }
        }
        if(gameState == pauseState) {
            System.out.println("going to pause");
        }
    }


    //draws on jpanel
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Debugging
//        long drawStart = 0;
//        if(keyH.checkDrawTime == true) {
//            drawStart = System.nanoTime();
//        }

        // Title Screen
        if(gameState == titleState) {
            ui.draw(g2);
        }
        // other stuff
        else {
            // Tiles
            tileM.draw(g2);

            // Objects
            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }

            // NPC
            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null) {
                    npc[i].draw(g2);
                }
            }

            // player
            player.repaint(g2);

            // ui
            ui.draw(g2);
        }




        // debug
//        if(keyH.checkDrawTime == true) {
//            long drawEnd = System.nanoTime();
//            long passed = drawEnd - drawStart;
//            g2.setColor(Color.white);
//            g2.drawString("Draw Time: " + passed, 10, 400);
//            System.out.println("Draw Time: " + passed);
//        }

        g2.dispose();
    }

    public void playMusic(int i){

        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {

        sound.stop();
    }

    public void playSE(int i) {

        sound.setFile(i);
        sound.play();
    }
}
