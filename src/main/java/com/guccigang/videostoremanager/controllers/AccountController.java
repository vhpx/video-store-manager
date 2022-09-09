package com.guccigang.videostoremanager.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountController implements Initializable {

    @FXML
    private Label addressLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label pointLabel;

    @FXML
    private Label rankingLabel;

    private SceneController sceneController = SceneController.getInstance();

    @FXML
    public ImageView profileImage = new ImageView();

    private ObservableList<String> list = FXCollections.observableArrayList(
            "Account Profile",
            "Setting",
            "Log out"
    );
    @FXML
    ComboBox<String> comboBox = new ComboBox<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboBox.setItems(list);
        this.profileImage.setImage(new Image(getClass().getResourceAsStream("/com/guccigang/image/images.png"))) ;
    }
    @FXML
    private void comboBoxChanged(ActionEvent event) throws IOException {

        if (this.comboBox.getValue().equals("Log out"))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setHeaderText("Your are about to log out your account.");
            alert.setContentText("Are you sure that your want to log out?");
            if (alert.showAndWait().get() == ButtonType.OK)
                this.sceneController.showScene("login");
        }else if (this.comboBox.getValue().equals("Account Profile"))
        {
            this.sceneController.showScene("accountInfo");
        }
    }

    @FXML
    void modifyAccountInfo() {
        this.sceneController.showScene("accountModification");
    }
        @FXML
    void backToAccountScreen ()
    {
        this.sceneController.showScene("account");
    }
    @FXML
    void browseItem(ActionEvent event) {

    }
    @FXML
    void historyBotton(ActionEvent event) {

    }

    @FXML
    void returnItem(ActionEvent event) {

    }
}
