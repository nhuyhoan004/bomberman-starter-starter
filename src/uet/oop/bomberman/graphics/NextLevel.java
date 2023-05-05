package uet.oop.bomberman.graphics;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.controls.Menu.updateMenu;
import static uet.oop.bomberman.entities.EntityArr.mapList;
import static uet.oop.bomberman.entities.EntityArr.portals;


public class NextLevel {
    private static boolean wait;
    public static long waitingTime;

    public static void setWait(boolean wait) {
        NextLevel.wait = wait;
    }

    public boolean getWait() {
        return wait;
    }

    public static void waitToLevelUp() {
        if (level < mapList.size() && isGameComplete()) {
            Image waitToNext = new Image("images/levelUp.png");
            authorView.setImage(waitToNext);
            long now = System.currentTimeMillis();
            if (now - waitingTime > 40) {
                Image transparent = new Image("images/transparent.png");
                authorView.setImage(transparent);
                CreateMap.loadMapListFromFile();
                CreateMap.readDataFromFile(++level);
                CreateMap.createMap();
                running = true;
//                System.out.println("work");
            }
        } else if (isGameComplete()) {
            Image victory = new Image("images/author.png");
            authorView.setImage(victory);
            bomber.setAlive(false);
        }
    }
}
