package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.scenes.SceneManager;
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
import java.util.Objects;
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
    private final SceneManager manager = ApplicationCore.getInstance().getSceneManager();

    @FXML
    protected void onClickedRegisterButton(ActionEvent e) throws IOException {
        manager.showScene("signup");
    }

    @FXML
    protected void onClickedLoginButton(ActionEvent e) throws IOException {
        manager.showScene("account");
    }

    @FXML
    protected void onClickedBackButton(ActionEvent e) throws IOException {
        manager.showScene("login");
    }

    @FXML
    protected void onClickedExitButton(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Your are about to exit the program.");
        alert.setContentText("Are you sure that your want to exit the program?");

        if (alert.showAndWait().orElseThrow() == ButtonType.OK) manager.closeWindow();
    }

    @FXML
    protected void onClickedExitSignupButton(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Your are about to exit the program.");
        alert.setContentText("Are you sure that your want to exit the program?");

        if (alert.showAndWait().orElseThrow() == ButtonType.OK) manager.closeWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/images/loginImage.png"))));
        this.image.fitHeightProperty().bind(this.parentPane.heightProperty());
        this.imagePane.prefWidthProperty().bind(this.image.fitWidthProperty());

    }
}
