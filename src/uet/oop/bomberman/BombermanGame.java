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
import uet.oop.bomberman.entities.block.Bomb;
import uet.oop.bomberman.entities.block.Grass;
import uet.oop.bomberman.entities.block.Wall;
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
        EntityArr.getFlames().clear();
        EntityArr.getBombers().clear();
        EntityArr.getBombs().clear();
        EntityArr.getWalls().clear();
        EntityArr.getGrasses ().clear();
        EntityArr.getDeads().clear();

        for (int i = 0; i < height; i++) {
            String s = map.get(i);
            for (int j = 0; j < width; j++) {
                Entity object = new Grass (j, i, Sprite.grass.getFxImage());
                EntityArr.getGrasses ().add(object);
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
                else {
                    if (c == '#') {
                        object = new Wall (j, i, Sprite.wall.getFxImage());
                        EntityArr.getWalls ().add(object);
                    }
                }
            }
        }
    }

    public void update() {
        EntityArr.getBombers().forEach(Entity::update);
        EntityArr.getDeads().forEach(Entity::update);
        EntityArr.getBombs().forEach(Entity::update);
        EntityArr.getFlames().forEach(Entity::update);

        for (int i = 0; i < EntityArr.getBombers().size(); i++) {
            ((Bomber)EntityArr.getBombers().get(i)).handleKeyPress(this.scene);
        }
        for (int i = 0; i < EntityArr.getBombs().size(); i++) {
            for (int j = 0; j < EntityArr.getBombers().size(); j++) {
                Bomber bomber = (Bomber) EntityArr.getBombers().get(j);
                ((Bomb)EntityArr.getBombs().get(i)).checkCharacterPassability(bomber);
            }
        }

        Bomb.addBomb();
        this.handleCollision();
        Flame.removeFinishedElements();
        Flame.removeDeadEntity();

        if (EntityArr.getBombers().size() == 0 && EntityArr.getDeads().size() == 0) {
            lose = true;
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        EntityArr.getGrasses ().forEach(g -> g.render(gc));
        EntityArr.getWalls ().forEach(g -> g.render(gc));
        EntityArr.getBombs().forEach(g -> g.render(gc));
        EntityArr.getDeads().forEach(g -> g.render(gc));
        EntityArr.getFlames().forEach(g -> g.render(gc));
        EntityArr.getBombers().forEach(g -> g.render(gc));

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
        if (EntityArr.getBombers().size() > 0) {
            Bomber bomber = (Bomber) EntityArr.getBombers().get(0);

            for (int i = 0; i < EntityArr.getFlames().size(); i++) {
                if (bomber.intersects(EntityArr.getFlames().get(i))) {
                    bomber.setHp(0);
                    break;
                }
            }

            for (int i = 0; i < EntityArr.getBombs().size(); i++) {
                if (((Bomb)EntityArr.getBombs().get(i)).isPassable()) {
                    continue;
                }
                bomber.checkObjectMovementAbility(EntityArr.getBombs().get(i));
            }
            for (int i = 0; i < EntityArr.getWalls ().size(); i++) {
                bomber.checkObjectMovementAbility(EntityArr.getWalls().get(i));
            }
        }
        // Bomb
        for (int i = 0; i < EntityArr.getBombs().size(); i++) {
            for (int j = 0; j < EntityArr.getFlames().size(); j++) {
                if (EntityArr.getBombs().get(i).intersects(EntityArr.getFlames().get(j))) {
                    EntityArr.getBombs().get(i).setHp(0);
                    break;
                }
            }
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
