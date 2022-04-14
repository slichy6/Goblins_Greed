package monster;

import characters.Characters;
import main.GamePanel;

import java.util.Random;

public class MON_BlueSlime extends Characters {

    public MON_BlueSlime(GamePanel gp) {
        super(gp);

        type = 2; // this is to ensure that only monsters can harm the player character and not the npcs
        name = "Green Slime";
        speed = 1;
        maxLife = 3;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();

    }

    public void getImage() {

        up1 = setup("/images/monster/blue_down_1");
        up2 = setup("/images/monster/blue_down_2");
        down1 = setup("/images/monster/blue_down_1");
        down2 = setup("/images/monster/blue_down_2");
        left1 = setup("/images/monster/blue_down_1");
        left2 = setup("/images/monster/blue_down_2");
        right1 = setup("/images/monster/blue_down_1");
        right2 = setup("/images/monster/blue_down_2");
    }

    public void setAction() {

        actionLockCounter++;

        if(actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
        }

        actionLockCounter = 0;
    }
}
