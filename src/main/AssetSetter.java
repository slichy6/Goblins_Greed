package main;

import characters.NPC_Cleric;
import monster.MON_BlueSlime;
import monster.MON_GreenSlime;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        gp.obj[0]= new OBJ_Key(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 8 * gp.tileSize;

        gp.obj[1] = new OBJ_Chest(gp);
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 30 * gp.tileSize;

        gp.obj[2] = new OBJ_Door(gp);
        gp.obj[2].worldX = 26 * gp.tileSize;
        gp.obj[2].worldY = 8 * gp.tileSize;

        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = 37 * gp.tileSize;
        gp.obj[3].worldY = 30 * gp.tileSize;

        gp.obj[3] = new OBJ_Potion(gp);
        gp.obj[3].worldX = 20 * gp.tileSize;
        gp.obj[3].worldY = 5 * gp.tileSize;

        gp.obj[4] = new OBJ_objects(gp);
        gp.obj[4].worldX = 22 * gp.tileSize;
        gp.obj[4].worldY = 5 * gp.tileSize;


    }

    public void setNPC() {

        gp.npc[0] = new NPC_Cleric(gp);
        gp.npc[0].worldX = gp.tileSize*5;
        gp.npc[0].worldY = gp.tileSize*6;

        gp.npc[1] = new NPC_Cleric(gp);
        gp.npc[1].worldX = gp.tileSize*29;
        gp.npc[1].worldY = gp.tileSize*32;
    }

    public void setMonster() {

        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldX = gp.tileSize*6;
        gp.monster[0].worldY = gp.tileSize*3;

        gp.monster[1] = new MON_BlueSlime(gp);
        gp.monster[1].worldX = gp.tileSize*7;
        gp.monster[1].worldY = gp.tileSize*4;

        gp.monster[2] = new MON_GreenSlime(gp);
        gp.monster[2].worldX = gp.tileSize*8;
        gp.monster[2].worldY = gp.tileSize*5;

        gp.monster[3] = new MON_BlueSlime(gp);
        gp.monster[3].worldX = gp.tileSize*20;
        gp.monster[3].worldY = gp.tileSize*4;
    }
}
