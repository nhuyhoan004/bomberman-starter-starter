package uet.oop.bomberman.graphics;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

import static uet.oop.bomberman.BombermanGame.*;
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
        if (getIsGameComplete()) {
            Image waitToNext = new Image("images/levelUp.png");
            authorView.setImage(waitToNext);
            int now = 3;
            while (now > 0) {
                wait = true;
                now--;
            }
            if (now == 0) {
                switch (level) {
                    case 0:
                        for (Entity portal : portals) {
                            portal.setAlive(true);
                        }
                        CreateMap.readDataFromFile(1);
                        CreateMap.createMap();
                        break;
                    case 1:
                        for (Entity portal : portals) {
                            portal.setAlive(true);
                        }
                        CreateMap.readDataFromFile(2);
                        CreateMap.createMap();
                        break;
                }
                wait = false;
            }
        }
    }
}
