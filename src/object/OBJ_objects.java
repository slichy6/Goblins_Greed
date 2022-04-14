package object;

import characters.Characters;
import main.GamePanel;

public class OBJ_objects extends Characters {

    public OBJ_objects(GamePanel gp){
        super(gp);

        name = "Gold Coins";
        down1 = setup("/images/objects/coins_gold");

        collision = true;
        // obj[1] = "coins_gold";
        // try{
        //     obj[1] = ImageIO.read(getClass().getResourceAsStream("/images/objects/coins_gold.png"));

        // }catch(IOException e){
        //     e.printStackTrace();
        // }
    }
}