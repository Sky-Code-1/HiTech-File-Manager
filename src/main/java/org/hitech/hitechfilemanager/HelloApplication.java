package org.hitech.hitechfilemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("filepage-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        Label titleLabel = new Label("Hi-Tech File Manager");
        stage.setTitle("Hi-Tech File Manager");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}