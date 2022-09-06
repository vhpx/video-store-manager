package com.guccigang.videostoremanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VSMController implements Initializable {
    private SceneController sceneController;

    private ObservableList<String> list = FXCollections.observableArrayList(
            "Account Information",
            "Setting",
            "Log out"
    );
    @FXML
    ComboBox<String> comboBox = new ComboBox<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.sceneController = SceneController.getInstance();
        comboBox.setItems(list);
    }
//    @FXML
//    private void comboBoxChanged(ActionEvent event) throws IOException {
//
//        if (comboBox.getValue().equals("Log out"))
//        {
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Exit");
//            alert.setHeaderText("Your are about to log out your account.");
//            alert.setContentText("Are you sure that your want to log out?");
//            if (alert.showAndWait().get() == ButtonType.OK)
//                sceneController.showScene("login");
//        }else if (comboBox.getValue().equals("Account Information"))
//        {
//
//        }
//    }
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

    @FXML
    void browseItem(MouseEvent event) {

    }

    @FXML
    void returnItem(MouseEvent event) {

    }



}