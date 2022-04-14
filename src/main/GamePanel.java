package main;
import javax.swing.JPanel;
import characters.*;
import object.SuperObject;
import tile.TileManager;

import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //screen settings
    final int originalTileSize = 16;  //16x16 tile
    final int scale = 3; 

    public final int tileSize = originalTileSize * scale;  //48 x 48 tile
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
    Thread gameThread;  //starts and stops game clock
    public CollisionChecker checker = new CollisionChecker(this);
    public ObjectSetter oSetter = new ObjectSetter(this);
    public UI ui = new UI(this);

    public Player player = new Player(this, keyH);
    //how many objects you can display at one time
    public SuperObject obj[] = new SuperObject[20];


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupObjects(){

        oSetter.setObject();
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

//            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update(){
        
        player.update();
    }
    //draws on jpanel
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        //draws tiles
        tileM.draw(g2);

        //draws objects
        for(int i = 0; i < obj.length; i++){
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        }
        
        //draws the goblin
        player.repaint(g2);

        //UI
        ui.draw(g2);

        g2.dispose();
    }
}
