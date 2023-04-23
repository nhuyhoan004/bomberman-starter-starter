package uet.oop.bomberman.entities.enemy;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntityArr;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.Random;

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
    }

    @Override
    public void update() {
        animate += Sprite.DEFAULT_SIZE / 10;
    };

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
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
        int n = rd.nextInt(2);
        direction(n);
    }
    public void randomSpeed() {
        Random random = new Random();
        int num = random.nextInt(10);
        if (num <= 5) {
            this.setSpeed(1);
        } else {
            this.setSpeed(2);
        }
    }

}
