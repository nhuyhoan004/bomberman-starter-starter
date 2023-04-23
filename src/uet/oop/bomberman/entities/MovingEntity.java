package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.entities.Entity;

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
        return wallPass;
    }

    public void setWallPass(boolean wallPass) {
        this.wallPass = wallPass;
    }

    public boolean isAbleToMoveLeft() {
        return ableToMoveLeft;
    }

    public boolean isAbleToMoveRight() {
        return ableToMoveRight;
    }

    public boolean isAbleToMoveUp() {
        return ableToMoveUp;
    }

    public boolean isAbleToMoveDown() {
        return ableToMoveDown;
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

    public MovingEntity(boolean life) {
        this.life = life;
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
     * @return neu va cham tra ve true, con lai tra ve false
     */


    /**
     * Kiem tra xem mot nhan vat co duoc di sang trai, sang phai, ... hay khong
     */
    public void checkObjectMovementAbility(Entity obj) {
        boolean left1, left2, right1, right2, up1, up2, down1, down2;

        if (this instanceof Bomber) {
            left1 = intersects(obj, this.getX() + 1, this.getY() + 4);
            left2 = intersects(obj, this.getX() + 1, this.getY() + this.getImg().getHeight() - 4);
            right1 = intersects(obj, this.getX() + this.getImg().getWidth() - 7 , this.getY() + 4);
            right2 = intersects(obj, this.getX() + this.getImg().getWidth() - 7, this.getY() + this. getImg().getHeight() - 4);
            up1 = intersects(obj, this.getX() + 4, this.getY() + 2);
            up2 = intersects(obj, this.getX() + this.getImg().getWidth() - 10, this .getY() + 2);
            down1 = intersects(obj, this.getX() + 4, this.getY() + this.getImg().getHeight() - 2);
            down2 = intersects(obj, this.getX() + this.getImg().getWidth() - 10, this .getY() + this.getImg().getHeight() - 2);
        } else {
            left1 = intersects(obj, this.getX() , this.getY() + 1);
            left2 = intersects(obj, this.getX() , this.getY() + this.getImg().getHeight() - 1);
            right1 = intersects(obj, this.getX() + this.getImg().getWidth() , this.getY() + 1);
            right2 = intersects(obj, this.getX() + this.getImg().getWidth(), this.getY() + this. getImg().getHeight() - 1);
            up1 = intersects(obj, this.getX() + 1, this.getY());
            up2 = intersects(obj, this.getX() + this.getImg().getWidth() - 1, this .getY());
            down1 = intersects(obj, this.getX() + 1, this.getY() + this.getImg().getHeight());
            down2 = intersects(obj, this.getX() + this.getImg().getWidth() - 1, this .getY() + this.getImg().getHeight());
        }

        if (this.ableToMoveLeft) {
            if (left1 || left2) {
                this.ableToMoveLeft = false;
            }
        }
        if (this.ableToMoveRight) {
            if (right1 || right2) {
                this.ableToMoveRight = false;
            }
        }
        if (this.ableToMoveUp) {
            if (up1 || up2) {
                this.ableToMoveUp = false;
            }
        }
        if (this.ableToMoveDown) {
            if (down1 || down2) {
                this.ableToMoveDown = false;
            }
        }
    }

    @Override
    public void update() {

    }
    public boolean checkBoundsBrick() {
        for (Entity e : EntityArr.bricks) {
            if (this.intersects(e)) return true;
        }
        return false;
    }

    public boolean checkBoundsBomb() {
        for (Entity e : EntityArr.bombs) {
            if (this.intersects(e)) return true;
        }
        return false;
    }

    public boolean checkBoundsWall() {
        for (Entity e : EntityArr.walls) {
            if (this.intersects(e)) return true;
        }
        return false;
    }
}
