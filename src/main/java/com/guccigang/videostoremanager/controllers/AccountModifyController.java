//package com.guccigang.videostoremanager.controllers;
//
//import javafx.animation.TranslateTransition;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.control.Button;
//import javafx.scene.control.CheckBox;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.VBox;
//import javafx.scene.transform.Translate;
//import javafx.util.Duration;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class AccountModifyController implements Initializable {
//
////    TranslateTransition translate;
////
////    private SceneController sceneController;
////
////    @FXML
////     CheckBox address;
////
////    @FXML
////     CheckBox name;
////
////    @FXML
////    Button submit;
////
////    @FXML
////    CheckBox phone;
////
////    @FXML
////    private VBox namePane;
////    @FXML
////    private VBox phonePane;
////    @FXML
////    private VBox addressPane;
////
////    @FXML
////    private HBox buttonPane;
////
////    @FXML
////    private Pane mainPane;
////
////    @FXML
////    private TextField firstName;
////
////    @FXML
////    private TextField lastName;
////
////    @FXML
////    private TextField phoneNum;
////
////    @FXML
////    private TextField homeaddress;
////
////    @Override
////    public void initialize(URL url, ResourceBundle resourceBundle) {
////        this.sceneController = SceneController.getInstance();
////        this.namePane.setVisible(false);
////        this.phonePane.setVisible(false);
////        this.addressPane.setVisible(false);
////        this.namePane.layoutYProperty().bind(this.name.layoutYProperty().add(10));
////        this.phonePane.layoutYProperty().bind(this.phone.layoutYProperty().add(10));
////        this.addressPane.layoutYProperty().bind(this.address.layoutYProperty().add(10));
////        this.buttonPane.layoutYProperty().bind(this.address.layoutYProperty().add(190));
////        this.namePane.setLayoutX(50);
////        this.phonePane.setLayoutX(50);
////        this.addressPane.setLayoutX(50);
////    }
////    public void moveVertical(Node checkBox, double value)
////    {
////        checkBox.setLayoutY(checkBox.getLayoutY()+value);
////    }
////
////
////    @FXML
////    void changeName(ActionEvent event) {
////        if (this.name.isSelected())
////        {
////            moveVertical(this.phone,namePane.getPrefHeight());
////            moveVertical(this.address,namePane.getPrefHeight());
////            this.namePane.setVisible(true);
////        }else {
////            moveVertical(this.phone, - namePane.getPrefHeight());
////            moveVertical(this.address, - namePane.getPrefHeight());
////            this.namePane.setVisible(false);
////        }
////
////    }
////
////    @FXML
////    void changePhone(ActionEvent event) {
////        if (this.phone.isSelected())
////        {
////            moveVertical(this.address,this.phonePane.getPrefHeight());
////            this.phonePane.setVisible(true);
////        }else {
////            moveVertical(this.address, -this.phonePane.getPrefHeight());
////            this.phonePane.setVisible(false);
////        }
////    }
////    @FXML
////    void changeAddress(ActionEvent event) {
////        if (this.address.isSelected())
////            this.addressPane.setVisible(true);
////        else
////            this.addressPane.setVisible(false);
////
////    }
////
////    @FXML
////    public void onSubmitButton(ActionEvent event)
////    {
////
////    }
////    @FXML
////    public void back(ActionEvent event)
////    {
////        this.sceneController.showScene("account");
////    }
//
//}

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

public class AccountModifyController implements Initializable {
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
    private AnchorPane displayPane;

    @FXML
    private Button saveButton = new Button();
    @FXML
    void backToDashboard(ActionEvent event) {
        this.editInfoPane.setVisible(false);
        this.displayPane.setVisible(true);
        this.sceneController.showScene("account");
    }

    @FXML
    void editProfile(ActionEvent event) {
        this.editInfoPane.setVisible(true);
        this.displayPane.setVisible(false);
    }

    @FXML
    void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Your are about to log out your account.");
        alert.setContentText("Are you sure that your want to log out?");
        if (alert.showAndWait().orElseThrow() == ButtonType.OK) {
            this.editInfoPane.setVisible(false);
            this.displayPane.setVisible(true);
            this.sceneController.showScene("login");
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
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/images/images.png")));
        circle.setFill(new ImagePattern(image));
        this.editInfoPane.setVisible(false);
        this.displayPane.setVisible(true);
        this.saveButton.layoutXProperty().bind(displayPane.prefWidthProperty().divide(2).add(190).subtract(50));
    }
}
