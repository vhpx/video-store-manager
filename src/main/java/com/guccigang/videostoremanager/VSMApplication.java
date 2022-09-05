package com.guccigang.videostoremanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VSMApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var resourceUrl = getClass().getResource("vsm-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Video Store Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}