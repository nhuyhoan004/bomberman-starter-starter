package uet.oop.bomberman;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.bomber.Bomb;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.CreateMap;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.controls.Menu.updateMenu;
import static uet.oop.bomberman.entities.EntityArr.*;

public class BombermanGame extends Application {
    private Group root;
    private Scene scene;
    public static boolean win = false;
    private boolean lose = false;

    private boolean isGameComplete = false;
    public static int level = 0;
    public static final String TITLE = "Bomberman by group 07";
    public static int width = 0;
    public static int height = 0;

    public static int[][] idObjects;    //Two-dimensional array is used to test paths
    private GraphicsContext gc;
    public static int[][] killList;
    private Canvas canvas;


    public static MovingEntity player;
    public static boolean running;

    public static void main(String[] args) {
        Sound.play ("soundtrack");
        Application.launch(BombermanGame.class);
    }

    public void start(Stage stage) {
        CreateMap.loadMapListFromFile();
        CreateMap.readDataFromFile();
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
                    CreateMap.readDataFromFile();

                    canvas.setHeight(Sprite.SCALED_SIZE * height);
                    canvas.setWidth(Sprite.SCALED_SIZE * width);
                    gc = canvas.getGraphicsContext2D();
                    root = new Group();
                    root.getChildren().add(canvas);
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    updateMenu();
                    CreateMap.createMap(); // Tạo bản đồ mới
                }
            }
        };
        timer.start();
        player = new Bomber(1, 1, Sprite.player_right_2.getFxImage());
        CreateMap.createMap();
    }

    public void update() {
        EntityArr.getBombers().forEach(Entity::update);
        EntityArr.getDeads().forEach(Entity::update);
        EntityArr.getBombs().forEach(Entity::update);
        EntityArr.getFlames().forEach(Entity::update);
        EntityArr.enemies.forEach(Enemy::update);


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
        grasses.forEach(g -> g.render(gc));
        portals.forEach(g -> g.render(gc));
        walls.forEach(g -> g.render(gc));
        EntityArr.getGrasses ().forEach(g -> g.render(gc));
        EntityArr.getWalls ().forEach(g -> g.render(gc));
        EntityArr.getBombs().forEach(g -> g.render(gc));
        EntityArr.getDeads().forEach(g -> g.render(gc));
        EntityArr.getFlames().forEach(g -> g.render(gc));
        EntityArr.getBombers().forEach(g -> g.render(gc));
        bricks.forEach(g -> g.render(gc));
        EntityArr.enemies.forEach(g -> {
            if (g.isAlive()) g.render(gc);
        });
    }


    /**
     * Xu ly va cham (tat ca moi va cham deu xu ly o day).
     */
    public void handleCollision() {
        // Nhan vat
        ///////////////////////////////////////////////////////
        if (bombers.size () > 0) {
            Bomber bomber = (Bomber) bombers.get (0);

            for (int i = 0; i < flames.size (); i++) {
                if (bomber.intersects (flames.get (i))) {
                    bomber.setHp (0);
                    break;
                }
            }

            for (int i = 0; i < enemies.size (); i++) {
                if (bomber.intersects (enemies.get (i))) {
                    bomber.setHp (0);
                    break;
                }
            }
            if (!bomber.isWallPass ()) {
                for (int i = 0; i < bricks.size (); i++) {
                    bomber.checkObjectMovementAbility (bricks.get (i));
                }
            }
            for (int i = 0; i < bombs.size (); i++) {
                if (((Bomb) bombs.get (i)).isPassable ()) {
                    continue;
                }
                bomber.checkObjectMovementAbility (bombs.get (i));
            }
            for (int i = 0; i < walls.size (); i++) {
                bomber.checkObjectMovementAbility (walls.get (i));
            }
            for (int i = 0; i < portals.size (); i++) {
                if (bomber.intersects (portals.get (i)) && enemies.size () == 0) {
                    boolean complete = true;
                    for (int j = 0; j < bricks.size (); j++) {
                        if (portals.get (i).getX () == bricks.get (j).getX () &&
                                portals.get (i).getY () == bricks.get (j).getY ()) {
                            complete = false;
                            break;
                        }
                    }
                    this.isGameComplete = complete;
                }
            }
            // Cac enemy
            ////////////////////////////////////////////////////////////////
            for (int i = 0; i < enemies.size (); i++) {
                MovingEntity movingEntity = (MovingEntity) enemies.get (i);
                if (!movingEntity.isWallPass ()) {
                    for (int j = 0; j < bricks.size (); j++) {
                        movingEntity.checkObjectMovementAbility (bricks.get (j));
                    }
                }
                for (int j = 0; j < bombs.size (); j++) {
                    movingEntity.checkObjectMovementAbility (bombs.get (j));
                }
                for (int j = 0; j < walls.size (); j++) {
                    movingEntity.checkObjectMovementAbility (walls.get (j));
                }
                for (int j = 0; j < flames.size (); j++) {
                    if (movingEntity.intersects (flames.get (j))) {
                        movingEntity.setHp (0);
                    }
                }
            }

            // Bomb
            for (int i = 0; i < bombs.size (); i++) {
                for (int j = 0; j < flames.size (); j++) {
                    if (bombs.get (i).intersects (flames.get (j))) {
                        bombs.get (i).setHp (0);
                        break;
                    }
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
