package object;

import characters.Characters;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Characters {

    public OBJ_Chest(GamePanel gp) {

        super(gp);

        name = "Treasure Chest";
        down1 = setup("/images/objects/chest", gp.tileSize, gp.tileSize);
        collision = true;
    }
}
