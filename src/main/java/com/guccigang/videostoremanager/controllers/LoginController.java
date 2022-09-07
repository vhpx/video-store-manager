package com.guccigang.videostoremanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;

public class LoginController {

    SceneController sceneController = SceneController.getInstance();
    @FXML
    protected void onClickedRegisterButton(ActionEvent e) throws IOException {
        sceneController.showScene("signup");
    }

    @FXML
    protected void onClickedLoginButton(ActionEvent e) throws IOException {
        sceneController.showScene("account");
    }

    @FXML
    protected void onClickedBackButton(ActionEvent e) throws IOException {
        sceneController.showScene("login");
    }

    @FXML
    protected void onClickedExitButton(ActionEvent e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Your are about to exit the program.");
        alert.setContentText("Are you sure that your want to exit the program?");

        if (alert.showAndWait().get() == ButtonType.OK)
            sceneController.closeWindow();
    }

    @FXML
    protected void onClickedExitSignupButton(ActionEvent e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Your are about to exit the program.");
        alert.setContentText("Are you sure that your want to exit the program?");

        if (alert.showAndWait().get() == ButtonType.OK)
            sceneController.closeWindow();
    }
}
