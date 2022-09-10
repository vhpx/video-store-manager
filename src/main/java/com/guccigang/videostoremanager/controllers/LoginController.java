package com.guccigang.videostoremanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private AnchorPane loginPane;

    @FXML
    private AnchorPane imagePane = new AnchorPane();

    @FXML
    private ImageView image = new ImageView();

    @FXML
    private AnchorPane parentPane = new AnchorPane();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.image.setImage(new Image(getClass().getResourceAsStream("/com/guccigang/image/loginImage.png")));
        this.image.fitHeightProperty().bind(this.parentPane.heightProperty());
        this.imagePane.prefWidthProperty().bind(this.image.fitWidthProperty());

    }
}
