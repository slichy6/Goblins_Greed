package object;

import characters.Characters;
import main.*;


public class OBJ_objects extends Characters {

    public OBJ_objects(GamePanel gp) {

        super(gp);

        name = "coins_gold";
        down1 = setup("/images/objects/coins_gold", gp.tileSize, gp.tileSize);
        collision = true;
    }
}