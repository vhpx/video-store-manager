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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountModificationController implements Initializable {
    private Account account;

    public void setAccount(Account account)
    {
        this.account =account;
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
            var account = accounts.createAccount(username, password);

            // Check if id is valid
            if (id == null || id.isEmpty())
                throw new AccountException("Invalid account id");

            // Get the account by username
            account = accounts.getAccountById(id);
            account.setName(name);
            account.setAddress(address);
            account.setPhone(phone);
            account.setPoints(point);

            switch (type) {
                case "GUEST" -> account.setRole(Constants.ROLE_GUEST);
                case "REGULAR" -> account.setRole(Constants.ROLE_REGULAR);
                case "VIP" -> account.setRole(Constants.ROLE_VIP);
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("admin-dasboard.fxml"));
            AdminDashboardController controller =fxmlLoader.getController();
            controller.updateTable();
            displayStatus();
            ApplicationCore.getInstance().getSceneManager().showScene("admin-dashboard");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }
    public void displayStatus()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Congratulation");
        alert.setHeaderText("Successfully");
        alert.setContentText("Create account successful");
        alert.showAndWait();
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
            if (this.account !=null)
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
    }
}
