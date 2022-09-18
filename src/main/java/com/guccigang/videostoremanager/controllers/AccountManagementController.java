package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.auth.Account;
import com.guccigang.videostoremanager.auth.AuthManager;
import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.errors.AccountException;
import com.guccigang.videostoremanager.scenes.SceneManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    private AuthManager authManager = ApplicationCore.getInstance().getAuthManager();
    private Account account = authManager.getCurrentAccount();
    private SceneManager sceneController;
    @FXML
    private Circle circle;
    @FXML
    private VBox editInfoPane;

    @FXML
    private TextField nameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;


    @FXML
    private Label profileNameLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneLabel;

    @FXML
    private Label pointLabel;

    @FXML
    private Label rankingLabel;

    @FXML
    private Label optionLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label passwordLabel;



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
    void logout() {
        if (showLogoutConfirmation()) {
            var app = ApplicationCore.getInstance();
            var auth = app.getAuthManager();

            // Log out the user
            auth.logout();
            app.save();

            // Show the auth screen
            var manager = app.getSceneManager();
            manager.showScene("auth");
        }
    }

    static boolean showLogoutConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log out");
        alert.setHeaderText("Your are about to log out the program.");
        alert.setContentText("Are you sure that your want to log out the program?");
        return alert.showAndWait().orElseThrow() == ButtonType.OK;
    }

    @FXML
    void save(ActionEvent event)  {
        try{
            System.out.println("asdfasdfasdf");
            if (this.passwordField.getText().length()!=0)
                account.updatePassword(passwordField.getText());
            if (this.phoneField.getText().length()!=0)
                account.updatePhone(phoneField.getText());
            if (this.addressField.getText().length()!=0)
                account.updateAddress(addressField.getText());
            if (this.nameField.getText().length()!=0)
                account.updateName(nameField.getText());
        }catch (AccountException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Update Error");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("Please try again!");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Update");
        alert.setHeaderText("Successful");
        alert.setContentText("Your account information update has been modified successfully!");

        System.out.println(account.toString());
        showSuccessfullyUpdate();
        displayAccountinfo();
    }

    static void showSuccessfullyUpdate() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Update");
        alert.setHeaderText("Successful");
        alert.setContentText("Your account information update has been modified successfully!");

    }

    @FXML
    void setting(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!account.getRole().equals("VIP"))
            this.pointLabel.setVisible(false);
        sceneController = ApplicationCore.getInstance().getSceneManager();
        Image image = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/images/images.png")));
        circle.setFill(new ImagePattern(image));
        this.editInfoPane.setVisible(false);
        this.displayPane.setVisible(true);
        this.profileNameLabel.setText(account.getName());
        //display the account information
        displayAccountinfo();

    }

    public void displayAccountinfo()
    {
        this.nameLabel.setText("Full Name: "+ account.getName());
        this.phoneLabel.setText("Phone Number "+ account.getPhone());
        this.addressLabel.setText("Address: "+account.getAddress());
        this.rankingLabel.setText("Role: " + account.getRole());
        //this.pointLabel.setText("Points: " + String.valueOf(account.getPoints()));
        this.userNameLabel.setText("User Name: "+ account.getUsername());
        this.passwordLabel.setText("Password: " + account.getPassword());
    }

}
