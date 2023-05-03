package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Flame;

public class FlameAnimation extends Animation {
    /**
     * Khởi tạo danh sách List hoạt hình tâm bom nổ
     */
    private static final List<Image> epicenter = new ArrayList<>(Arrays.asList(Sprite.bomb_exploded.getFxImage(),
            Sprite.bomb_exploded1.getFxImage(), Sprite.bomb_exploded2.getFxImage()));
    /**
     * Hoạt hình nổ theo chiều ngang
     */
    private static final List<Image> horizontal = new ArrayList<>(Arrays.asList(Sprite.explosion_horizontal.getFxImage()
            ,Sprite.explosion_horizontal1.getFxImage(),Sprite.explosion_horizontal2.getFxImage()));
    /**
     * hoạt hình nổ theo chiều ngang với phần cuối cùng bên trái
     */
    private static final List<Image> horizontalLeftLast = new ArrayList<>(Arrays.asList(Sprite.explosion_horizontal_left_last.getFxImage()
            ,Sprite.explosion_horizontal_left_last1.getFxImage(),Sprite.explosion_horizontal_left_last2.getFxImage()));
    /**
     * hoạt hình nổ theo chiều ngang với phần cuối cùng bên phải
     */
    private static final List<Image> horizontalRightLast = new ArrayList<>(Arrays.asList(Sprite.explosion_horizontal_right_last.getFxImage()
            ,Sprite.explosion_horizontal_right_last1.getFxImage(),Sprite.explosion_horizontal_right_last2.getFxImage()));
    /**
     * hoạt hình nổ theo chiều dọc
     */
    private static final List<Image> vertical = new ArrayList<>(Arrays.asList(Sprite.explosion_vertical.getFxImage()
            ,Sprite.explosion_vertical1.getFxImage(),Sprite.explosion_vertical2.getFxImage()));
    /**
     * hoạt hình nổ theo chiều dọc bên trên
     */
    private static final List<Image> verticalUpLast = new ArrayList<>(Arrays.asList(Sprite.explosion_vertical_top_last.getFxImage()
            ,Sprite.explosion_vertical_top_last1.getFxImage(),Sprite.explosion_vertical_top_last2.getFxImage()));
    /**
     * hoạt hình nổ theo chiều dọc với bên dưới
     */
    private static final List<Image> verticalDownLast = new ArrayList<>(Arrays.asList(Sprite.explosion_vertical_down_last.getFxImage()
            ,Sprite.explosion_vertical_down_last1.getFxImage(),Sprite.explosion_vertical_down_last2.getFxImage()));


    public FlameAnimation() {
    }


    public void setSprite(Entity entity) {
        if (!(entity instanceof Flame)) {
            return;
        }
        Flame flame = (Flame) entity;
        List<Image> animationFrames = null;

        switch (flame.getClassification()) {
            case "up":
                animationFrames = verticalUpLast;
                break;
            case "down":
                animationFrames = verticalDownLast;
                break;
            case "left":
                animationFrames = horizontalLeftLast;
                break;
            case "right":
                animationFrames = horizontalRightLast;
                break;
            case "vertical":
                animationFrames = vertical;
                break;
            case "epicenter":
                animationFrames = epicenter;
                break;
            case "horizontal":
                animationFrames = horizontal;
                break;
        }

        if (animationFrames != null) {
            flame.setImg(this.handle(animationFrames, flame.getClassification()));
            this.countDeadFrames++;
            if (this.countDeadFrames == this.numberOfFrames * animationFrames.size()) {
                this.finishDeadAnimation = true;
            }
        }
    }
}
