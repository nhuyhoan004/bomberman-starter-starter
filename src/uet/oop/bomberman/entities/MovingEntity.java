package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.bomber.Bomber;

public abstract class MovingEntity extends Entity {
    protected int speed = 1;

    protected boolean wallPass = false;
    protected boolean moveLeft = false;
    protected boolean moveRight = false;
    protected boolean moveUp = false;
    protected boolean moveDown = false;

    protected boolean ableToMoveLeft = true;
    protected boolean ableToMoveRight = true;
    protected boolean ableToMoveUp = true;
    protected boolean ableToMoveDown = true;


    protected int isMove; // jump with pixel
    protected int swap; // swap image
    protected String direction; //dirirection of player
    protected int count; // count step of a jump
    protected int countToRun; // run after count frame
    protected boolean life; // life of enemy


    public boolean isWallPass() {
        return !wallPass;
    }
    public MovingEntity() {}

    public MovingEntity(int isMove, int swap, String direction, int count, int countToRun) {
        this.isMove = isMove;
        this.swap = swap;
        this.direction = direction;
        this.count = count;
        this.countToRun = countToRun;
    }

    public MovingEntity(int x, int y, Image img) {
        super( x, y, img);
    }

    public boolean isLife() {
        return life;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int  newSpeed) {
        if (0 < newSpeed && newSpeed <= 2) {
            this.speed = newSpeed;
        }
    }

    /**
     * di chuyen.
     */
    protected void move() {
        if (this.hp <= 0) {
            return;
        }
        if (moveLeft && this.ableToMoveLeft) {
            this.x -= this.getSpeed();
        } else if (moveRight && this.ableToMoveRight) {
            this.x += this.getSpeed();
        } else if (moveUp && this.ableToMoveUp) {
            this.y -= this.getSpeed();
        } else if (moveDown && this.ableToMoveDown) {
            this.y += this.getSpeed();
        }
    }


    /**
     * Ham kiem tra va cham giua 2 doi tuong.
     * @param obj doi tuong bat ki
     *  neu va cham tra ve true, con lai tra ve false
     * Kiem tra xem mot nhan vat co duoc di sang trai, sang phai, ... hay khong
     */
    public void checkObjectMovementAbility(Entity obj) {
        boolean left1, left2, right1, right2, up1, up2, down1, down2;

        int imgWidth = (int) this.getImg().getWidth();
        int imgHeight = (int) this.getImg().getHeight();
        int x = this.getX();
        int y = this.getY();

        if (this instanceof Bomber) {
            left1 = intersects(obj, x + 1, y + 4);
            left2 = intersects(obj, x + 1, y + imgHeight - 4);
            right1 = intersects(obj, x + imgWidth - 7, y + 4);
            right2 = intersects(obj, x + imgWidth - 7, y + imgHeight - 4);
            up1 = intersects(obj, x + 4, y + 2);
            up2 = intersects(obj, x + imgWidth - 10, y + 2);
            down1 = intersects(obj, x + 4, y + imgHeight - 2);
            down2 = intersects(obj, x + imgWidth - 10, y + imgHeight - 2);
        } else {
            left1 = intersects(obj, x, y + 1);
            left2 = intersects(obj, x, y + imgHeight - 1);
            right1 = intersects(obj, x + imgWidth, y + 1);
            right2 = intersects(obj, x + imgWidth, y + imgHeight - 1);
            up1 = intersects(obj, x + 1, y);
            up2 = intersects(obj, x + imgWidth - 1, y);
            down1 = intersects(obj, x + 1, y + imgHeight);
            down2 = intersects(obj, x + imgWidth - 1, y + imgHeight);
        }

        if (obj.isAlive()) {
            this.ableToMoveLeft = this.ableToMoveLeft && !left1 && !left2;
            this.ableToMoveRight = this.ableToMoveRight && !right1 && !right2;
            this.ableToMoveUp = this.ableToMoveUp && !up1 && !up2;
            this.ableToMoveDown = this.ableToMoveDown && !down1 && !down2;
        }
    }
    @Override
    public void update() {
    }
}
