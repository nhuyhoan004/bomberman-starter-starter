package uet.oop.bomberman.entities;

import java.util.List;
import java.util.ArrayList;
public class EntityArr {
    private static List<Entity> bombers = new ArrayList<> ();
    private static List<Entity> bombs = new ArrayList<>();
    private static List<Entity> deads = new ArrayList<>();
    private static List<Entity> flames = new ArrayList<>();
    private static List<Entity> grasses = new ArrayList<>();
    private static List<Entity> walls = new ArrayList<>();
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
}
