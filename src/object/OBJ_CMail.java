package object;

import characters.Characters;
import main.GamePanel;

public class OBJ_CMail extends Characters {
    public OBJ_CMail(GamePanel gp) {
        super(gp);

        type = type_armor;
        name = "Chain Mail";
        down1 = setup("/images/armor/chain_mail", gp.tileSize, gp.tileSize);
        defenseLevel = 2;
        description = "[" + name + "]\nVest made of linked\nchains.";
    }
}
