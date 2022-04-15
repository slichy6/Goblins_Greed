package object;

import characters.Characters;
import main.GamePanel;

public class OBJ_Flail extends Characters {
    public OBJ_Flail(GamePanel gp) {
        super(gp);

        type = type_weapon;
        name = "Flail";
        down1 = setup("/images/weapons/mace", gp.tileSize, gp.tileSize);
        attackStrength = 2;
        description = "[" + name + "]\nThe ole ball 'n chain.";
    }
}
