package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.animation.Animation;
import uet.oop.bomberman.animation.OnealAnimation;
import uet.oop.bomberman.graphics.Sprite;
public abstract class Entity {
    protected int x;
    protected int y;
    protected Image img;
    protected Animation animation;
    private boolean isAlive = true;
    protected int animate;
    protected int hp = 1;

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp < 0) {
            hp = 0;
        }
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int xUnit) {
        this.x = xUnit * Sprite.SCALED_SIZE;
    }

    public void setY(int yUnit) {
        this.y = yUnit * Sprite.SCALED_SIZE;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public Animation getAnimation() {
        return animation;
    }

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
        this.animate = Sprite.DEFAULT_SIZE;
    }

    public Entity() {}

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();
    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
    }
    public boolean intersects1(Entity e) {
        return e.getBoundary().intersects(this.getBoundary());
    }
    public boolean intersects(Entity obj) {
        return intersects(this, obj.getX() + obj.getImg().getWidth() / 2 , obj.getY() + obj.getImg().getHeight() / 2);
    }

    /**
     * Ham kiem tra xem mot diem co nam trong doi tuong ko
     * @param entity doi tuong
     * @param x toa do x
     * @param y toa do y
     * @return true neu co, false neu ko
     */
    public static boolean intersects(Entity entity, double x, double y) {
        return (entity.getX() <= x && x <= (entity.getX() + entity.getImg().getWidth())
                && entity.getY() <= y && y <= (entity.getY() + entity.getImg().getHeight()));
    }
    public boolean checkBoundsBrick() {
        for (Entity e : EntityArr.bricks) {
            if (this.intersects1(e)) return true;
        }
        return false;
    }

    public boolean checkBoundsBomb() {
        for (Entity e : EntityArr.bombs) {
            if (this.intersects1(e)) return true;
        }
        return false;
    }

    public boolean checkBoundsWall() {
        for (Entity e : EntityArr.walls) {
            if (this.intersects1(e)) return true;
        }
        return false;
    }

    public boolean isAlive() {
        return isAlive;
    }
    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
