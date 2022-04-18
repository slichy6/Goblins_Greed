package tile;

import main.GamePanel;
import main.UtilityTool;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.ImageIO;

//import com.GameBoard.main.GamePanel;


public class TileManager{

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[100];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/images/maps/world1.txt");
    }

    public void getTileImage(){

            setup(10, "cobblestone", false);
            setup(11, "grass", false);
            setup(12, "tree1", true);
            setup(13, "wood_floor", false);
            setup(14, "border_bottom", true);
            setup(15, "flooring", false);
            setup(16, "block", true);
            setup(17, "border_top", true);
            setup(18, "border_right", true);
            setup(19, "border_left", true);
            setup(20,"ruby", true);
    }

    public void setup(int index, String imageName, boolean collision){

        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaledImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream("/images/maps/world1.txt");

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){

                String line = br.readLine();

                while(col < gp.maxWorldCol){
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch(Exception e){

        }
    }

    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;


        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            //screen size in comparison to world
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // stop moving the camera
            if(gp.player.screenX > gp.player.worldX) {
                screenX = worldX;
            }
            if(gp.player.screenY > gp.player.worldY) {
                screenY = worldY;
            }

//            int rightOffset = gp.screenWidth - gp.player.screenX;
//            if(rightOffset > gp.worldWidth - gp.player.worldX) {
//                screenX = gp.screenWidth - (gp.worldWidth - worldX);
//            }
//            int bottomOffset = gp.screenHeight - gp.player.screenY;
//            if(bottomOffset > gp.worldHeight - gp.player.worldY){
//                screenY = gp.screenHeight - (gp.worldHeight - worldY);
//            }


            if(worldX + gp.tileSize * 15 > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize * 15 < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize * 15 > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize * 15 < gp.player.worldY + gp.player.screenY){

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            else if(gp.player.screenX > gp.player.worldX ||
                    gp.player.screenY > gp.player.worldY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}