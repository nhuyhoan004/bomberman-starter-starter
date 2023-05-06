package uet.oop.bomberman.entities.enemy;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import static uet.oop.bomberman.BombermanGame.bomber;

public class Doll extends Enemy {
    public Doll(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (isAlive()) {
            int distanceX = Math.abs(getX() - bomber.getX());
            int distanceY = Math.abs(getY() - bomber.getY());
            if (this.getSpeedX() == 0) {
                this.y += this.getSpeedY();
                if (distanceX <= Sprite.SCALED_SIZE  && distanceY <= Sprite.SCALED_SIZE ) {
                    this.chaseBomber();
                    if (checkBoundsBrick() || checkBoundsBomb() || checkBoundsWall()) {
                        this.y -= this.getSpeedY();
                        this.chaseBomber();
                    }
                } else if (checkBoundsBrick() || checkBoundsBomb() || checkBoundsWall() || getY() % Sprite.SCALED_SIZE == 0) {
                    if (checkBoundsBrick() || checkBoundsBomb() || checkBoundsWall()) {
                        this.y -= this.getSpeedY();
                    }
                    this.randomDirection();
                }
            } else {
                this.x += this.getSpeedX();
                if (distanceX <= Sprite.SCALED_SIZE  && distanceY <= Sprite.SCALED_SIZE ) {
                    this.chaseBomber();
                    if (checkBoundsBrick() || checkBoundsBomb() || checkBoundsWall()) {
                        this.x -= this.getSpeedX();
                        this.chaseBomber();
                    }
                } else if (checkBoundsBrick() || checkBoundsBomb() || checkBoundsWall() || getX() % Sprite.SCALED_SIZE == 0) {
                    if (checkBoundsBrick() || checkBoundsBomb() || checkBoundsWall()) {
                        this.x -= this.getSpeedX();
                    }
                    this.randomDirection();
                }
            }
        } else {
            this.setImg(Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3
                    , this.animate, Sprite.DEFAULT_SIZE).getFxImage());
        }
        if (isAlive()) {
            if (this.getSpeedX() > 0) {
                this.img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2
                        , Sprite.doll_right3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
            } else if (this.getSpeedX() < 0){
                this.img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2
                        , Sprite.doll_left3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
            } else if (this.getSpeedY() > 0) {
                this.img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2
                        , Sprite.doll_right3, this.y, Sprite.DEFAULT_SIZE).getFxImage();
            } else {
                this.img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2
                        , Sprite.doll_left3, this.y, Sprite.DEFAULT_SIZE).getFxImage();
            }
        } else {
            this.img = Sprite.doll_dead.getFxImage();
        }
    }
}
