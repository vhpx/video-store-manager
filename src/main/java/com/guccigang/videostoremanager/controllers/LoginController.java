package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.scenes.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController{
    private final SceneManager manager = ApplicationCore.getInstance().getSceneManager();

    @FXML
    private TextField nameLabel;

    @FXML
    private TextField passLabel;

    @FXML
    private VBox sigupPane;

    @FXML
    void exit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Your are about to exit the program.");
        alert.setContentText("Are you sure that your want to exit the program?");
        if (alert.showAndWait().orElseThrow() == ButtonType.OK) manager.closeWindow();
    }

    @FXML
    void login(ActionEvent event) {
        manager.showScene("user-dashboard");
    }

    @FXML
    void signup(ActionEvent event) {

    }


}
