package uet.oop.bomberman.animation;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

import java.util.List;

public abstract class Animation {
    private int count = 0;
    protected int countDeadFrames = 0;
    protected int numberOfFrames = 8;
    protected int numberOfDeadFrames = 32;
    protected boolean finishDeadAnimation = false;
    private String id;

    public boolean isFinishDeadAnimation() {
        return finishDeadAnimation;
    }

    /**
     * Truyen vao mot danh sach cac anh va sau đó tra ve anh duoc chon.
     * sau numberOfFrames khung hình thì sẽ thay 1 anh moi. den het thi lai quay ve anh dau
     * @param list danh sach cac anh
     * @return
     */
    protected Image handle(List<Image> list, String s) {
        if (id == null || (!(id.equals(s)))) {
            id = s;
            count = 0;
        }
        Image img = (list.get(count / numberOfFrames));
        count++;
        if (count >= numberOfFrames * list.size()) {
            count = 0;
        }
        return img;
    }

    public abstract void setSprite(Entity entity);
}
