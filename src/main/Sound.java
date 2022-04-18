package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/images/sound/intro.mid");
        soundURL[1] = getClass().getResource("/images/sound/town.mid");
        soundURL[2] = getClass().getResource("/images/sound/battle.mid");
        soundURL[3] = getClass().getResource("/images/sound/world.mid");
        soundURL[4] = getClass().getResource("/images/sound/dungeon.mid");
        soundURL[5] = getClass().getResource("/images/sound/hitmonster.wav");
        soundURL[6] = getClass().getResource("/images/sound/receivedamage.wav");
        soundURL[7] = getClass().getResource("/images/sound/attack.wav");
        soundURL[8] = getClass().getResource("/images/sound/levelup.wav");
        soundURL[9] = getClass().getResource("/images/sound/cursorm.wav");
        soundURL[10] = getClass().getResource("/images/sound/powerup.wav");
    }

    public void setFile(int i) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch(Exception e) {

        }
    }

    public void play() {

        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
