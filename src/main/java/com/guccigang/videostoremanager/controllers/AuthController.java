package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.scenes.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AuthController {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    void exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Your are about to exit the program.");
        alert.setContentText("Are you sure that your want to exit the program?");

        var app = ApplicationCore.getInstance();
        var manager = app.getSceneManager();

        // Check if the user wants to exit the program
        if (alert.showAndWait().orElseThrow() == ButtonType.OK) {
            // If the user clicked OK, save the data and exit the program
            System.out.println("Exiting program...\n");
            app.stop();

            // Close the current window
            manager.closeWindow();
        }
    }

    @FXML
    void login(ActionEvent event) {
        var app = ApplicationCore.getInstance();
        var auth = app.getAuthManager();

        // Get the username and password
        var username = this.username.getText();
        var password = this.password.getText();

        // Check if the username and password are valid
        if (!auth.login(username, password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Your username or password is incorrect.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
            return;
        }

        var manager = app.getSceneManager();

        // Check if the user is the admin
        if (auth.isAdmin()) {
            manager.showScene("admin-dashboard");
            return;
        }

        // Otherwise, show the user dashboard
        manager.showScene("user-dashboard");
    }

    @FXML
    void signup(ActionEvent event) {
        var app = ApplicationCore.getInstance();
        var auth = app.getAuthManager();

        // Get the username and password
        var username = this.username.getText();
        var password = this.password.getText();

        // Check if the username and password are valid
        if (!auth.signup(username, password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Signup Error");
            alert.setHeaderText("Your username or password is invalid.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
            return;
        }

        // Show the user dashboard
        var manager = app.getSceneManager();
        manager.showScene("user-dashboard");
    }
}
