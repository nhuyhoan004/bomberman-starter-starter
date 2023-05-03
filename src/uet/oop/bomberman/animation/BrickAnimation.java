package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import uet.oop.bomberman.entities.Entity;


public class BrickAnimation extends Animation {
    private static final List<Image> dead = new ArrayList<>(Arrays.asList(Sprite.brick_exploded.getFxImage(),Sprite.brick_exploded1.getFxImage()
            ,Sprite.brick_exploded2.getFxImage()));

    public BrickAnimation() {
        this.numberOfDeadFrames = 16;
    }

    /**
     * chia truong hop de chon loai hoat anh phu hop
     * @param entity doi tuong can xu ly
     */
    public void setSprite(Entity entity) {
        if (entity.getHp() <= 0) {
            numberOfFrames = numberOfDeadFrames;
            entity.setImg(handle(dead, "dead"));
            countDeadFrames++;
            if (countDeadFrames == numberOfDeadFrames * dead.size()) {
                finishDeadAnimation = true;
            }
        }
    }
}
