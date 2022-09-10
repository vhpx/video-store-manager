package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.scenes.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountController implements Initializable {

    @FXML
    private Label addressLabel;

    @FXML
    Label firstNameLabel;

    @FXML
    private AnchorPane accountPane;

    @FXML
    private Button logout;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label pointLabel;

    @FXML
    private Label rankingLabel;

    private final SceneManager manager = ApplicationCore.getInstance().getSceneManager();

    private final ObservableList<String> list = FXCollections.observableArrayList("Account Profile", "Setting", "Log out");
    @FXML
    ComboBox<String> comboBox = new ComboBox<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboBox.setItems(list);

    }

    @FXML
    private void comboBoxChanged() {

        if (this.comboBox.getValue().equals("Log out")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setHeaderText("Your are about to log out your account.");
            alert.setContentText("Are you sure that your want to log out?");
            if (alert.showAndWait().orElseThrow() == ButtonType.OK) this.manager.showScene("login");
        } else if (this.comboBox.getValue().equals("Account Profile")) {
            this.manager.showScene("accountInfo");
        }
    }

    @FXML
    void modifyAccountInfo() {
        this.manager.showScene("accountModification");
    }

    @FXML
    void backToAccountScreen() {
        this.manager.showScene("account");
    }

    @FXML
    void browseItem(ActionEvent event) {

    }

    @FXML
    void historyButton(ActionEvent event) {

    }

    @FXML
    void returnItem(ActionEvent event) {

    }
}
