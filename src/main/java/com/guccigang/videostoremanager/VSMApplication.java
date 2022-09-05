package com.guccigang.videostoremanager;

import com.guccigang.videostoremanager.controllers.SceneController;
import com.guccigang.videostoremanager.core.Constants;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VSMApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            // Initialize SceneController
            var sceneController = SceneController.getInstance();
            sceneController.link(stage);
            sceneController.initialize(this);

            // Set the title of the stage
            String appTitle = Constants.getAppName();
            stage.setTitle(appTitle);

            // Set the initial scene
            Scene scene = sceneController.getDefaultScene();
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            System.out.println("There was an error loading the main scene.");
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}