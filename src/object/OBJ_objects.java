package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_objects extends SuperObject{

    public OBJ_objects(){
        
        name = "gold_coins";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/coins_gold.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}