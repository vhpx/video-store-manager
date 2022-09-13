package com.guccigang.videostoremanager.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class AdminDashboardController implements Initializable {

    @FXML
    private TextField accountMngSearchBar;

    @FXML
    private AnchorPane background;

    @FXML
    private Button btnAccount;

    @FXML
    private Button btnAccountMngSearch;

    @FXML
    private Button btnItemSearch;

    @FXML
    private Button btnItems;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnSetting;

    @FXML
    private Button btnTransactionSearch;

    @FXML
    private Button btnTransactions;

    @FXML
    private ComboBox<String> cbAccountMng;

    @FXML
    private ComboBox<String> cbItem;

    @FXML
    private ComboBox<String> cbTransactions;

    @FXML
    private TextField itemSearchBar;

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
    private Pane pnlStatus;

    @FXML
    private GridPane pnlTransactions;

    @FXML
    private VBox settingBackground;

    @FXML
    private HBox settingBackground2;

    @FXML
    private TableView<?> tableItems;

    @FXML
    private TableView<?> tableTransactions;

    @FXML
    private TableView<?> tableUserAccounts;

    @FXML
    private TextField transactionSearchBar;

    @FXML
    void search(ActionEvent event) {

    }

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        cbItem.setItems(FXCollections.observableArrayList("Titles", "IDs", "Display All", "Display Out Of Stock"));
        cbItem.buttonCellProperty(FXCollections.);
        cbAccountMng.setItems(FXCollections.observableArrayList("All Customers", "Guest", "Regular", "VIP"));
    }

    @FXML
    private void handleClick(ActionEvent event) {
        if(event.getSource() == btnAccount) {
            lbiStatusMini.setText("/Menu/Account");
            lbiStatus.setText("Account Management");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(201, 198, 193), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlAccount.toFront();
        }
        else if(event.getSource() == btnItems) {
            lbiStatusMini.setText("/Menu/Items");
            lbiStatus.setText("Items Menu");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(201, 198, 193), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlItems.toFront();
        }
        else if(event.getSource() == btnTransactions) {
            lbiStatusMini.setText("/Menu/Transactions");
            lbiStatus.setText("Transactions");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(201, 198, 193), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlTransactions.toFront();
        }
        else if(event.getSource() == btnLogOut) {
            // Switch to login screen
        }
    }
}

