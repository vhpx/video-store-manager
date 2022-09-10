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
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountController implements Initializable {


    private final ApplicationCore app = ApplicationCore.getInstance();

    private final SceneManager manager = ApplicationCore.getInstance().getSceneManager();

    private final ObservableList<String> list = FXCollections.observableArrayList("Account Profile");
    private final ObservableList<String> sortlist = FXCollections.observableArrayList("Alphabet",
                                                                                        "Rental Type",
                                                                                        "Genre");


    @FXML
    GridPane borrwedList;

    @FXML
    GridPane transaction;


    @FXML
    Label optionLabel;

    @FXML
    Label greetingLabel;
    @FXML
    ComboBox<String> sortComboBox = new ComboBox<>();
    @FXML
    ComboBox<String> comboBox = new ComboBox<>();

    @FXML
    ComboBox<String> historyComboBox = new ComboBox<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboBox.setItems(list);
        this.sortComboBox.setItems(sortlist);
        this.historyComboBox.setItems(sortlist);
        this.borrwedList.setVisible(true);
        this.transaction.setVisible(false);
    }
    @FXML
    private void comboBoxChanged() {

        if (this.comboBox.getValue().equals("Account Profile")) {
            this.manager.showScene("account-info");
        }
    }
    @FXML
    void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Your are about to log out the program.");
        alert.setContentText("Are you sure that your want to log out the program?");
        if (alert.showAndWait().orElseThrow() == ButtonType.OK)
        {
            this.app.stop();
            this.manager.showScene("auth");
        }
    }

    @FXML
    void browseItem(ActionEvent event) {

    }
    @FXML
    void borrowedList(ActionEvent event) {
        this.transaction.setVisible(false);
        this.borrwedList.setVisible(true);
        this.optionLabel.setText("Borrowed List");
    }
    @FXML
    void historyButton(ActionEvent event) {
        this.transaction.setVisible(true);
        this.borrwedList.setVisible(false);
        this.optionLabel.setText("History");
    }

    @FXML
    void returnItem(ActionEvent event) {

    }
}
