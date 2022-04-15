package characters;

import main.GamePanel;

import java.util.Random;

public class NPC_Cleric extends Characters {

    public NPC_Cleric(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;

        getClericImage();
        setDialogue();

    }

    public void getClericImage(){

        up1 = setup("/images/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/images/npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/images/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/images/npc/oldman_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/images/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/images/npc/oldman_right_2", gp.tileSize, gp.tileSize);
        left1 = setup("/images/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/images/npc/oldman_left_2", gp.tileSize, gp.tileSize);
    }


    public void setDialogue() {

        dialogues[0] = "TEst Test Test";
        dialogues[1] = "You're going to need to find more from the JSON file";
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
    public void speak() {

        // leaving this child parameter here in case there is a point in the game when I have to show a new item or need special dialogue

        super.speak(); // inherits the speak method from the Characters parent class
    }

}
