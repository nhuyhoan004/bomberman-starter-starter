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
    private static List<Image> epicenter = new ArrayList<>(Arrays.asList(Sprite.bomb_exploded.getFxImage(),
            Sprite.bomb_exploded1.getFxImage(), Sprite.bomb_exploded2.getFxImage()));
    /**
     * Hoạt hình nổ theo chiều ngang
     */
    private static List<Image> horizontal = new ArrayList<>(Arrays.asList(Sprite.explosion_horizontal.getFxImage()
            ,Sprite.explosion_horizontal1.getFxImage(),Sprite.explosion_horizontal2.getFxImage()));
    /**
     * hoạt hình nổ theo chiều ngang với phần cuối cùng bên trái
     */
    private static List<Image> horizontalLeftLast = new ArrayList<>(Arrays.asList(Sprite.explosion_horizontal_left_last.getFxImage()
            ,Sprite.explosion_horizontal_left_last1.getFxImage(),Sprite.explosion_horizontal_left_last2.getFxImage()));
    /**
     * hoạt hình nổ theo chiều ngang với phần cuối cùng bên phải
     */
    private static List<Image> horizontalRightLast = new ArrayList<>(Arrays.asList(Sprite.explosion_horizontal_right_last.getFxImage()
            ,Sprite.explosion_horizontal_right_last1.getFxImage(),Sprite.explosion_horizontal_right_last2.getFxImage()));
    /**
     * hoạt hình nổ theo chiều dọc
     */
    private static List<Image> vertical = new ArrayList<>(Arrays.asList(Sprite.explosion_vertical.getFxImage()
            ,Sprite.explosion_vertical1.getFxImage(),Sprite.explosion_vertical2.getFxImage()));
    /**
     * hoạt hình nổ theo chiều dọc bên trên
     */
    private static List<Image> verticalUpLast = new ArrayList<>(Arrays.asList(Sprite.explosion_vertical_top_last.getFxImage()
            ,Sprite.explosion_vertical_top_last1.getFxImage(),Sprite.explosion_vertical_top_last2.getFxImage()));
    /**
     * hoạt hình nổ theo chiều dọc với bên dưới
     */
    private static List<Image> verticalDownLast = new ArrayList<>(Arrays.asList(Sprite.explosion_vertical_down_last.getFxImage()
            ,Sprite.explosion_vertical_down_last1.getFxImage(),Sprite.explosion_vertical_down_last2.getFxImage()));


    public FlameAnimation() {
    }

    public void setSprite(Entity entity) {
        if (!(entity instanceof Flame)) {
            return;
        }
        Flame flame = (Flame) entity;
        if (flame.getClassification().equals("up")) {
            flame.setImg(this.handle(verticalUpLast, "up"));
            this.countDeadFrames++;
            if (this.countDeadFrames == this.numberOfFrames * (verticalUpLast.size())) {
                this.finishDeadAnimation = true;
            }
        }
        else if (flame.getClassification().equals("down")) {
            flame.setImg(this.handle(verticalDownLast, "down"));
            this.countDeadFrames++;
            if (this.countDeadFrames == this.numberOfFrames * (verticalDownLast.size())) {
                this.finishDeadAnimation = true;
            }
        }
        else if (flame.getClassification().equals("left")) {
            flame.setImg(this.handle(horizontalLeftLast, "left"));
            this.countDeadFrames++;
            if (this.countDeadFrames == this.numberOfFrames * (horizontalLeftLast.size())) {
                this.finishDeadAnimation = true;
            }
        }
        else if (flame.getClassification().equals("right")) {
            flame.setImg(this.handle(horizontalRightLast, "right"));
            this.countDeadFrames++;
            if (this.countDeadFrames == this.numberOfFrames * (horizontalRightLast.size())) {
                this.finishDeadAnimation = true;
            }
        }
        else if (flame.getClassification().equals("vertical")) {
            flame.setImg(this.handle(vertical, "vertical"));
            this.countDeadFrames++;
            if (this.countDeadFrames == this.numberOfFrames * (vertical.size())) {
                this.finishDeadAnimation = true;
            }
        }
        else if (flame.getClassification().equals("epicenter")) {
            flame.setImg(this.handle(epicenter, "epicenter"));
            this.countDeadFrames++;
            if (this.countDeadFrames == this.numberOfFrames * (epicenter.size())) {
                this.finishDeadAnimation = true;
            }
        }
        else if (flame.getClassification().equals("horizontal")) {
            flame.setImg(this.handle(horizontal, "horizontal"));
            this.countDeadFrames++;
            if (this.countDeadFrames == this.numberOfFrames * (horizontal.size())) {
                this.finishDeadAnimation = true;
            }
        }
    }
}
