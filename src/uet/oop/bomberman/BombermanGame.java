package uet.oop.bomberman;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.block.Bomb;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.CreateMap;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ListIterator;

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


    private GraphicsContext gc;
    private Canvas canvas;

    public static Bomber bomber = new Bomber (1, 1, Sprite.player_right.getFxImage());
    public static boolean running;

    public static void main(String[] args) {
        Sound.play ("soundtrack");
        Application.launch(BombermanGame.class);
    }

    public void start(Stage stage) {
        CreateMap.loadMapListFromFile();
        CreateMap.readDataFromFile(0);
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
                    CreateMap.readDataFromFile(++level);

                    canvas.setHeight(Sprite.SCALED_SIZE * height);
                    canvas.setWidth(Sprite.SCALED_SIZE * width);
                    gc = canvas.getGraphicsContext2D();
                    root = new Group();
                    root.getChildren().add(canvas);
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    /*updateMenu();*/
                    CreateMap.createMap(); // Tạo bản đồ mới
                }
                Enemy.removeEnemy ();
            }
        };
        timer.start();
        bomber = new Bomber(1, 1, Sprite.player_right_2.getFxImage());
        CreateMap.createMap();
    }



/**
xử lí va chạm
 */
    public void handleCollision() {
        // Nhan vat
        ///////////////////////////////////////////////////////
        if (bombers.size() > 0) {
            Bomber bomber = (Bomber) bombers.get(0);

            for (Entity flame : flames) {
                if (bomber.intersects (flame)) {
                    bomber.setHp (0);
                    break;
                }
            }
            for (Entity enemy : enemies) {
                if (bomber.intersects (enemy)) {
                    bomber.setHp (0);
                    break;
                }
            }
            if (bomber.isWallPass ()) {
                for (Entity brick : bricks) {
                    if (brick.isAlive ()) {
                        bomber.checkObjectMovementAbility (brick);
                    }
                }
            }
            if (bomber.isWallPass ()) {
                for (Entity flameItem : flameItems) {
                    if (flameItem.isAlive ()) {
                        bomber.checkObjectMovementAbility (flameItem);
                    }
                }
            }
            if (bomber.isWallPass ()) {
                for (Entity bombItem : bombItems) {
                    if (bombItem.isAlive ()) {
                        bomber.checkObjectMovementAbility (bombItem);
                    }
                }
            }
            if (bomber.isWallPass ()) {
                for (Entity speedItem : speedItems) {
                    if (speedItem.isAlive ()) {
                        bomber.checkObjectMovementAbility (speedItem);
                    }
                }
            }
            for (Entity bomb : bombs) {
                if (((Bomb) bomb).isPassable ()) {
                    continue;
                }
                bomber.checkObjectMovementAbility (bomb);
            }
            for (Entity wall : walls) {
                bomber.checkObjectMovementAbility (wall);
            }
            // va chạm với flameItem
            ListIterator<Entity> itemIterator = flameItems.listIterator();
            while (!flameItems.isEmpty() && itemIterator.hasNext()) {
                Entity itemNext = itemIterator.next();
                if (bomber.intersects(itemNext)) {
                    int power = bomber.getFlame() + 2;
                    bomber.setFlame(power);
                    itemIterator.remove();
                }
            }
            // va chạm với speedItem
            ListIterator<Entity> sItemIterator = speedItems.listIterator();
            while (!speedItems.isEmpty() && sItemIterator.hasNext()) {
                Entity itemNext = sItemIterator.next();
                if (bomber.intersects(itemNext)) {
                    bomber.setSpeed(2);
                    sItemIterator.remove();
                }
            }
            // va chạm với bombItem
            ListIterator<Entity> bItemIterator = bombItems.listIterator();
            while (!bombItems.isEmpty() && bItemIterator.hasNext()) {
                Entity itemNext = bItemIterator.next();
                if (bomber.intersects(itemNext)) {
                    bomber.setBomb(2);
                    bItemIterator.remove();
                }
            }
            for (Entity portal : portals) {
                if (bomber.intersects (portal) && enemies.size () == 0) {
                    boolean complete = true;
                    for (Entity brick : bricks) {
                        if (portal.getX () == brick.getX () &&
                                portal.getY () == brick.getY ()) {
                            complete = false;
                            break;
                        }
                    }
                    this.isGameComplete = complete;
                }
            }
        }

        // Bomb
        for (Entity bomb : bombs) {
            for (Entity flame : flames) {
                if (bomb.intersects (flame)) {
                    bomb.setHp (0);
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
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        EntityArr.getGrasses ().forEach(g -> g.render(gc));
        portals.forEach(g -> {if (g.isAlive()) g.render(gc);
        });
        EntityArr.enemies.forEach(g -> {
            if (g.isAlive()) g.render(gc);
        });
        EntityArr.getWalls ().forEach(g -> g.render(gc));
        flameItems.forEach(g -> g.render(gc));
        speedItems.forEach(g -> g.render(gc));
        bombItems.forEach(g -> g.render(gc));
        bricks.forEach(g -> {if (g.isAlive()) g.render(gc);
        });
        EntityArr.getDeads().forEach(g -> g.render(gc));
        EntityArr.getBombs().forEach(g -> g.render(gc));
        EntityArr.getFlames().forEach(g -> g.render(gc));
        EntityArr.getBombers().forEach(g -> g.render(gc));
    }

    public void update() {
        EntityArr.enemies.forEach(Entity::update);
        portals.forEach(Entity::update);
        EntityArr.getDeads().forEach(Entity::update);
        EntityArr.getFlames().forEach(Entity::update);
        flameItems.forEach(Entity::update);
        speedItems.forEach(Entity::update);
        bombItems.forEach(Entity::update);
        bricks.forEach(Entity::update);
        EntityArr.getBombs().forEach(Entity::update);
        EntityArr.getBombers().forEach(Entity::update);

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

}
