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
    String sound;
    public static Sound title_screen = new Sound ("title_screen");
    public static Sound soundplay = new Sound ("soundplay");
    public static Sound bomno = new Sound ("BOM_NO");
    public static Sound dead = new Sound ("died");
    public static Sound enemy_dead = new Sound ("ENEMY_DIE");
    public static Sound game_over = new Sound ("game_over");
    public static Sound Item = new Sound ("Item");
    public static Sound move = new Sound ("move");
    public static Sound place_bomb = new Sound ("SPACE");
    public static Sound miss = new Sound ("miss");
    public static Sound win  = new Sound ("stage_clear");
    public static Sound stage_start = new Sound ("stage_start");
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
           clip.close ();
        }
    }

}
