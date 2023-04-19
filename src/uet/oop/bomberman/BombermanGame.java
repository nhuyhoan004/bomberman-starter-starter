package uet.oop.bomberman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.block.Grass;
import uet.oop.bomberman.entities.block.Wall;
import uet.oop.bomberman.entities.bomber.Bomb;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    private Group root;
    private Scene scene;
    private boolean win = false;
    private boolean lose = false;

    private boolean isGameComplete = false;
    private int level = 0;
    public static final String TITLE = "Bomberman by group 07";
    public static int width;
    public static int height;

    private GraphicsContext gc;
    public static List<Entity> block = new ArrayList<>();
    public static int[][] killList;
    private Canvas canvas;
    private List<String> mapList = new ArrayList<>();
    private List<String> map = new ArrayList<>();
    private List<Entity> grasses = new ArrayList<>();
    private List<Entity> walls = new ArrayList<>();
    private List<Entity> portals = new ArrayList<>();
    private List<Entity> bombers = new ArrayList<>();
    private List<Entity> bombs = new ArrayList<>();
    private List<Entity> deads = new ArrayList<>();
    private List<Entity> flames = new ArrayList<>();

    public static void main(String[] args) {
        Sound.play ("soundtrack");
        Application.launch(BombermanGame.class);
    }

    public void start(Stage stage) {
        this.loadMapListFromFile();
        this.readDataFromFile();
        stage.setTitle(BombermanGame.TITLE);
        stage.setResizable(false);

        canvas = new Canvas(Sprite.SCALED_SIZE * width, Sprite.SCALED_SIZE * height);
        gc = canvas.getGraphicsContext2D();
        root = new Group();
        root.getChildren().add(canvas);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();

                if (lose) {
                    return;
                }

                if (win) {
                    return;
                }

                if (isGameComplete()) {
                    readDataFromFile();

                    canvas.setHeight(Sprite.SCALED_SIZE * height);
                    canvas.setWidth(Sprite.SCALED_SIZE * width);
                    gc = canvas.getGraphicsContext2D();
                    root = new Group();
                    root.getChildren().add(canvas);
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    createMap(); // Tạo bản đồ mới
                }
            }
        };
        timer.start();

        createMap();
    }

    /**
     * tao map tu du lieu trong list map
     */
    public void createMap() {
        flames.clear();
        deads.clear();
        bombs.clear();
        walls.clear();
        grasses.clear();
        portals.clear();

        for (int i = 0; i < height; i++) {
            String s = map.get(i);
            for (int j = 0; j < width; j++) {
                Entity object = new Grass(j, i, Sprite.grass.getFxImage());
                grasses.add(object);
                char c = s.charAt(j);

                if (c == 'p') {
                    if (bombers.size() != 0) {
                        bombers.get(0).setX(j);
                        bombers.get(0).setY(i);
                    }
                    else {
                        object = new Bomber(j, i, Sprite.player_down.getFxImage());
                        bombers.add(object);
                    }
                }
                else {
                    if (c == '#') {
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                        walls.add(object);
                    }
                }
            }
        }
    }

    public void update() {
        bombers.forEach(Entity::update);
        deads.forEach(Entity::update);
        bombs.forEach(Entity::update);
        flames.forEach(Entity::update);

        for (int i = 0; i < bombers.size(); i++) {
            ((Bomber)bombers.get(i)).handleKeyPress(this.scene);
        }
        for (int i = 0; i < bombs.size(); i++) {
            for (int j = 0; j < bombers.size(); j++) {
                Bomber bomber = (Bomber) bombers.get(j);
                ((Bomb)bombs.get(i)).checkCharacterPassability(bomber);
            }
        }

        this.addBomb();
        this.handleCollision();
        this.removeFinishedElements();
        removeDeadEntity();

        if (bombers.size() == 0 && deads.size() == 0) {
            lose = true;
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grasses.forEach(g -> g.render(gc));
        portals.forEach(g -> g.render(gc));
        walls.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        deads.forEach(g -> g.render(gc));
        flames.forEach(g -> g.render(gc));
        bombers.forEach(g -> g.render(gc));
    }

    private void loadMapListFromFile() {
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
    private void readDataFromFile() {
        map.clear();

        try {
            if (level >= mapList.size()) {
                win = true;
                return;
            }

            File file = new File(mapList.get(level++));
            Scanner scaner = new Scanner(file);
            int L;
            L = scaner.nextInt();
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
     * Xu ly va cham (tat ca moi va cham deu xu ly o day).
     */
    public void handleCollision() {
        // Nhan vat
        ///////////////////////////////////////////////////////
        if (bombers.size() > 0) {
            Bomber bomber = (Bomber) bombers.get(0);

            for (int i = 0; i < flames.size(); i++) {
                if (bomber.intersects(flames.get(i))) {
                    bomber.setHp(0);
                    break;
                }
            }

            for (int i = 0; i < bombs.size(); i++) {
                if (((Bomb)bombs.get(i)).isPassable()) {
                    continue;
                }
                bomber.checkObjectMovementAbility(bombs.get(i));
            }
            for (int i = 0; i < walls.size(); i++) {
                bomber.checkObjectMovementAbility(walls.get(i));
            }
        }
        // Bomb
        for (int i = 0; i < bombs.size(); i++) {
            for (int j = 0; j < flames.size(); j++) {
                if (bombs.get(i).intersects(flames.get(j))) {
                    bombs.get(i).setHp(0);
                    break;
                }
            }
        }
    }

    /**
     * Xoa nhung doi tuong co hp <= 0 khoi cac list
     */
    public void removeDeadEntity() {
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).getHp() > 0) {
                break;
            }
            this.addFlame(bombs.get(i).getX() / Sprite.SCALED_SIZE, bombs.get(i).getY() / Sprite.SCALED_SIZE);
            bombs.remove(i--);
        }

        for (int i = 0; i < bombers.size(); i++) {
            if (bombers.get(i).getHp() <= 0) {
                deads.add(bombers.get(i));
                bombers.remove(i--);
            }
        }
    }

    /**
     * Them bomb khi nhan duoc lenh tu ban phim va co du dieu kien.
     */
    public void addBomb() {
        if (bombers.size() <= 0) {
            return;
        }
        Bomber bomber = (Bomber) bombers.get(0);
        if ((bomber.getCreateBomb() != 1) || bombs.size() >= bomber.getBomb()) {
            return;
        }

        Bomb bomb = new Bomb ((bomber.getX() + (int)(bomber.getImg().getWidth() / 2))/ Sprite.SCALED_SIZE,
                (bomber.getY() + (int)(bomber.getImg().getHeight() / 2)) / Sprite.SCALED_SIZE, Sprite.bomb.getFxImage());
        boolean add = true;
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).equals(bomb)) {
                add = false;
                break;
            }
        }
        if (add) {
            bombs.add(bomb);
        }
    }

    public void removeFinishedElements() {
        for (int i = 0; i < deads.size(); i++) {
            if (deads.get(i).getAnimation().isFinishDeadAnimation()) {
                deads.remove(i--);
            }
        }

        for (int i = 0; i < flames.size(); i++) {
            if (flames.get(i).getAnimation().isFinishDeadAnimation()) {
                flames.remove(i--);
            }
        }
    }

    public void addFlame(int xUnit, int yUnit) {
        if (bombers.size() == 0) {
            return;
        }

        Entity flame = new Flame (xUnit, yUnit, "epicenter");
        flames.add(flame);
        this.addFlame(xUnit, yUnit, -1, 0);
        this.addFlame(xUnit, yUnit, 1, 0);
        this.addFlame(xUnit, yUnit, 0, -1);
        this.addFlame(xUnit, yUnit, 0, 1);
    }

    private void addFlame(int xUnit, int yUnit, int x, int y) {
        Bomber bomber = (Bomber) bombers.get(0);

        boolean add = true;

        for (int i = 0; i < bomber.getFlame(); i++) {
            String s;
            if (x == -1) {
                s = "left";
            } else if (x == 1) {
                s = "right";
            } else if (y == -1) {
                s = "up";
            } else {
                s = "down";
            }

            Entity flame;
            if (i + 1 < bomber.getFlame()) {
                if (x == 0) {
                    flame = new Flame (xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_vertical.getFxImage(), "vertical");
                } else {
                    flame = new Flame (xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_horizontal.getFxImage(), "horizontal");
                }
            }
            else {
                if (x == -1) {
                    flame = new Flame (xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_horizontal_left_last.getFxImage(), "left");
                } else if (x == 1) {
                    flame = new Flame (xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_horizontal_right_last.getFxImage(), "right");
                } else if (y == -1) {
                    flame = new Flame (xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_vertical_top_last.getFxImage(), "up");
                } else {
                    flame = new Flame (xUnit + x * (i + 1), yUnit + y * (i + 1), Sprite.explosion_vertical_down_last.getFxImage(), "down");
                }
            }

            add = true;
            for (int j = 0; j < walls.size(); j++) {
                if (flame.intersects(walls.get(j))) {
                    add = false;
                    break;
                }
            }

            if (!add) {
                break;
            }
            flames.add(flame);
        }
    }

    private boolean isGameComplete() {
        if (isGameComplete) {
            isGameComplete = false;
            return true;
        }
        return false;
    };

}
