package uet.oop.bomberman.entities.bomber;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.animation.BomberAnimation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.sound.Sound;


public class Bomber extends Entity {


    private int createBomb = 0;
    private int speed = 1;

    protected boolean wallPass = false;
    protected boolean moveLeft = false;
    protected boolean moveRight = false;
    protected boolean moveUp = false;
    protected boolean moveDown = false;

    protected boolean ableToMoveLeft = true;
    protected boolean ableToMoveRight = true;
    protected boolean ableToMoveUp = true;
    protected boolean ableToMoveDown = true;

    public void update() {
        this.move();
        this.animation.setSprite(this);
        this.ableToMoveDown = true;
        this.ableToMoveLeft = true;
        this.ableToMoveRight = true;
        this.ableToMoveUp = true;
    }
    public boolean isWallPass() {
        return !wallPass;
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

    public int getCreateBomb() {
        return createBomb;
    }

    private int bomb = 1;
    private int flame = 1;



    public Bomber() {}

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
    /**
     * Xu ly khi cac phim duoc nhan, nha.
     */
    public void handleKeyPress(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.SPACE || code == KeyCode.X) {
                this.createBomb++;
                Sound sound = new Sound("SPACE");
                sound.play();
                if (this.createBomb > 100000) {
                    this.createBomb = 10;
                }
            } else if (code == KeyCode.UP || code == KeyCode.W) {
                this.moveUp = true;
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                this.moveDown = true;
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                this.moveLeft = true;
            } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
                this.moveRight = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.SPACE || code == KeyCode.X) {
                this.createBomb = 0;
            } else if (code == KeyCode.UP || code == KeyCode.W) {
                this.moveUp = false;
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                this.moveDown = false;
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                moveLeft = false;
            } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
                this.moveRight = false;
            }
        });
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
     * phuong thuc di chuyen.
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

        left1 = intersects (obj, x + 1, y + 4);
        left2 = intersects(obj, x + 1, y + imgHeight - 4);
        right1 = intersects(obj, x + imgWidth - 7, y + 4);
        right2 = intersects(obj, x + imgWidth - 7, y + imgHeight - 4);
        up1 = intersects(obj, x + 4, y + 2);
        up2 = intersects(obj, x + imgWidth - 10, y + 2);
        down1 = intersects(obj, x + 4, y + imgHeight - 2);
        down2 = intersects(obj, x + imgWidth - 10, y + imgHeight - 2);

        if (obj.isAlive()) {
            this.ableToMoveLeft = this.ableToMoveLeft && !left1 && !left2;
            this.ableToMoveRight = this.ableToMoveRight && !right1 && !right2;
            this.ableToMoveUp = this.ableToMoveUp && !up1 && !up2;
            this.ableToMoveDown = this.ableToMoveDown && !down1 && !down2;
        }
    }
}