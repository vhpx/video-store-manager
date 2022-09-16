package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.scenes.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AccountManagementController implements Initializable {
    private SceneManager sceneController;

    @FXML
    private Circle circle;

    @FXML
    private TextField addressField;

    @FXML
    private Label addressLabel;

    @FXML
    private VBox editInfoPane;

    @FXML
    private TextField emailField;

    @FXML
    private TextField fnameField;

    @FXML
    private Label fnameLabel;

    @FXML
    private Label fullNameLabel;

    @FXML
    private TextField lnameField;

    @FXML
    private TextField phoneField;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label pointLabel;

    @FXML
    private Label rankingLabel;

    @FXML
    private Label optionLabel;

    @FXML
    private AnchorPane displayPane;

    @FXML
    private Button saveButton = new Button();

    @FXML
    void backToDashboard(ActionEvent event) {
        this.editInfoPane.setVisible(false);
        this.displayPane.setVisible(true);
        this.sceneController.showScene("user-dashboard");
    }

    @FXML
    void editProfile(ActionEvent event) {
        this.editInfoPane.setVisible(true);
        this.displayPane.setVisible(false);
        this.optionLabel.setText("/Edit Profile");
    }

    @FXML
    void profile(ActionEvent event) {
        this.editInfoPane.setVisible(false);
        this.displayPane.setVisible(true);
        this.optionLabel.setText("/Profile");
    }

    @FXML
    void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out!");
        alert.setHeaderText("Your are about to log out your account.");
        alert.setContentText("Are you sure that your want to log out?");
        if (alert.showAndWait().orElseThrow() == ButtonType.OK) {
            this.editInfoPane.setVisible(false);
            this.displayPane.setVisible(true);
            this.sceneController.showScene("auth");
        }

    }

    @FXML
    void save(ActionEvent event) {

    }

    @FXML
    void setting(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sceneController = ApplicationCore.getInstance().getSceneManager();
        Image image = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/images/images.png")));
        circle.setFill(new ImagePattern(image));
        this.editInfoPane.setVisible(false);
        this.displayPane.setVisible(true);
    }
}
