package uet.oop.bomberman.entities.block;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.killList;
import static uet.oop.bomberman.entities.EntityArr.bricks;

public class Brick extends Entity {

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    private static List<Image> dead = new ArrayList<> (Arrays.asList(Sprite.brick_exploded.getFxImage(),Sprite.brick_exploded1.getFxImage()
            ,Sprite.brick_exploded2.getFxImage()));

    public void checkHidden() {
        for (Entity entity : bricks) {
            if (entity instanceof Brick) {
                // danh sách chứa các Brick bị phá hủy
                // nếu bị phá hủy biến Brick thành Grass
                if (killList[entity.getX() / 32][entity.getY() / 32] == 4) {
                    entity.setImg(Sprite.grass.getFxImage());
                }
            }
        }

    }


    @Override
    public void update() {
        checkHidden();
    }
}
