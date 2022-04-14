package main;

import object.*;

public class ObjectSetter {
    GamePanel gp;

    public ObjectSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

        gp.obj[0]= new OBJ_objects();
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 8 * gp.tileSize;

        gp.obj[1] = new OBJ_objects();
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 30 * gp.tileSize;

        gp.obj[2] = new OBJ_armor();
        gp.obj[2].worldX = 26 * gp.tileSize;
        gp.obj[2].worldY = 8 * gp.tileSize;

        gp.obj[3] = new OBJ_weapon();
        gp.obj[3].worldX = 23 * gp.tileSize;
        gp.obj[3].worldY = 34 * gp.tileSize;
        
    }
}
