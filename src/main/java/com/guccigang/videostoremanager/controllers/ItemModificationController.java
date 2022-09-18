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
        if (Flag.check == 1) //create
        {
            //to be added

        }else{
            try{
                if (this.titleField.getText().length()!=0)
                    Flag.item.updateTitle(titleField.getText());
                if (this.comboxGenre.getSelectionModel().getSelectedItem().length()!=0)
                    Flag.item.updateGenre(this.comboxGenre.getSelectionModel().getSelectedItem());
                if (this.comboxLoanType.getSelectionModel().getSelectedItem().length()!=0)
                    Flag.item.updateLoanType(this.comboxLoanType.getSelectionModel().getSelectedItem());
                if (this.comboxRentalType.getSelectionModel().getSelectedItem().length()!=0)
                    Flag.item.updateRentalType(this.comboxRentalType.getSelectionModel().getSelectedItem());
                if (this.copyField!=null)
                    Flag.item.updateCopy(Integer.parseInt(copyField.getText()));
                if (this.feeField!=null)
                    Flag.item.updateCopy(Integer.parseInt(feeField.getText()));
            }catch (ItemException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information Update Error");
                alert.setHeaderText(e.getMessage());
                alert.setContentText("Please try again!");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Update");
            alert.setHeaderText("Successful");
            alert.setContentText("Your item information update has been modified successfully!");

            System.out.println(Flag.item.toString());
            sceneManager.showScene("admin-dashboard");
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

        if (Flag.check == 0)
        {
            feeField.setText(String.valueOf(Flag.item.getRentalFee()));
            copyField.setText(String.valueOf(Flag.item.getStock()));
            titleField.setText(Flag.item.getTitle());
            comboxGenre.setValue(Flag.item.getGenre());
            comboxRentalType.setValue(Flag.item.getRentalType());
            comboxLoanType.setValue(Flag.item.getLoanType());
        }
    }






}