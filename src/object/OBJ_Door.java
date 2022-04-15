package object;

import characters.Characters;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends Characters {

    public OBJ_Door(GamePanel gp) {

        super(gp);

        name = "Door";
        down1 = setup("/images/objects/door", gp.tileSize, gp.tileSize);
        collision = true;
    }
}
