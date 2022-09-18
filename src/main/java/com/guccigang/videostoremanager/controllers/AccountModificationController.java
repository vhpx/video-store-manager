package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.auth.Account;
import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.core.Constants;
import com.guccigang.videostoremanager.errors.AccountException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountModificationController implements Initializable {


    public void setAccount(Account account)
    {
        comboBoxType.setValue(account.getRole());
        usernameField.setText(account.getName());
        nameField.setText(account.getUsername());
        passwordField.setText(account.getPassword());
        addressField.setText(account.getAddress());
        phoneField.setText(account.getPhone());
        pointField.setText(String.valueOf(account.getPoints()));
        id = account.getId();

        if (!account.getRole().equals("VIP")) {
            pointPane.setVisible(false);
        } else {
            pointPane.setVisible(true);
            pointField.setText(String.valueOf(account.getPoints()));
        }
    }
    @FXML
    HBox pointPane = new HBox();

    @FXML
    private TextField addressField;

    @FXML
    private ComboBox<String> comboBoxType = new ComboBox<>();

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField pointField;

    @FXML
    private TextField nameField;

    private String id = "";

    @FXML
    void cancel(ActionEvent event) {
        var app = ApplicationCore.getInstance();
        var manager = app.getSceneManager();
        manager.showScene("admin-dashboard");
    }

    @FXML
    void save(ActionEvent event) {
        try {
            var app = ApplicationCore.getInstance();
            var accounts = app.getAccountManager();

            var username = usernameField.getText();
            var password = passwordField.getText();
            var name = nameField.getText();
            var address = addressField.getText();
            var phone = phoneField.getText();
            var type = comboBoxType.getValue();
            var point = Integer.parseInt(pointField.getText());

            if (Flag.check == 1) {
                System.out.println("Creating new account");
                // Create the account
                var account = accounts.createAccount(username, password);
                id = account.getId();
            } else {
                System.out.println("Updating account");
            }

            // Check if id is valid
            if (id == null || id.isEmpty())
                throw new AccountException("Invalid account id");

            // Get the account by username
            var account = accounts.getAccountById(id);

            account.setName(name);
            account.setAddress(address);
            account.setPhone(phone);
            account.setPoints(point);

            switch (type) {
                case "GUEST" -> account.setRole(Constants.ROLE_GUEST);
                case "REGULAR" -> account.setRole(Constants.ROLE_REGULAR);
                case "VIP" -> account.setRole(Constants.ROLE_VIP);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxType.setItems(FXCollections.observableArrayList("Guest", "Regular", "VIP"));

        comboBoxType.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.equals("VIP")) {
                pointPane.setVisible(false);
                return;
            }
            pointPane.setVisible(true);

        });

//        if (Flag.check == 0) {
//            comboBoxType.setValue(Flag.account.getRole());
//            usernameField.setText(Flag.account.getName());
//            nameField.setText(Flag.account.getUsername());
//            passwordField.setText(Flag.account.getPassword());
//            addressField.setText(Flag.account.getAddress());
//            phoneField.setText(Flag.account.getPhone());
//            pointField.setText(String.valueOf(Flag.account.getPoints()));
//            id = Flag.account.getId();
//
//            if (!Flag.account.getRole().equals("VIP")) {
//                pointPane.setVisible(false);
//            } else {
//                pointPane.setVisible(true);
//                pointField.setText(String.valueOf(Flag.account.getPoints()));
//            }
//        }
//
//        if (Flag.check == 1) {
//            comboBoxType.setValue(null);
//            usernameField = new TextField();
//            nameField = new TextField();
//            passwordField = new TextField();
//            addressField = new TextField();
//            phoneField = new TextField();
//            pointField = new TextField();
//            id = "";
//        }
    }
}
