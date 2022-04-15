package object;

import characters.Characters;
import main.GamePanel;



public class OBJ_Key extends Characters {

    public OBJ_Key(GamePanel gp) {
        super(gp);

        name = "Key";
        down1 = setup("/images/objects/key", gp.tileSize, gp.tileSize);

    }
}
