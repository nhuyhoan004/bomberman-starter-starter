package uet.oop.bomberman.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Sound {
    private Clip clip;
    public String sound;
    public Sound(String sound) {
        this.sound = sound;
        try {
            File audioFile = new File("res/sound/" + sound + ".wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.start();
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }

    public void close() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
}
