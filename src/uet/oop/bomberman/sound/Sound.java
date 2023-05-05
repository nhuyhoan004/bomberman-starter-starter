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
    private int currentFrame;
    private boolean isPaused;

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
// bắt đầu phát âm thanh
    public void play() {
        if (clip != null) {
            clip.setFramePosition(currentFrame);
            clip.start();
            isPaused = false;
        }
    }
 // tạm dừng âm thanh
    public void pause() {
        if (clip != null && clip.isRunning()) {
            currentFrame = clip.getFramePosition();
            clip.stop();
            isPaused = true;
        }
    }
// tiếp tục âm thanh
    public void resume() {
        if (clip != null && isPaused) {
            clip.setFramePosition(currentFrame);
            clip.start();
            isPaused = false;
        }
    }
// dừng âm thanh
    public void stop() {
        if (clip != null) {
            clip.stop();
            currentFrame = 0;
            isPaused = false;
        }
    }
// đóng âm thanh
    public void close() {
        stop();
        if (clip != null) {
            clip.close();
        }
    }
}
