package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.errors.ItemException;
import com.guccigang.videostoremanager.scenes.SceneManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class ItemModificationController implements Initializable {

    @FXML
    private HBox genrePane = new HBox();
    private SceneManager sceneManager = ApplicationCore.getInstance().getSceneManager();



    @FXML
    private ComboBox<String> comboxGenre = new ComboBox<String>();

    @FXML
    private ComboBox<String> comboxLoanType = new ComboBox<>();

    @FXML
    private ComboBox<String> comboxRentalType = new ComboBox<>();

    private final ObservableList<String> genreList = FXCollections.observableArrayList("Action", "Comedy", "Drama", "Horror");

    private final ObservableList<String> loanList = FXCollections.observableArrayList("2 Days", "7 Days");
    private final ObservableList<String> rentalList = FXCollections.observableArrayList("Record", "DVD", "Game");

    @FXML
    private TextField copyField;

    @FXML
    private TextField idField;

    @FXML
    private TextField feeField;

    @FXML
    private TextField titleField;

    @FXML
    void cancel(ActionEvent event) {
        this.sceneManager.showScene("admin-dashboard");
    }
    @FXML
    void save(ActionEvent event) {
        var itemManager = ApplicationCore.getInstance().getItemManager();
        ArrayList<String> inputs = new ArrayList<String>();

        inputs.add(idField.getText());
        inputs.add(this.titleField.getText());
        inputs.add(this.comboxGenre.getSelectionModel().getSelectedItem());
        inputs.add(this.comboxRentalType.getSelectionModel().getSelectedItem());
        inputs.add(this.comboxLoanType.getSelectionModel().getSelectedItem());
        inputs.add(copyField.getText());
        inputs.add(feeField.getText());


        if (Flag.check == 1) //create
        {
            System.out.println("create");
            //to be added

        }else{
            System.out.println("update");
            //tobe updated
        }

    }

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        comboxGenre.setItems(genreList);
        comboxLoanType.setItems(loanList);
        comboxRentalType.setItems(rentalList);
        comboxRentalType.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldvalue, String newValue) {

                if (!newValue.equals("Game"))
                {
                    System.out.println("it's not game");
                    genrePane.setVisible(true);
                    return;
                }
                System.out.println("it's game game");
                genrePane.setVisible(false);

            }
        });
//        if (Flag.check == 1) // the value cannot be empty when click create item
//        {
//            idField.setText("");
//            feeField.setText(null);
//            copyField.setText(null);
//            titleField.setText(null);
//            comboxGenre.setValue(null);
//            comboxRentalType.setValue(null);
//            comboxLoanType.setValue(null);
//        }
        if (Flag.check == 0)
        {
            idField.setText(Flag.item.getId());
            feeField.setText(String.valueOf(Flag.item.getRentalFee()));
            copyField.setText(String.valueOf(Flag.item.getStock()));
            titleField.setText(Flag.item.getTitle());
            comboxGenre.setValue(Flag.item.getGenre());
            comboxRentalType.setValue(Flag.item.getRentalType());
            comboxLoanType.setValue(Flag.item.getLoanType());
        }
    }






}