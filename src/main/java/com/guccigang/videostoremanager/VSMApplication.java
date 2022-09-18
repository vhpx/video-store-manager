package com.guccigang.videostoremanager;

import com.guccigang.videostoremanager.core.ApplicationCore;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VSMApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            // Get the application core
            var app = ApplicationCore.getInstance();

            // Link the application and the stage
            app.link(this, stage);

            // Initialize the application
            app.initialize();

            // Execute the main flow of the application
            app.execute();
        } catch (Exception e) {
            System.out.println("There was an error loading the main scene.");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}