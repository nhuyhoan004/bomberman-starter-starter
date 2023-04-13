package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import uet.oop.bomberman.entities.Entity;


public class BombAnimation extends Animation {
    private static List<Image> bombSprite = new ArrayList<>(Arrays.asList(Sprite.bomb.getFxImage(),Sprite.bomb_1.getFxImage(),
            Sprite.bomb_2.getFxImage()));

    public BombAnimation() {
    }

    /**
     * chia truong hop de chon loai hoat anh phu hop
     * @param entity doi tuong can xu ly
     */
    public void setSprite(Entity entity) {
        entity.setImg(this.handle(bombSprite, "bombSprite"));
    }
}
