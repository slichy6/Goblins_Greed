package main;
import javax.swing.JPanel;

import characters.*;
import object.SuperObject;
import tile.*;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    //screen settings
    final int originalTileSize = 16;  //16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;  //48 x 48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;  //768 pixels
    public final int screenHeight = tileSize * maxScreenRow;  //576 pixels

    //WORLD settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // Full Screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    //FPS setting
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);

    Thread gameThread;  //starts and stops game clock

    // Character and Objects
    public Player player = new Player(this, keyH);
    public Characters[] obj = new Characters[10];
    public Characters[] npc = new Characters[10];
    public Characters[] monster = new Characters[20];
    ArrayList<Characters> characterList = new ArrayList<>();

    // Game States
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int nameEnterState = 5;


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
        aSetter.setMonster();
//        playMusic(0);
//        stopMusic();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics(); // rendering the screen to a temp then to JPanel to resize

//        setFullScreen();


    }

    public void setFullScreen(){

        // Sets the game screen to the default screen setting of the user
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // full screen width and height
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();

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
//                repaint();
                drawFullScreen(); // draw everything to the buffered images
                drawToScree(); // draw buffered image to the screen
                delta--;
                drawCount++;
            }

            if(timer >= 1000000){
                drawCount = 0;
                timer = 0;
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
            // MONSTER
            for(int i = 0; i < monster.length; i++) {
                if(monster[i] != null) {
                    if(monster[i].alive == true && monster[i].dying == false) {
                        monster[i].update();
                    }
                    if(monster[i].alive == false) {
                        monster[i] = null;
                    }
                }
            }
        }
        if(gameState == pauseState) {
            System.out.println("going to pause");
        }
    }

    public void drawFullScreen(){
        //Debugging/testing
        long drawStart = 0;
        if(keyH.showDebugText == true){
            drawStart = System.nanoTime();
        }


        // Title Screen
        if(gameState == titleState) {
            ui.draw(g2);
        }
        // other stuff
        else {
            // Tiles
            tileM.draw(g2);

            // add characters and objects/items to the list
            characterList.add(player);

            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null) {
                    characterList.add(npc[i]);
                }
            }

            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null) {
                    characterList.add(obj[i]);
                }
            }

            for(int i = 0; i < monster.length; i++){
                if(monster[i] != null) {
                    characterList.add(monster[i]);
                }
            }

            // Sort
            Collections.sort(characterList, new Comparator<Characters>() {
                @Override
                public int compare(Characters o1, Characters o2) {

                    int result = Integer.compare(o1.worldY, o2.worldY);
                    return result;
                }
            });

            // Draw characters/items/objects
            for(int i = 0; i <characterList.size(); i++) {
                characterList.get(i).draw(g2);
            }
            // empty the character list
            characterList.clear();

            // player
//            player.repaint(g2);

            // ui
            ui.draw(g2);
        }

        // SHow the player's world x, world y so we can place items within the world easier
        if(keyH.showDebugText == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX " + player.worldX, x, y); y += lineHeight;
            g2.drawString("WorldY " + player.worldY, x, y); y += lineHeight;
            g2.drawString("Column " + (player.worldX + player.solidArea.x)/tileSize, x, y);y += lineHeight;
            g2.drawString("Row " + (player.worldY + player.solidArea.y)/tileSize, x, y);y += lineHeight;
            System.out.println("Draw Time: " + passed);
        }

    }
//
//    //draws on jpanel
//    public void paintComponent(Graphics g){
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D)g;
//
//        //Debugging/testing
//        long drawStart = 0;
//        if(keyH.showDebugText == true){
//            drawStart = System.nanoTime();
//        }
//
//
//        // Title Screen
//        if(gameState == titleState) {
//            ui.draw(g2);
//        }
//        // other stuff
//        else {
//            // Tiles
//            tileM.draw(g2);
//
//            // add characters and objects/items to the list
//            characterList.add(player);
//
//            for(int i = 0; i < npc.length; i++){
//                if(npc[i] != null) {
//                    characterList.add(npc[i]);
//                }
//            }
//
//            for(int i = 0; i < obj.length; i++){
//                if(obj[i] != null) {
//                    characterList.add(obj[i]);
//                }
//            }
//
//            for(int i = 0; i < monster.length; i++){
//                if(monster[i] != null) {
//                    characterList.add(monster[i]);
//                }
//            }
//
//            // Sort
//            Collections.sort(characterList, new Comparator<Characters>() {
//                @Override
//                public int compare(Characters o1, Characters o2) {
//
//                    int result = Integer.compare(o1.worldY, o2.worldY);
//                    return result;
//                }
//            });
//
//            // Draw characters/items/objects
//            for(int i = 0; i <characterList.size(); i++) {
//                characterList.get(i).draw(g2);
//            }
//            // empty the character list
//            characterList.clear();
//
//            // player
////            player.repaint(g2);
//
//            // ui
//            ui.draw(g2);
//        }
//
//        // SHow the player's world x, world y so we can place items within the world easier
//        if(keyH.showDebugText == true){
//            long drawEnd = System.nanoTime();
//            long passed = drawEnd - drawStart;
//
//            g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
//            g2.setColor(Color.white);
//            int x = 10;
//            int y = 400;
//            int lineHeight = 20;
//
//            g2.drawString("WorldX " + player.worldX, x, y); y += lineHeight;
//            g2.drawString("WorldY " + player.worldY, x, y); y += lineHeight;
//            g2.drawString("Column " + (player.worldX + player.solidArea.x)/tileSize, x, y);y += lineHeight;
//            g2.drawString("Row " + (player.worldY + player.solidArea.y)/tileSize, x, y);y += lineHeight;
//            System.out.println("Draw Time: " + passed);
//        }
//
//        g2.dispose();
//    }
    public void drawToScree() {

        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
    public void playMusic(int i){

        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {

        music.stop();
    }

    public void playSE(int i) {

        music.setFile(i);
        music.play();
    }
}
