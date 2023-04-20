package uet.oop.bomberman.entities.block;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;


public class Portal extends Entity {
    public static boolean isPortal = false;

    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }
}