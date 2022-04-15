package object;

import characters.Characters;
import main.GamePanel;

public class OBJ_TShield extends Characters {
    public OBJ_TShield(GamePanel gp) {
        super(gp);

        type = type_armor;
        name = "Tower Shield";
        down1 = setup("/images/armor/tower_shield", gp.tileSize, gp.tileSize);
        defenseLevel = 5;
        description = "[" + name + "]\nTall and heavy.";
    }
}
