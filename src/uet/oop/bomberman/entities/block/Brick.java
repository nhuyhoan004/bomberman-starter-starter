package uet.oop.bomberman.entities.block;
import javafx.scene.image.Image;
import uet.oop.bomberman.animation.BrickAnimation;
import uet.oop.bomberman.entities.Entity;


public class Brick extends Entity {

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        animation = new BrickAnimation();
    }
    @Override
    public void update() {
        this.animation.setSprite(this);
    }

}
