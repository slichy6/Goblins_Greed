package object;

import java.io.IOException;
// import java.util.Objects;

// import main.GamePanel;
import javax.imageio.ImageIO;

public class OBJ_objects extends SuperObject{

    public OBJ_objects(){
        
        name = "gold_coins";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/gold_coins.png"));

        }catch(IOException e){
            e.printStackTrace();
        }

        collision = true;
        // obj[1] = "coins_gold";
        // try{
        //     obj[1] = ImageIO.read(getClass().getResourceAsStream("/images/objects/coins_gold.png"));

        // }catch(IOException e){
        //     e.printStackTrace();
        // }
    }
}