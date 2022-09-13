package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.scenes.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class AccountController implements Initializable {


    private final ApplicationCore app = ApplicationCore.getInstance();

    private final SceneManager manager = ApplicationCore.getInstance().getSceneManager();

    private final ObservableList<String> list = FXCollections.observableArrayList("Account Profile");
    private final ObservableList<String> sortlist = FXCollections.observableArrayList("Alphabet",
                                                                                        "Rental Type",
                                                                                        "Genre");
    @FXML
    Circle circleImage;
    @FXML
    GridPane borrowedList;

    @FXML
    GridPane transaction;

    @FXML
    GridPane browsePane;


    @FXML
    Label optionLabel;

    @FXML
    Label statusMini;

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
        this.borrowedList.setVisible(true);
        this.transaction.setVisible(false);
        this.browsePane.setVisible(false);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/images/images.png")));
        circleImage.setFill(new ImagePattern(image));
    }
    @FXML
    private void comboBoxChanged() {

        if (this.comboBox.getValue().equals("Account Profile")) {
            this.manager.showScene("account-info");
            this.comboBox.getSelectionModel().clearSelection();
        }
    }
    @FXML
    void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out");
        alert.setHeaderText("Your are about to log out the program.");
        alert.setContentText("Are you sure that your want to log out the program?");
        if (alert.showAndWait().orElseThrow() == ButtonType.OK)
        {
            this.manager.showScene("auth");
            //this.app.stop();
        }
    }

    @FXML
    void browseItem(ActionEvent event) {
        this.browsePane.setVisible(true);
        this.borrowedList.setVisible(false);
        this.transaction.setVisible(false);
        this.optionLabel.setText("Borrow Item");
        this.statusMini.setText("Dashboard/ Borrow Item");
    }

    @FXML
    void borrowedList(ActionEvent event) {
        this.browsePane.setVisible(false);
        this.borrowedList.setVisible(true);
        this.transaction.setVisible(false);
        this.optionLabel.setText("Borrowed List");
        this.statusMini.setText("Dashboard/ Borrowed List");
    }
    @FXML
    void historyButton(ActionEvent event) {
        this.browsePane.setVisible(false);
        this.borrowedList.setVisible(false);
        this.transaction.setVisible(true);
        this.optionLabel.setText("History");
        this.statusMini.setText("Dashboard/ History");
    }

    @FXML
    void returnItem(ActionEvent event) {

    }

    @FXML
    void borrowItem(ActionEvent event) {

    }



}
