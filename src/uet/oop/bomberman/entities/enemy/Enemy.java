package uet.oop.bomberman.entities.enemy;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.animation.EnemyAnimation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.image.Image;
import uet.oop.bomberman.sound.Sound;

import java.util.ListIterator;
import java.util.Random;

import static uet.oop.bomberman.BombermanGame.bomber;
import static uet.oop.bomberman.entities.EntityArr.enemies;

public abstract class Enemy extends Entity {
    private int speed = 1;
    private int speedX = this.speed;
    private int speedY = 0;
    private boolean isAlive = true;
    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int UP = 3;

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        animation = new EnemyAnimation();
    }

    @Override
    public void update() {
        animate += Sprite.DEFAULT_SIZE / 10;
        removeEnemy();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    protected void direction(int num) {
        switch (num) {
            case RIGHT:
                this.speedX = this.getSpeed();
                this.speedY = 0;
                break;
            case LEFT:
                this.speedX = this.getSpeed() * -1;
                this.speedY = 0;
                break;
            case DOWN:
                this.speedY = this.getSpeed();
                this.speedX = 0;
                break;
            case UP:
                this.speedY = this.getSpeed() * -1;
                this.speedX = 0;
                break;
        }
    }

    public void randomDirection() {
        Random rd = new Random();
        int n = rd.nextInt(4);
        direction(n);
    }
    public int sameRow() {
        if (getX() > bomber.getX()) {
            return LEFT;
        }
        else if (getX() < bomber.getX()) {
            return RIGHT;
        }
        return -1;
    }

    public int sameColumn() {
        if (getY() > bomber.getY()) {
            return UP;
        }
        else if (getY() < bomber.getY()) {
            return DOWN;
        }
        return -1;
    }

    public void chaseBomber() {
        if (getY() == bomber.getY()) {
            direction(sameRow());
        } else if (getX() == bomber.getX()) {
            direction(sameColumn());
        }
    }

    public void removeEnemy() {
        this.animation.setSprite(this);
    }
}
