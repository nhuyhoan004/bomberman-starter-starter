package uet.oop.bomberman.entities.bomber;

import javafx.scene.image.Image;
import uet.oop.bomberman.animation.BombAnimation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    private boolean isPassable = true;
    private static final int time = 150;
    private int count = 0;
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        animation = new BombAnimation();
    }

    public boolean isPassable() {
        return isPassable;
    }

    public void update() {
        this.bombTimer();
        this.animation.setSprite(this);
    }

    public boolean equals(Object object) {
        if ((object instanceof Bomb)) {
            Bomb bomb = (Bomb) object;
            if (this.x == bomb.getX() && this.y == bomb.getY()) {
                return true;
            }
        }
        return false;
    }

    public void bombTimer() {
        // time: thoi gian ton tai cua bomb
        count++;
        if (count > time) {
            this.hp = 0;
        }
    }

    public void checkCharacterPassability(Bomber bomber) {
        if (this.isPassable) {
            if (!bomber.intersects(this)) {
                this.isPassable = false;
            }
        }
    }
    public static void addBomb() {
        if (Entity.getBombers().size() <= 0) {
            return;
        }
        Bomber bomber = (Bomber) Entity.getBombers().get(0);
        if ((bomber.getCreateBomb() != 1) || Entity.getBombs().size() >= bomber.getBomb()) {
            return;
        }

        Bomb bomb = new Bomb ((bomber.getX() + (int)(bomber.getImg().getWidth() / 2))/ Sprite.SCALED_SIZE,
                (bomber.getY() + (int)(bomber.getImg().getHeight() / 2)) / Sprite.SCALED_SIZE, Sprite.bomb.getFxImage());
        boolean add = true;
        for (int i = 0; i < Entity.getBombs().size(); i++) {
            if (Entity.getBombs().get(i).equals(bomb)) {
                add = false;
                break;
            }
        }
        if (add) {
            Entity.getBombs().add(bomb);
        }
    }
}
