package uet.oop.bomberman.graphics;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntityArr;
import uet.oop.bomberman.entities.block.Brick;
import uet.oop.bomberman.entities.block.Grass;
import uet.oop.bomberman.entities.block.Portal;
import uet.oop.bomberman.entities.block.Wall;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.entities.enemy.Balloon;
import uet.oop.bomberman.entities.enemy.Doll;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.sound.Sound;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;
import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.entities.EntityArr.*;

public class CreateMap {

    /** Thêm map vào danh sách maplist.
     *
     */
    public static void loadMapListFromFile() {
        mapList.clear();
        try {
            File file = new File("res/levels/mapList.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                mapList.add(line);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Doc du lieu tu file txt va ghi vao list map.
     */
    public static void readDataFromFile(int _level) {
        map.clear();

        try {
            if (_level >= mapList.size()) {
                win = true;
                return;
            }

            File file = new File(mapList.get(_level));
            Scanner scaner = new Scanner(file);
            int Level = scaner.nextInt();
            height = scaner.nextInt();
            width = scaner.nextInt();

            String line = scaner.nextLine();
            for (int i = 0; i < height; i++) {
                line = scaner.nextLine();
                map.add(line);
            }
            scaner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * tao map tu du lieu trong list map
     */
    public static void createMap() {
        EntityArr.getFlames().clear();
        EntityArr.getBombers().clear();
        EntityArr.getBombs().clear();
        EntityArr.walls.clear();
        grasses.clear();
        portals.clear();

        for (int i = 0; i < height; i++) {
            String s = map.get(i);
            for (int j = 0; j < width; j++) {
                Entity object = new Grass(j, i, Sprite.grass.getFxImage());
                grasses.add(object);
                char c = s.charAt(j);

                if (c == 'p') {
                    if (EntityArr.getBombers().size() != 0) {
                        EntityArr.getBombers ().get(0).setX(j);
                        EntityArr.getBombers ().get(0).setY(i);
                    }
                    else {
                        object = new Bomber(j, i, Sprite.player_down.getFxImage());
                        EntityArr.getBombers().add(object);
                    }
                }
                else if ('0' <= c && c <= '9') {
                    if (c == '1') {
                        object = new Balloon (j, i, Sprite.balloom_left1.getFxImage ());
                    } else if (c == '2') {
                        object = new Doll(j,i, Sprite.doll_left2.getFxImage());
                    }
                    enemies.add (object);
                } else if (c == '*') {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        bricks.add(object);
                    }
                // portal
                else if (c == 'x') {
                    object = new Portal(j, i);
                    if (object.isAlive()) {
                        object.setImg(Sprite.portal.getFxImage());
                    } else {
                        object.setImg(Sprite.grass.getFxImage());
                    }
                    portals.add(object);
                }
                // wall
                else if (c == '#') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                    EntityArr.walls.add(object);
                }
            }
        }
    }
}

