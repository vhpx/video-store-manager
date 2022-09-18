package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.scenes.SceneManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AccountModificationController implements Initializable {

    @FXML
    HBox pointPane = new HBox();
    private SceneManager sceneController = ApplicationCore.getInstance().getSceneManager();

    @FXML
    private TextField addressField;

    @FXML
    private ComboBox<String> comboBoxType = new ComboBox<>();

    @FXML
    private TextField nameField;

    @FXML
    private TextField passField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField pointField;

    @FXML
    private TextField userNameField;


    @FXML
    void cancel(ActionEvent event) {
        sceneController.showScene("admin-dashboard");
    }

    @FXML
    void save(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxType.setItems(FXCollections.observableArrayList("Guest","Regular","VIP"));

        comboBoxType.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldvalue, String newValue) {
                if (newValue.equals("VIP"))
                {
                    pointPane.setVisible(false);
                    return;
                }
                pointPane.setVisible(true);

            }
        });
        if (Flag.check == 0)
        {
           comboBoxType.setValue(Flag.account.getRole());
           nameField.setText(Flag.account.getName());
           userNameField.setText(Flag.account.getUsername());
           passField.setText(Flag.account.getPassword());
           addressField.setText(Flag.account.getAddress());
           phoneField.setText(Flag.account.getPhone());
           if (!Flag.account.getRole().equals("VIP"))
           {
               pointPane.setVisible(false);
           }else{
               pointPane.setVisible(true);
               pointField.setText(String.valueOf(Flag.account.getPoints()));
           }
        }
        if(Flag.check == 1){
            comboBoxType.setValue(null);
            nameField = new TextField();
            userNameField= new TextField();
            passField= new TextField();
            addressField= new TextField();
            phoneField= new TextField();
        }
    }
}
