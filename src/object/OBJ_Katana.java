package object;

import characters.Characters;
import main.GamePanel;

public class OBJ_Katana extends Characters {
    public OBJ_Katana(GamePanel gp) {
        super(gp);

        name = "Katana";
        down1 = setup("/images/weapons/katana", gp.tileSize, gp.tileSize);
        attackStrength = 1;

    }
}
