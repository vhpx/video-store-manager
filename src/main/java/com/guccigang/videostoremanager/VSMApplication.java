package com.guccigang.videostoremanager;

import com.guccigang.videostoremanager.controllers.SceneController;
import com.guccigang.videostoremanager.core.Constants;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class VSMApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            // Initialize SceneController
            var sceneController = SceneController.getInstance();
            sceneController.link(stage);
            sceneController.initialize(this);
//            sceneController.getStyleSheet();
            // Set the title of the stage
            String appTitle = Constants.getAppName();
            stage.setTitle(appTitle);
            // Set the initial scene
            Scene scene = sceneController.getDefaultScene();
            stage.setScene(scene);
            String css = this.getClass().getResource("JVMStyleSheet.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.show();

            stage.setOnCloseRequest(e -> {
                e.consume();
                exit(stage);

            });

        } catch (Exception e) {
            System.out.println("There was an error loading the main scene.");
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    protected void exit(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Your are about to exit the program.");
        alert.setContentText("Are you sure that your want to exit the program?");

        if (alert.showAndWait().get() == ButtonType.OK)
            stage.close();

    }

    public static void main(String[] args) {
        launch();
    }
}