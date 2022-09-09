package com.guccigang.videostoremanager.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class adminMainController implements Initializable {
    @FXML
    private Button btnAccount;

    @FXML
    private Button btnItems;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnSetting;

    @FXML
    private Button btnTransactions;

    @FXML
    private Button btnSaveChanges;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private ComboBox<String> comboBox2;

    @FXML
    private Label lbiStatus;

    @FXML
    private Label lbiStatusMini;

    @FXML
    private GridPane pnlAccount;

    @FXML
    private GridPane pnlItems;

    @FXML
    private GridPane pnlSetting;

    @FXML
    private GridPane pnlTransactions;

    @FXML
    private Pane pnlStatus;

    @FXML
    private TextField searchBar;

    @FXML
    private TextField searchBar2;

    @FXML
    private TableView<?> tableItems;

    @FXML
    private TableView<?> tableTransactions;

    @FXML
    private TableView<?> tableUserAccounts;

    @FXML
    void search(ActionEvent event) {

    }

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        comboBox.setItems(FXCollections.observableArrayList("Titles", "IDs", "Display All", "Display Out Of Stock"));
        comboBox2.setItems(FXCollections.observableArrayList("All Customers", "Guest", "Regular", "VIP"));
    }

    @FXML
    private void handleClick(ActionEvent event) {
        if(event.getSource() == btnAccount) {
            lbiStatusMini.setText("Menu/Account");
            lbiStatus.setText("Account Management");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(201, 198, 193), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlAccount.toFront();
        }
        else if(event.getSource() == btnItems) {
            lbiStatusMini.setText("Menu/Items");
            lbiStatus.setText("Items Menu");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(201, 198, 193), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlItems.toFront();
        }
        else if(event.getSource() == btnSetting) {
            lbiStatusMini.setText("Menu/Settings");
            lbiStatus.setText("Profile");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(201, 198, 193), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlSetting.toFront();
        }
        else if(event.getSource() == btnTransactions) {
            lbiStatusMini.setText("Menu/Transactions");
            lbiStatus.setText("Transactions");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(201, 198, 193), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlTransactions.toFront();
        }
        else if(event.getSource() == btnLogOut) {
            // Switch to login screen
        }
    }

}

