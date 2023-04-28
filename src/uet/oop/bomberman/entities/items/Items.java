package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class Items extends Entity {
    public Items(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Items() {}

    public void update() {

    }
}
