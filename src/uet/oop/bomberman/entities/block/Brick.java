package uet.oop.bomberman.entities.block;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.block;
import static uet.oop.bomberman.BombermanGame.killList;

public class Brick extends Entity {

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void checkHidden() {
        for (Entity entity : block) {
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
