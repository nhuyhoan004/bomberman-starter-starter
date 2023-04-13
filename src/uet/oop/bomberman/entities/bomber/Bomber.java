package uet.oop.bomberman.entities.bomber;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.animation.Animation;
import uet.oop.bomberman.animation.BomberAnimation;
import uet.oop.bomberman.entities.MovingEntity;

public class Bomber extends MovingEntity {
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
}
