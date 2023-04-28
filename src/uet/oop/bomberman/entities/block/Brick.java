package uet.oop.bomberman.entities.block;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Brick extends Entity {

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    private static final List<Image> dead = new ArrayList<> (Arrays.asList(Sprite.brick_exploded.getFxImage(),Sprite.brick_exploded1.getFxImage()
            ,Sprite.brick_exploded2.getFxImage()));

    public void checkHidden() {
        if (!this.isAlive()) {
            this.setImg(Sprite.grass.getFxImage());
        }
    }

    public boolean isAlive() {
        return this.hp != 0;
    }

    @Override
    public void update() {
        checkHidden();
    }

}
