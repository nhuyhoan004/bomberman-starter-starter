package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.animation.FlameAnimation;

public class Flame extends Entity {
    private String classification;

    public String getClassification() {
        return classification;
    }

    /**
     * Ham khoi tao
     * @param x toa do x
     * @param y toa do y
     * @param img anh
     * @param classification: phan loai (left, right, up, down, vertical, epicenter, horizontal)
     */
    public Flame(int x, int y, Image img, String classification) {
        super(x, y, img);
        this.classification = classification;
        this.animation = new FlameAnimation();
    }

    public Flame(int x, int y, String classification) {
        super(x, y);
        this.classification = classification;
        this.animation = new FlameAnimation();
    }

    public void update() {
        this.animation.setSprite(this);
    }

}
