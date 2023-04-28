package uet.oop.bomberman.entities;

import uet.oop.bomberman.entities.block.Brick;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.sound.Sound;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class EntityArr {
    public static final List<Entity> bombers = new ArrayList<> ();
    public static final List<Entity> bombs = new ArrayList<>();
    public static final List<Entity> deads = new ArrayList<>();
    public static final List<Entity> flames = new ArrayList<>();
    public static final List<Entity> grasses = new ArrayList<>();
    public static final List<Entity> walls = new ArrayList<>();
    public static final List<Entity> portals = new ArrayList<>();
    public static final List<Entity> bricks = new ArrayList<>();
    public static final List<Entity> enemies = new ArrayList<>();
    public static final List<String> mapList = new ArrayList<>();
    public static final List<String> map = new ArrayList<>();
    public static final List<Entity> flameItems = new ArrayList<>();
    public static final List<Entity> speedItems = new ArrayList<>();

    public static List<Entity> getDeads() {
        return deads;
    }

    public static List<Entity> getBombers() {
        return bombers;
    }

    public static List<Entity> getFlames() {
        return flames;
    }

    public static List<Entity> getBombs() {
        return bombs;
    }

    public static List<Entity> getWalls() {
        return walls;
    }

    public static List<Entity> getGrasses() {
        return grasses;
    }

    public static List<Entity> getBricks() {
        return bricks;
    }
    public static void removeEnemy() {
        ListIterator<Entity> enemyIterator = enemies.listIterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = (Enemy) enemyIterator.next();
            if (!enemy.isAlive()) {
                Sound.play("AA126_11");
                enemyIterator.remove();
            }
        }
    }

}
