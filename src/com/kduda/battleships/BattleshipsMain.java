package com.kduda.battleships;

import com.kduda.battleships.controllers.BattleshipsConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class BattleshipsMain extends Application {
    private int HEIGHT;
    private int WIDTH;

    public BattleshipsMain() {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double height = bounds.getHeight();

        if (height < 1000) {
            this.HEIGHT = 580;
            this.WIDTH = 620;
            BattleshipsConfig.INSTANCE.cellSize = 18;
            return;
        }

        this.HEIGHT = 730;
        this.WIDTH = 820;
        BattleshipsConfig.INSTANCE.cellSize = 25;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/battleshipsScene.fxml"));
        primaryStage.getIcons().add(new Image(getClass().getResource("/com/kduda/battleships/assets/images/icon.png").toURI().toString()));
        primaryStage.setTitle("Battleships");
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        primaryStage.setScene(new Scene(root, this.WIDTH, this.HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
