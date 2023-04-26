package uet.oop.bomberman.entities.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Brick extends Entity {

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    private static List<Image> dead = new ArrayList<> (Arrays.asList(Sprite.brick_exploded.getFxImage(),Sprite.brick_exploded1.getFxImage()
            ,Sprite.brick_exploded2.getFxImage()));

    public void checkHidden() {
        if (!this.isAlive()) {
            this.setImg(Sprite.grass.getFxImage());
        }
    }

    public boolean isAlive() {
        if (this.hp == 0) {
            return false;
        }
        return true;
    }

    @Override
    public void update() {
        checkHidden();
    }

}
