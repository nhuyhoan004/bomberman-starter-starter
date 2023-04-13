package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import uet.oop.bomberman.entities.Entity;


public class BomberAnimation extends Animation{

    /**
     * cac list là cac trang thai
     */
    private static List<Image> left = new ArrayList<>(Arrays.asList(Sprite.player_left.getFxImage(),Sprite.player_left_1.getFxImage()
            ,Sprite.player_left_2.getFxImage()));
    private static List<Image> right = new ArrayList<>(Arrays.asList(Sprite.player_right.getFxImage(),Sprite.player_right_1.getFxImage()
            ,Sprite.player_right_2.getFxImage()));
    private static List<Image> up = new ArrayList<>(Arrays.asList(Sprite.player_up.getFxImage(),Sprite.player_up_1.getFxImage()
            ,Sprite.player_up_2.getFxImage()));
    private static List<Image> down = new ArrayList<>(Arrays.asList(Sprite.player_down.getFxImage(),Sprite.player_down_1.getFxImage()
            ,Sprite.player_down_2.getFxImage()));
    private static List<Image> dead = new ArrayList<>(Arrays.asList(Sprite.player_dead1.getFxImage(),Sprite.player_dead2.getFxImage()
            ,Sprite.player_dead3.getFxImage()));

    public BomberAnimation() {

    }

    /**
     * chia truong hop de chon loai hoat anh phu hop
     * @param entity doi tuong can xu ly
     */
    public void setSprite(Entity entity) {
        if (!(entity instanceof MovingEntity)) {
            return;
        }
        MovingEntity movingEntity = (MovingEntity) entity;
        if (movingEntity.getHp() <= 0) {
            this.numberOfFrames = this.numberOfDeadFrames;
            movingEntity.setImg(this.handle(dead, "dead"));

            this.countDeadFrames++;
            if (this.countDeadFrames == this.numberOfDeadFrames * (this.dead.size())) {
                this.finishDeadAnimation = true;
            }
            return;
        }

        if (movingEntity.isMoveLeft()) {
            movingEntity.setImg(this.handle(left, "left"));
        } else if (movingEntity.isMoveRight()) {
            movingEntity.setImg(this.handle(right, "right"));
        } else if (movingEntity.isMoveUp()) {
            movingEntity.setImg(this.handle(up, "up"));
        } else if (movingEntity.isMoveDown()) {
            movingEntity.setImg(this.handle(down, "down"));
        }
    }
}
