package uet.oop.bomberman.entities.enemy;

import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.image.Image;

public class Balloom extends Enemy {
    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (isAlive()) {
            if (this.getSpeedX() == 0) {
                this.y += this.getSpeedY();
                if (checkBoundsWall() || checkBoundsBomb() || checkBoundsBrick() || getY() % Sprite.SCALED_SIZE == 0) {
                    if (getY() % Sprite.SCALED_SIZE != 0) {
                        this.y -= this.getSpeedY();
                    }
                    this.randomDirection();
                }
            } else {
                this.x += this.getSpeedX();
                if (checkBoundsBrick() || checkBoundsBomb() || checkBoundsWall() || getX() % Sprite.SCALED_SIZE == 0) {
                    if (getX() % Sprite.SCALED_SIZE != 0) {
                        this.x -= this.getSpeedX();
                    }
                    this.randomDirection();
                }
            }
        } else {
            this.img = Sprite.balloom_dead.getFxImage();
        }

        if (isAlive()) {
            if (this.getSpeedX() > 0) {
                this.img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3
                        , this.x, Sprite.DEFAULT_SIZE).getFxImage();
            } else if (this.getSpeedX() < 0) {
                this.img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3
                        , this.x, Sprite.DEFAULT_SIZE).getFxImage();
            } else if (this.getSpeedY() > 0) {
                this.img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3
                        , this.y, Sprite.DEFAULT_SIZE).getFxImage();
            } else {
                this.img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3
                        , this.y, Sprite.DEFAULT_SIZE).getFxImage();
            }
        } else {
            this.img = Sprite.balloom_dead.getFxImage();
        }
    }
}
