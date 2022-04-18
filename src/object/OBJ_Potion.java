package object;

import characters.Characters;
import main.GamePanel;

public class OBJ_Potion extends Characters {
    int value = 5;
    public OBJ_Potion(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Health Potion";
        down1 = setup("/images/objects/potion", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nRestores some health.";

    }
    public void use(Characters characters) {

        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drank the " + name + ".\n" +"Your health recovered by " + value + " points.";
        characters.life += value;
        if(gp.player.life > gp.player.maxLife) {
            gp.player.life = gp.player.maxLife;
        }
        gp.playSE(10);
    }
}
