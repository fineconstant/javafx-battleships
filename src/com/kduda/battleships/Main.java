package com.kduda.battleships;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/battleshipsScene.fxml"));
//        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        primaryStage.setTitle("Battleships");
        primaryStage.setScene(new Scene(root,900,800));
        primaryStage.show();
    }
}
