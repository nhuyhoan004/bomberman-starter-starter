package uet.oop.bomberman;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import uet.oop.bomberman.controls.Menu;
import uet.oop.bomberman.entities.block.Bomb;
import uet.oop.bomberman.entities.bomber.Bomber;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;
import javafx.scene.image.ImageView;
import java.util.ListIterator;
import static uet.oop.bomberman.controls.Menu.updateMenu;
import static uet.oop.bomberman.entities.EntityArr.*;
import static uet.oop.bomberman.graphics.NextLevel.*;


public class BombermanGame extends Application {
    public static Group root;
    public static Scene scene;
    public static boolean win = false;
    public static boolean lose = false;

    private static boolean isGameComplete = false;
    public static int level = 0;
    public static final String TITLE = "Bomberman by group 07";
    public static int width = 31;
    public static int height = 13;
    public static ImageView authorView;
    public static GraphicsContext gc;
    public static Canvas canvas;

    public static Bomber bomber;
    public static Stage stage = null;
    public static boolean running;
    public static Sound sound;

    public static boolean getIsGameComplete() {
        return isGameComplete;
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public void start(Stage stage) {
        // khởi động giao diện Menu

        stage.setTitle(BombermanGame.TITLE);
        stage.setResizable(false);
        sound = new Sound ("title_screen");
        sound.play();
        canvas = new Canvas(Sprite.SCALED_SIZE * width, Sprite.SCALED_SIZE * height);
        canvas.setTranslateY(28);
        gc = canvas.getGraphicsContext2D();
        Image icon = new Image("images/icon.png");
        stage.getIcons().add(icon);
        Image author = new Image("images/author.png");
        authorView = new ImageView(author);
        authorView.setY(30);
        root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().add(authorView);
        Menu.createMenu(root);
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                Enemy.removeEnemy();
                update();
            }
        };
        timer.start();
        bomber = new Bomber(1, 1, Sprite.player_right.getFxImage());
        bomber.setAlive(false);
    }



    /**
     * xử lý va chạm
     */
    public void handleCollision() {
        // Nhan vat
        ///////////////////////////////////////////////////////
        if (bombers.size() > 0) {
            Bomber bomber = (Bomber) bombers.get(0);

            for (int i = 0; i < flames.size(); i++) {
                if (bomber.intersects(flames.get(i))) {
                    bomber.setHp(0);
                    running = false;
                    break;
                }
            }
            for (int i = 0; i < enemies.size(); i++) {
                if (bomber.intersects(enemies.get(i))) {
                    bomber.setHp(0);
                    running = false;
                }
            }
            for (Entity flame : flames) {
                if (bomber.intersects(flame)) {
                    bomber.setHp(0);
                    bomber.setAlive(false);
                    break;
                }
            }
            for (Entity enemy : enemies) {
                if (bomber.intersects(enemy)) {
                    bomber.setHp(0);
                    bomber.setAlive(false);
                    break;
                }
            }
            if (bomber.isWallPass()) {
                for (Entity brick : bricks) {
                    if (brick.isAlive()) {
                        bomber.checkObjectMovementAbility(brick);
                    }
                }
            }
            
            for (Entity bomb : bombs) {
                if (((Bomb) bomb).isPassable()) {
                    continue;
                }
                bomber.checkObjectMovementAbility(bomb);
            }
            for (Entity wall : walls) {
                bomber.checkObjectMovementAbility(wall);
            }
            // va chạm với flameItem
            ListIterator<Entity> itemIterator = flameItems.listIterator();
            while (!flameItems.isEmpty() && itemIterator.hasNext()) {
                Entity itemNext = itemIterator.next();
                if (bomber.intersects(itemNext)) {
                    int power = bomber.getFlame() + 2;
                    bomber.setFlame(power);
                    itemIterator.remove();
                    sound = new Sound ("Item");
                    sound.play();
                }
            }
            // va chạm với speedItem
            ListIterator<Entity> sItemIterator = speedItems.listIterator();
            while (!speedItems.isEmpty() && sItemIterator.hasNext()) {
                Entity itemNext = sItemIterator.next();
                if (bomber.intersects(itemNext)) {
                    bomber.setSpeed(2);
                    sItemIterator.remove();
                    sound = new Sound ("Item");
                    sound.play();
                }
            }
            // va chạm với bombItem
            ListIterator<Entity> bItemIterator = bombItems.listIterator();
            while (!bombItems.isEmpty() && bItemIterator.hasNext()) {
                Entity itemNext = bItemIterator.next();
                if (bomber.intersects(itemNext)) {
                    bomber.setBomb(2);
                    bItemIterator.remove();
                    sound = new Sound ("Item");
                    sound.play();
                }
            }
            // portal
            for (Entity portal : portals) {
                if (bomber.intersects(portal) && enemies.size() == 0) {
                    boolean complete = true;
                    for (Entity brick : bricks) {
                        if (portal.getX() == brick.getX() &&
                                portal.getY() == brick.getY()) {
                            complete = false;
                            break;
                        }
                    }
                    isGameComplete = complete;
//                    waitingTime = System.currentTimeMillis();
                }
            }
        }
        // Bomb
        for (Entity bomb : bombs) {
            for (Entity flame : flames) {
                if (bomb.intersects(flame)) {
                    bomb.setHp(0);
                    break;
                }
            }
        }
    }

    public static boolean isGameComplete() {
        if (isGameComplete) {
            isGameComplete = false;
            setWait(true);
            waitingTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        EntityArr.getGrasses().forEach(g -> g.render(gc));
        portals.forEach(g -> {
            if (g.isAlive()) g.render(gc);
        });
        EntityArr.getWalls().forEach(g -> g.render(gc));
        flameItems.forEach(g -> g.render(gc));
        speedItems.forEach(g -> g.render(gc));
        bombItems.forEach(g -> g.render(gc));
        EntityArr.enemies.forEach(g -> {
            if (g.isAlive()) g.render(gc);
        });
        bricks.forEach(g -> {
            if (g.isAlive()) g.render(gc);
        });
        EntityArr.getDeads().forEach(g -> g.render(gc));
        EntityArr.getBombs().forEach(g -> g.render(gc));
        EntityArr.getFlames().forEach(g -> g.render(gc));
        EntityArr.getBombers().forEach(g -> g.render(gc));
    }

    public void update () {
        EntityArr.enemies.forEach(Entity::update);
        portals.forEach(Entity::update);
        EntityArr.getFlames().forEach(Entity::update);
        flameItems.forEach(Entity::update);
        speedItems.forEach(Entity::update);
        bombItems.forEach(Entity::update);
        bricks.forEach(Entity::update);
        EntityArr.getDeads().forEach(Entity::update);
        EntityArr.getBombs().forEach(Entity::update);
        EntityArr.getBombers().forEach(Entity::update);

        for (int i = 0; i < EntityArr.getBombers().size(); i++) {
            ((Bomber) EntityArr.getBombers().get(i)).handleKeyPress(this.scene);
        }
        for (int i = 0; i < EntityArr.getBombs().size(); i++) {
            for (int j = 0; j < EntityArr.getBombers().size(); j++) {
                Bomber bomber = (Bomber) EntityArr.getBombers().get(j);
                ((Bomb) EntityArr.getBombs().get(i)).checkCharacterPassability(bomber);
            }
        }

        Bomb.addBomb();
        this.handleCollision();
        Flame.removeFinishedElements();
        Flame.removeDeadEntity();
        if (!enemies.isEmpty() && EntityArr.getBombers().isEmpty()) {
            bomber.setAlive(false);
            Image gameOver = new Image("images/gameOver.png");
            authorView.setImage(gameOver);
//            sound.close();
            lose = true;
            running = false;
        }
        waitToLevelUp();
        updateMenu();
    }

}

