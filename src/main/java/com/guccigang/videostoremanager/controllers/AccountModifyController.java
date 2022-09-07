package com.guccigang.videostoremanager.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountModifyController implements Initializable {

    TranslateTransition translate;
    @FXML
     CheckBox address;

    @FXML
     CheckBox name;

    @FXML
    Button submit;

    @FXML
    CheckBox phone;

    @FXML
    private VBox namePane;
    @FXML
    private VBox phonePane;
    @FXML
    private VBox addressPane;

    @FXML
    private Pane mainPane;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField phoneNum;

    @FXML
    private TextField homeaddress;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.namePane.setVisible(false);
        this.phonePane.setVisible(false);
        this.addressPane.setVisible(false);
        this.namePane.layoutYProperty().bind(this.name.layoutYProperty().add(10));
        this.phonePane.layoutYProperty().bind(this.phone.layoutYProperty().add(10));
        this.addressPane.layoutYProperty().bind(this.address.layoutYProperty().add(10));
        this.submit.layoutXProperty().bind(this.mainPane.prefWidthProperty().divide(2));
        this.submit.layoutYProperty().bind(this.address.layoutYProperty().add(190));
        this.namePane.setLayoutX(50);
        this.phonePane.setLayoutX(50);
        this.addressPane.setLayoutX(50);
    }
    public void moveVertical(Node checkBox, double value)
    {
        checkBox.setLayoutY(checkBox.getLayoutY()+value);
    }


    @FXML
    void changeName(ActionEvent event) {
        if (this.name.isSelected())
        {
            moveVertical(this.phone,namePane.getPrefHeight());
            moveVertical(this.address,namePane.getPrefHeight());
            this.namePane.setVisible(true);
        }else {
            moveVertical(this.phone, - namePane.getPrefHeight());
            moveVertical(this.address, - namePane.getPrefHeight());
            this.namePane.setVisible(false);
        }

    }

    @FXML
    void changePhone(ActionEvent event) {
        if (this.phone.isSelected())
        {
            moveVertical(this.address,this.phonePane.getPrefHeight());
            this.phonePane.setVisible(true);
        }else {
            moveVertical(this.address, -this.phonePane.getPrefHeight());
            this.phonePane.setVisible(false);
        }
    }
    @FXML
    void changeAddress(ActionEvent event) {
        if (this.address.isSelected())
            this.addressPane.setVisible(true);
        else
            this.addressPane.setVisible(false);

    }

    @FXML
    public void onSubmitButton(ActionEvent event)
    {

    }



}
