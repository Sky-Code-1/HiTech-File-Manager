package org.hitech.hitechfilemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HiTechFileManager extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HiTechFileManager.class.getResource("filepage-view.fxml"));
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