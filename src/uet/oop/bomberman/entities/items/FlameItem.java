package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import static uet.oop.bomberman.BombermanGame.bomber;
import static uet.oop.bomberman.entities.EntityArr.flameItems;

public class FlameItem extends Items {
    public FlameItem(int x, int y, Image img) {
        super(x, y, img);
    }
    public FlameItem() {

    }
    public void update() {
    }
}
