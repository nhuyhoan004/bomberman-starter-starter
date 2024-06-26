package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.animation.FlameAnimation;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ListIterator;
import static uet.oop.bomberman.entities.EntityArr.*;


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
        checkEnemy ();
        checkBricks();
      /*  checkFlameItems();
        checkSpeedItems();
        checkBombItems();*/
    }
    public static void addFlame(int xUnit, int yUnit) {
        if (EntityArr.getBombers().size() == 0) {
            return;
        }

        Entity flame = new Flame (xUnit, yUnit, "epicenter");
        EntityArr.getFlames().add(flame);
        addFlame(xUnit, yUnit, -1, 0);
        addFlame(xUnit, yUnit, 1, 0);
        addFlame(xUnit, yUnit, 0, -1);
        addFlame(xUnit, yUnit, 0, 1);
    }

    private static void addFlame(int xUnit, int yUnit, int x, int y) {
        Bomber bomber = (Bomber) EntityArr.getBombers().get(0);

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
            // xử lí va chạm flame với tường
            for (int j = 0; j < EntityArr.getWalls().size(); j++) {
                if (flame.intersects(EntityArr.getWalls().get(j))) {
                    add = false;
                    break;
                }
            }
            if (add) {
                for (int j = 0; j < bricks.size(); j++) {
                    if (flame.intersects(bricks.get(j))) {
                        bricks.get(j).setHp(0);
                        add = false;
                        break;
                    }
                }
                for (int j = 0; j < enemies.size(); j++) {
                    if (flame.intersects(enemies.get(j))) {
                        enemies.get(j).setHp(0);
                        enemies.get(j).setAlive(false);
                        add = false;
                        break;
                    }
                }
            }
            if (!add) {
                break;
            }
            EntityArr.getFlames().add(flame);
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

        for (int i = 0; i < EntityArr.getBricks().size(); i++) {
            if (EntityArr.getBricks().get(i).getHp() <= 0) {
                deads.add(bricks.get(i));
                bricks.remove(i--);
            }
        }

        for (int i = 0; i < EntityArr.getEnemies().size(); i++) {
            if (EntityArr.getEnemies().get(i).getHp() <= 0) {
                deads.add(enemies.get(i));
                enemies.remove(i--);
                Sound.enemy_dead.play ();
            }
        }

        for (int i = 0; i < bombers.size(); i++) {
            if (bombers.get(i).getHp() <= 0) {
                deads.add(bombers.get(i));
                bombers.remove(i--);
                Sound.dead.play();
                Sound.game_over.play();
            }
        }
    }


    public static void removeFinishedElements() {
        for (int i = 0; i < EntityArr.getDeads().size(); i++) {
            if (EntityArr.getDeads().get(i).getAnimation().isFinishDeadAnimation()) {
                EntityArr.getDeads().remove(i--);
            }
        }

        for (int i = 0; i < EntityArr.getFlames().size(); i++) {
            if (EntityArr.getFlames().get(i).getAnimation().isFinishDeadAnimation()) {
                EntityArr.getFlames().remove(i--);
            }
        }
    }
    public void checkEnemy() {
        ListIterator<Entity> enemyIterator = enemies.listIterator();
        while (!enemies.isEmpty() && enemyIterator.hasNext()) {
            Entity enemyNext = enemyIterator.next();
            if (this.intersects(enemyNext)) {
                enemyNext.setImg(Sprite.grass.getFxImage());
                enemyIterator.remove();
            }
        }
    }

    public void checkBricks() {
        ListIterator<Entity> brickIterator = bricks.listIterator();
        while (!bricks.isEmpty() && brickIterator.hasNext()) {
            Entity brickNext = brickIterator.next();
            if (this.intersects(brickNext)) {
                brickNext.setImg(Sprite.grass.getFxImage());
                brickIterator.remove();
            }
        }
    }

}