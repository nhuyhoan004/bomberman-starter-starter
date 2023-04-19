package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.animation.FlameAnimation;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {
    private String classification;

    public String getClassification() {
        return classification;
    }

    /**
     * Ham khoi tao
     * @param x toa do x
     * @param y toa do y
     * @param img anh
     * @param classification: phan loai (left, right, up, down, vertical, epicenter, horizontal)
     */
    public Flame(int x, int y, Image img, String classification) {
        super(x, y, img);
        this.classification = classification;
        this.animation = new FlameAnimation();
    }

    public Flame(int x, int y, String classification) {
        super(x, y);
        this.classification = classification;
        this.animation = new FlameAnimation();
    }

    public void update() {
        this.animation.setSprite(this);
    }
    public static void addFlame(int xUnit, int yUnit) {
        if (Entity.getBombers().size() == 0) {
            return;
        }

        Entity flame = new Flame (xUnit, yUnit, "epicenter");
        Entity.getFlames().add(flame);
        addFlame(xUnit, yUnit, -1, 0);
        addFlame(xUnit, yUnit, 1, 0);
        addFlame(xUnit, yUnit, 0, -1);
        addFlame(xUnit, yUnit, 0, 1);
    }

    private static void addFlame(int xUnit, int yUnit, int x, int y) {
        Bomber bomber = (Bomber) Entity.getBombers().get(0);

        boolean add = true;

        for (int i = 0; i < bomber.getFlame(); i++) {
            String s;
            if (x == -1) {
                s = "left";
            } else if (x == 1) {
                s = "right";
            } else if (y == -1) {
                s = "up";
            } else {
                s = "down";
            }

            Entity flame;
            if (i + 1 < bomber.getFlame()) {
                if (x == 0) {
                    flame = new Flame (xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_vertical.getFxImage(), "vertical");
                } else {
                    flame = new Flame (xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_horizontal.getFxImage(), "horizontal");
                }
            }
            else {
                if (x == -1) {
                    flame = new Flame (xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_horizontal_left_last.getFxImage(), "left");
                } else if (x == 1) {
                    flame = new Flame (xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_horizontal_right_last.getFxImage(), "right");
                } else if (y == -1) {
                    flame = new Flame (xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_vertical_top_last.getFxImage(), "up");
                } else {
                    flame = new Flame (xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_vertical_down_last.getFxImage(), "down");
                }
            }

            add = true;
            if (!add) {
                break;
            }
            Entity.getFlames().add(flame);
        }
    }
    /**
     * Xoa nhung doi tuong co hp <= 0 khoi cac list
     */
    public static void removeDeadEntity() {
        for (int i = 0; i < Entity.getBombs().size(); i++) {
            if (Entity.getBombs().get(i).getHp() > 0) {
                break;
            }
            addFlame(Entity.getBombs().get(i).getX() / Sprite.SCALED_SIZE, Entity.getBombs().get(i).getY() / Sprite.SCALED_SIZE);
            Entity.getBombs().remove(i--);
        }

        for (int i = 0; i < Entity.getBombers().size(); i++) {
            if (Entity.getBombers().get(i).getHp() <= 0) {
                Entity.getDeads().add(Entity.getBombers().get(i));
                Entity.getBombers().remove(i--);
            }
        }
    }

    public static void removeFinishedElements() {
        for (int i = 0; i < Entity.getDeads().size(); i++) {
            if (Entity.getDeads().get(i).getAnimation().isFinishDeadAnimation()) {
                Entity.getDeads().remove(i--);
            }
        }

        for (int i = 0; i < Entity.getFlames().size(); i++) {
            if (Entity.getFlames().get(i).getAnimation().isFinishDeadAnimation()) {
                Entity.getFlames().remove(i--);
            }
        }
    }

}
