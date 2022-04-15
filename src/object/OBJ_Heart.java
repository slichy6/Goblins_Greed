package object;

import characters.Characters;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Characters {

    public OBJ_Heart(GamePanel gp) {

        super(gp);
        name = "Heart";
        image1 = setup("/images/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/images/objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("/images/objects/heart_blank", gp.tileSize, gp.tileSize);

    }
}
