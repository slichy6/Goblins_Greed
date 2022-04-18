package main;

import characters.Characters;
import characters.Player;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Characters characters){

        int charactersLeftWorldX = characters.worldX + characters.solidArea.x;
        int charactersRightWorldX = characters.worldX + characters.solidArea.x + characters.solidArea.width;
        int charactersTopWorldY = characters.worldY + characters.solidArea.y;
        int charactersBottomWorldY = characters.worldY + characters.solidArea.y + characters.solidArea.height;

        int charactersLeftCol = charactersLeftWorldX/gp.tileSize;
        int charactersRightCol = charactersRightWorldX/gp.tileSize;
        int charactersTopRow = charactersTopWorldY/gp.tileSize;
        int charactersBottomRow = charactersBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        //checks collision for player rectangle compared to location on map/ prediction of direction
        switch(characters.direction){
            case "up":
                charactersTopRow = (charactersTopWorldY - characters.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[charactersLeftCol][charactersTopRow];
                tileNum2 = gp.tileM.mapTileNum[charactersRightCol][charactersTopRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    characters.collisionOn = true;
                }
                break;

            case "down":
                charactersBottomRow = (charactersBottomWorldY + characters.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[charactersLeftCol][charactersBottomRow];
                tileNum2 = gp.tileM.mapTileNum[charactersRightCol][charactersBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    characters.collisionOn = true;
                }
                break;

            case "left":
                charactersLeftCol = (charactersLeftWorldX - characters.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[charactersLeftCol][charactersTopRow];
                tileNum2 = gp.tileM.mapTileNum[charactersLeftCol][charactersBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    characters.collisionOn = true;
                }
                break;

            case "right":
                charactersRightCol = (charactersRightWorldX + characters.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[charactersRightCol][charactersTopRow];
                tileNum2 = gp.tileM.mapTileNum[charactersRightCol][charactersBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    characters.collisionOn = true;
                }
                break;
        }
    }
    public int checkObject(Characters characters, boolean player) {

        int index = 999;

        for(int i = 0; i < gp.obj.length; i++) {
            if(gp.obj[i] != null) {

                // Get character's solid area position
                characters.solidArea.x = characters.worldX + characters.solidArea.x;
                characters.solidArea.y = characters.worldY + characters.solidArea.y;

                // Get the object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch(characters.direction) {
                    case "up":
                        characters.solidArea.y -= characters.speed;
                        if(characters.solidArea.intersects(gp.obj[i].solidArea)) {
                            if(gp.obj[i].collision == true) {
                                characters.collisionOn = true;
                            }
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;

                    case "down":
                        characters.solidArea.y += characters.speed;
                        if(characters.solidArea.intersects(gp.obj[i].solidArea)) {
                            if(gp.obj[i].collision == true) {
                                characters.collisionOn = true;
                            }
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;

                    case "left":
                        characters.solidArea.x -= characters.speed;
                        if(characters.solidArea.intersects(gp.obj[i].solidArea)) {
                            if(gp.obj[i].collision == true) {
                                characters.collisionOn = true;
                            }
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;

                    case "right":
                        characters.solidArea.x += characters.speed;
                        if(characters.solidArea.intersects(gp.obj[i].solidArea)) {
                            if(gp.obj[i].collision == true) {
                                characters.collisionOn = true;
                            }
                            if(player == true) {
                                index = i;
                            }
                        }
                        break;
                        }


                characters.solidArea.x = characters.solidAreaDefaultX;
                characters.solidArea.y = characters.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    // NPC or Monster collision

    public int checkCharacter(Characters characters, Characters[] target) {

        int index = 999;

        for(int i = 0; i < target.length; i++) {
            if(target[i] != null) {

                // Get character's solid area position
                characters.solidArea.x = characters.worldX + characters.solidArea.x;
                characters.solidArea.y = characters.worldY + characters.solidArea.y;

                // Get the object's solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch(characters.direction) {
                    case "up":
                        characters.solidArea.y -= characters.speed;
                        break;
                    case "down":
                        characters.solidArea.y += characters.speed;
                        break;
                    case "left":
                        characters.solidArea.x -= characters.speed;
                        break;
                    case "right":
                        characters.solidArea.x += characters.speed;
                        break;
                }
            if(characters.solidArea.intersects(target[i].solidArea)) {
                if(target[i] != characters) {
                    characters.collisionOn = true;
                    index = i;
                }
            }
                characters.solidArea.x = characters.solidAreaDefaultX;
                characters.solidArea.y = characters.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public boolean checkPLayer(Characters characters) {

        boolean contactPlayer = false;

        characters.solidArea.x = characters.worldX + characters.solidArea.x;
        characters.solidArea.y = characters.worldY + characters.solidArea.y;

        // Get the object's solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch(characters.direction) {
            case "up":
                characters.solidArea.y -= characters.speed;
                break;
            case "down":
                characters.solidArea.y += characters.speed;
                break;
            case "left":
                characters.solidArea.x -= characters.speed;
                break;
            case "right":
                characters.solidArea.x += characters.speed;
                break;
        }
        if(characters.solidArea.intersects(gp.player.solidArea)) {
            characters.collisionOn = true;
            contactPlayer = true;
        }

        characters.solidArea.x = characters.solidAreaDefaultX;
        characters.solidArea.y = characters.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;

    }
}
