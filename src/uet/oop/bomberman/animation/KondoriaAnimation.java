package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class KondoriaAnimation extends Animation {
    private static List<Image> left = new ArrayList<>(Arrays.asList(Sprite.kondoria_left1.getFxImage(),Sprite.kondoria_left2.getFxImage()
    ,Sprite.kondoria_left3.getFxImage()));
    private static List<Image> right = new ArrayList<>(Arrays.asList(Sprite.kondoria_right1.getFxImage(),Sprite.kondoria_right2.getFxImage()
    ,Sprite.kondoria_right3.getFxImage()));
    private static List<Image> dead = new ArrayList<>(Arrays.asList(Sprite.kondoria_dead.getFxImage(),Sprite.mob_dead1.getFxImage()
    ,Sprite.mob_dead2.getFxImage(),Sprite.mob_dead3.getFxImage()));

    public KondoriaAnimation() {
    }

    /**
     * chia truong hop de chon loai hoat anh phu hop
     * @param entity doi tuong can xu ly
     */
    public void setSprite(Entity entity) {
        if (!(entity instanceof Bomber)) {
            return;
        }
        Bomber bomber = (Bomber) entity;
        if (bomber.getHp() <= 0) {
            this.numberOfFrames = this.numberOfDeadFrames;
            bomber.setImg(this.handle(dead, "dead"));

            this.countDeadFrames++;
            if (this.countDeadFrames == this.numberOfDeadFrames * (dead.size())) {
                this.finishDeadAnimation = true;
            }
            return;
        }
    }
}

