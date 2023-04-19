package uet.oop.bomberman.entities.bomber;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.animation.Animation;
import uet.oop.bomberman.animation.BomberAnimation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Flame;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends MovingEntity {
    private static List<Entity> bombers = new ArrayList<> ();
    private static List<Entity> bombs = new ArrayList<>();
    private static List<Entity> deads = new ArrayList<>();
    private static List<Entity> flames = new ArrayList<>();
    private int createBomb = 0;

    public int getCreateBomb() {
        return createBomb;
    }

    private int bomb = 1;
    private int flame = 1;



    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        animation = new BomberAnimation();
    }

    public int getFlame() {
        return flame;
    }

    public void setFlame(int flame) {
        if (1 <= flame && flame <= 4) {
            this.flame = flame;
        }
    }

    public int getBomb() {
        return bomb;
    }

    public void setBomb(int bomb) {
        if (1 <= bomb && bomb <= 5) {
            this.bomb = bomb;
        }
    }

    public void update() {
        this.move();
        this.animation.setSprite(this);
        this.ableToMoveDown = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveUp = true;
    }

    /**
     * Xu ly khi cac phim duoc nhan, nha.
     * @param scene
     */
    public void handleKeyPress(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                this.createBomb++;
                if (this.createBomb > 100000) {
                    this.createBomb = 10;
                }
            }

            if (event.getCode() == KeyCode.UP) {
                this.moveUp = true;
            } else if (event.getCode() == KeyCode.DOWN) {
                this.moveDown = true;
            } else if (event.getCode() == KeyCode.LEFT) {
                this.moveLeft = true;
            } else if (event.getCode() == KeyCode.RIGHT) {
                this.moveRight = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                this.createBomb = 0;
            }

            if (event.getCode() == KeyCode.UP) {
                this.moveUp = false;
            } else if (event.getCode() == KeyCode.DOWN) {
                this.moveDown = false;
            }
            if (event.getCode() == KeyCode.LEFT) {
                this.moveLeft = false;
            } else if (event.getCode() == KeyCode.RIGHT) {
                this.moveRight = false;
            }
        });
    }
    public static void addBomb() {
        if (bombers.size() <= 0) {
            return;
        }
        Bomber bomber = (Bomber) bombers.get(0);
        if ((bomber.getCreateBomb() != 1) || bombs.size() >= bomber.getBomb()) {
            return;
        }

        Bomb bomb = new Bomb ((bomber.getX() + (int)(bomber.getImg().getWidth() / 2))/ Sprite.SCALED_SIZE,
                (bomber.getY() + (int)(bomber.getImg().getHeight() / 2)) / Sprite.SCALED_SIZE, Sprite.bomb.getFxImage());
        boolean add = true;
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).equals(bomb)) {
                add = false;
                break;
            }
        }
        if (add) {
            bombs.add(bomb);
        }
    }
    public static void addFlame(int xUnit, int yUnit) {
        if (bombers.size() == 0) {
            return;
        }

        Entity flame = new Flame (xUnit, yUnit, "epicenter");
        flames.add(flame);
        addFlame(xUnit, yUnit, -1, 0);
        addFlame(xUnit, yUnit, 1, 0);
        addFlame(xUnit, yUnit, 0, -1);
        addFlame(xUnit, yUnit, 0, 1);
    }

    private static void addFlame(int xUnit, int yUnit, int x, int y) {
        Bomber bomber = (Bomber) bombers.get(0);

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
            flames.add(flame);
        }
    }
    /**
     * Xoa nhung doi tuong co hp <= 0 khoi cac list
     */
    public static void removeDeadEntity() {
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).getHp() > 0) {
                break;
            }
            addFlame(bombs.get(i).getX() / Sprite.SCALED_SIZE, bombs.get(i).getY() / Sprite.SCALED_SIZE);
            bombs.remove(i--);
        }

        for (int i = 0; i < bombers.size(); i++) {
            if (bombers.get(i).getHp() <= 0) {
                deads.add(bombers.get(i));
                bombers.remove(i--);
            }
        }
    }

    public static void removeFinishedElements() {
        for (int i = 0; i < deads.size(); i++) {
            if (deads.get(i).getAnimation().isFinishDeadAnimation()) {
                deads.remove(i--);
            }
        }

        for (int i = 0; i < flames.size(); i++) {
            if (flames.get(i).getAnimation().isFinishDeadAnimation()) {
                flames.remove(i--);
            }
        }
    }

    public static List<Entity> getDeads() {
        return deads;
    }

    public static List<Entity> getBombers() {
        return bombers;
    }

    public static List<Entity> getFlames() {
        return flames;
    }

    public static List<Entity> getBombs() {
        return bombs;
    }
}
