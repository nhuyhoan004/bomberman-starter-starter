package uet.oop.bomberman.entities;


import java.util.List;
import java.util.ArrayList;
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
    public static final List<Entity> bombItems = new ArrayList<>();

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

    public static List<Entity> getEnemies() {
        return enemies;
    }

}
