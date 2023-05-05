package uet.oop.bomberman.controls;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.EntityArr;
import uet.oop.bomberman.graphics.CreateMap;
import uet.oop.bomberman.sound.Sound;

import static javax.sound.sampled.AudioSystem.getClip;
import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.entities.EntityArr.enemies;

// Menu của trò chơi
public class Menu {
    public static Sound sound;
    private static ImageView statusGame;
    // Menu hiển thị level,số hàng, số cột
    public static void createMenu(Group root) {
        Sound.title_screen.play ();
        Image newGame = new Image("images/newGame.png");
        statusGame = new ImageView(newGame);
        statusGame.setScaleY(0.5);
        statusGame.setScaleX(0.5);
        statusGame.setX(-95);
        statusGame.setY(-57);


        Pane pane = new Pane();
        pane.getChildren().addAll(statusGame);
        pane.setMinSize(992, 30);
        pane.setMaxSize(992, 30);
        pane.setStyle("-fx-background-color: WHITE");

        root.getChildren().add(pane);

        statusGame.setOnMouseClicked(event -> {
            if (bomber.isAlive()) {
                running = !running;
            } else {
                Sound.title_screen.close();
                Sound.soundplay.play ();
                Image transparent = new Image("images/transparent.png");
                authorView.setImage(transparent);
                CreateMap.loadMapListFromFile();
                CreateMap.readDataFromFile(0);
                CreateMap.createMap();
                bomber.setAlive(true);
                running = true;
            }
            updateMenu();
        });
    }

    public static void updateMenu() {
        if (bomber.isAlive()) {
            if (running) {
                Image pauseGame = new Image("images/pauseGame.png");
                statusGame.setImage(pauseGame);
                Sound.soundplay.resume();
            } else {
                Image playGame = new Image("images/playGame.png");
                statusGame.setImage(playGame);
                Sound.soundplay.pause ();
            }
        } else {
            Image newGame = new Image("images/newGame.png");
            statusGame.setImage(newGame);
        }
    }
}
