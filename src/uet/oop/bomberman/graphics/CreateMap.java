package uet.oop.bomberman.graphics;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntityArr;
import uet.oop.bomberman.entities.block.Brick;
import uet.oop.bomberman.entities.block.Grass;
import uet.oop.bomberman.entities.block.Portal;
import uet.oop.bomberman.entities.block.Wall;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.entities.enemy.*;
import uet.oop.bomberman.entities.items.BombItem;
import uet.oop.bomberman.entities.items.FlameItem;
import uet.oop.bomberman.entities.items.SpeedItem;

import java.io.File;
import java.io.FileNotFoundException;
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
            Scanner scanner = new Scanner(file);
            int Level = scanner.nextInt();
            height = scanner.nextInt();
            width = scanner.nextInt();

            String line = scanner.nextLine();
            for (int i = 0; i < height; i++) {
                line = scanner.nextLine();
                map.add(line);
            }
            scanner.close();
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
                        // balloom
                        object = new Balloom (j, i, Sprite.balloom_left1.getFxImage ());
                    } else if (c == '2') {
                        // oneal
                        object = new Oneal (j, i, Sprite.oneal_right1.getFxImage ());
                    } else if (c == '3') {
                        // doll
                        object = new Doll (j, i, Sprite.doll_left2.getFxImage ());
                    } else if (c == '4') {
                        // minvo
                        object = new Minvo (j, i, Sprite.minvo_right2.getFxImage ());
                    } else if (c == '5') {
                        // kondoria
                        object = new Kondoria (j, i, Sprite.kondoria_right2.getFxImage ());
                    }
                    enemies.add (object);
                }
                // brick
                else if (c == '*') {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        bricks.add(object);
                    }
                // portal
                else if (c == 'x') {
                    object = new Portal(j, i, Sprite.grass.getFxImage());
                    portals.add(object);
                }
                // wall
                else if (c == '#') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                    EntityArr.walls.add(object);
                }
                // bomb item
                else if (c == 'b') {
                    object = new BombItem(j, i, Sprite.brick.getFxImage());
                    bombItems.add(object);
                }
                // flame item
                else if (c == 'f') {
                    object = new FlameItem(j, i, Sprite.brick.getFxImage());
                    flameItems.add(object);
                }
                // speed item
                else if (c == 's') {
                    object = new SpeedItem(j, i,Sprite.brick.getFxImage());
                    speedItems.add(object);
                }
            }
        }
    }
}

