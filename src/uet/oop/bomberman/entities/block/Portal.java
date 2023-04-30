package uet.oop.bomberman.entities.block;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import java.util.List;
import static uet.oop.bomberman.entities.EntityArr.*;

public class Portal extends Entity {

    public Portal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Portal(int xUnit, int yUnit) {
        super(xUnit, yUnit);
    }

    public static void checkPortal(List<Entity> e) {
        for (Entity portal : portals) {
            if (e.isEmpty()) {
                portal.setAlive(true);
                portal.setImg(Sprite.portal.getFxImage());
            } else {
                portal.setAlive(false);
                portal.setImg(Sprite.grass.getFxImage());
            }
        }
    }

    @Override
    public void update() {
        checkPortal(enemies);
    }


}
