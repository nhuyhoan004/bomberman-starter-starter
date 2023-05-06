package uet.oop.bomberman.controls;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import uet.oop.bomberman.graphics.CreateMap;
import uet.oop.bomberman.sound.Sound;
import static uet.oop.bomberman.BombermanGame.*;

// Menu của trò chơi
public class Menu {
    private static ImageView statusGame;
    // Menu hiển thị level,số hàng, số cột
    public static void createMenu(Group root) {
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
        Sound.soundstart.loop();
        statusGame.setOnMouseClicked(event -> {
            if (bomber.isAlive()) {
                // nếu người chơi còn mạng, game tiếp tục
                if (Sound.soundplay.getClip() != null && Sound.soundplay.getClip().isRunning()) {
                    Sound.soundplay.pause();
                } else {
                    Sound.soundplay.resume();
                }
                running = !running;
            } else {
                // bomber dead, start a new game
                Sound.game_over.stop();
                Sound.soundstart.stop();
                Sound.soundplay.play();
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
            } else {
                Image playGame = new Image("images/playGame.png");
                statusGame.setImage(playGame);
            }
        } else {
            Image newGame = new Image("images/newGame.png");
            statusGame.setImage(newGame);
        }
    }
}
