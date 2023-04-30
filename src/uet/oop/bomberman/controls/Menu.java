package uet.oop.bomberman.controls;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.graphics.CreateMap;


import static uet.oop.bomberman.BombermanGame.*;

// Menu của trò chơi
public class Menu {
    private static ImageView statusGame;
    public static Text level, bomb, time;
    public static int bomNumber = 20;
    // Menu hiển thị level,số hàng, số cột
    public static void createMenu(Group root) {
        // só thự tự màn chơi
        /*level = new Text("level: 1");
        level.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14 ));
        level.setFill(Color.BLUEVIOLET);
        level.setX(416);
        level.setY(20);*/
        // số hàng của bản đồ

        // số cột của bản đồ

        Image newGame = new Image("images/newGame.png");
        statusGame = new ImageView(newGame);
        statusGame.setScaleY(0.5);
        statusGame.setScaleX(0.5);
        statusGame.setX(-70);
        statusGame.setY(-10);


        Pane pane = new Pane();
        pane.getChildren().addAll(statusGame);
        pane.setMinSize(992, 32);
        pane.setMaxSize(992, 32);
        pane.setStyle("-fx-background-color: WHITE");

        root.getChildren().add(pane);

        statusGame.setOnMouseClicked(event -> {
            if (bomber.isAlive()) {
                // nếu người chơi còn mạng, game tiếp tục
                running = !running;
            } else {
                // bomber dead, start a new game
                CreateMap.readDataFromFile(0);
                CreateMap.loadMapListFromFile();
                Image transparent = new Image("images/transparent.png");
                authorView.setImage(transparent);
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
