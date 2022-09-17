package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.errors.AccountException;
import com.guccigang.videostoremanager.errors.ItemException;
import com.guccigang.videostoremanager.errors.TransactionException;
import com.guccigang.videostoremanager.items.Item;
import com.guccigang.videostoremanager.scenes.SceneManager;
import com.guccigang.videostoremanager.transactions.Transaction;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class AccountController implements Initializable {
    private final ObservableList<String> list = FXCollections.observableArrayList("Account Profile");
    private final ObservableList<String> sortOptions = FXCollections.observableArrayList("Alphabet",
            "Rental Type",
            "Genre");

    @FXML
    Circle circleImage;
    @FXML
    GridPane transaction;

    @FXML
    AnchorPane accountPane;
    @FXML
    GridPane browsePane;

    @FXML
    Label optionLabel;

    @FXML
    Label statusMini;

    @FXML
    Label greetingLabel;
    @FXML
    ComboBox<String> sortComboBox = new ComboBox<>();
    @FXML
    ComboBox<String> comboBox = new ComboBox<>();

    @FXML
    ComboBox<String> historyComboBox = new ComboBox<>();


    @FXML
    TableView<Item> rentTable = new TableView<>();

    @FXML
    private final TableColumn<Item, String> itemTitle = new TableColumn<>("Title");
    @FXML
    private final TableColumn<Item, String> itemGenre = new TableColumn<>("Genre");
    @FXML
    private final TableColumn<Item, String> itemRentalType = new TableColumn<>("Rental Type");
    @FXML
    private final TableColumn<Item, String>  itemLoanType = new TableColumn<>("Loan Type");
    @FXML
    private final TableColumn<Item, Double> itemFees = new TableColumn<>("Fees");

    @FXML
    private final TableColumn<Item, String> itemNoCoppy = new TableColumn<>("Copy Available");

    @FXML
    private TableView<Transaction> historyTable = new TableView<>();

    @FXML
    private final TableColumn<Transaction, String> hisotryTitle = new TableColumn<>("Title");

    @FXML
    private final TableColumn<Transaction, String> historyGenre = new TableColumn<>("Genre");

    @FXML
    private final TableColumn<Transaction, String> historyRentalType = new TableColumn<>("Rental Type");
    @FXML
    private final TableColumn<Transaction, String> historyLoanType = new TableColumn<>("Loan Type");
    @FXML
    private final TableColumn<Transaction, String> historyItemStatus = new TableColumn<>("Rental Status");

    @FXML
    private final TableColumn <Transaction, Transaction> historyAction = new TableColumn<>("Action");
    @FXML
    private final TableColumn <Item, Item> itemAction = new TableColumn<>("Action");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboBox.setItems(list);
        this.transaction.setVisible(true);
        this.browsePane.setVisible(false);

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/images/images.png")));
        circleImage.setFill(new ImagePattern(image));

        var app = ApplicationCore.getInstance();
        var auth = app.getAuthManager();

        if (auth.isLoggedIn()) {
            var username = auth.isAdmin() ? "admin" : auth.getCurrentAccount().getUsername();
            this.greetingLabel.setText("Hello, " + username + "!");
        }
        displayItemTable();
        displayHistoryTable();
    }

    private ObservableList<Transaction> getTransactions() {
        var appCore = ApplicationCore.getInstance();
        var transactionManager = appCore.getTransactionManager();
        var transactions = transactionManager.getTransactions(appCore.getAuthManager().getCurrentAccount());

        return FXCollections.observableArrayList(transactions);
    }
    private ObservableList<Item> getItems() {
        var appCore = ApplicationCore.getInstance();
        var itemManager = appCore.getItemManager();
        var items = itemManager.getAll();

        return FXCollections.observableArrayList(items);
    }
    private void displayItemTable()
    {
        //set the column value for the item table
        itemTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        itemGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        itemRentalType.setCellValueFactory(new PropertyValueFactory<>("rentalType"));
        itemLoanType.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        itemFees.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));
        itemNoCoppy.setCellValueFactory(new PropertyValueFactory<>("stock"));
        itemAction.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        itemAction.setCellFactory(param -> new TableCell<Item, Item>() {
            private final Button rentButton = new Button("Rent");

            @Override
            protected void updateItem(Item item, boolean empty) {
                rentButton.getStyleClass().add("buttonYellow");
                super.updateItem(item, empty);

                if (item == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(rentButton);
                rentButton.setOnAction(
                        event -> {
                            try {
                                var authManager = ApplicationCore.getInstance().getAuthManager();
                                var account = authManager.getCurrentAccount();
                                Item currentItem =  getTableView().getItems().get(getIndex());
                                System.out.println(currentItem.toString());
                                System.out.println(account.toString());
                                account.rent(currentItem);
                                account.displayRental();
                            } catch (ItemException e) {
                                throw new RuntimeException(e);
                            } catch (AccountException e) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Borrow Error");
                                alert.setHeaderText(e.getMessage());
                                alert.setContentText("Sorry for the inconvenience.");
                            }

                        }
                );
            }
        });


        rentTable.setItems(getItems());
        rentTable.getColumns().addAll(itemTitle,itemGenre,itemRentalType,itemLoanType,itemFees,itemNoCoppy,itemAction);


    }

    private void displayHistoryTable()
    {
        //set column value for the history table
        hisotryTitle.setCellValueFactory(transaction -> new ReadOnlyStringWrapper(transaction.getValue().getItem().getTitle()));
        historyGenre.setCellValueFactory(transaction -> new ReadOnlyStringWrapper(transaction.getValue().getItem().getGenre()));
        historyRentalType.setCellValueFactory(transaction -> new ReadOnlyStringWrapper(transaction.getValue().getItem().getRentalType()));
        historyLoanType.setCellValueFactory(transaction -> new ReadOnlyStringWrapper(transaction.getValue().getItem().getLoanType()));
        historyItemStatus.setCellValueFactory(transaction -> new ReadOnlyStringWrapper(transaction.getValue().isResolved() ? "Returned" : "Borrowing"));
        historyItemStatus.setSortType(TableColumn.SortType.ASCENDING);
        historyAction.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        historyAction.setCellFactory(param -> new TableCell<Transaction, Transaction>() {
            private final Button returnButton = new Button("Return");


            protected void updateItem(Item item, boolean empty) {
                returnButton.getStyleClass().add("buttonYellow");
                //super.updateItem(item, empty);

                if (item == null ||historyItemStatus.equals("Returned")) {
                    setGraphic(null);
                    return;
                }
                setGraphic(returnButton);
                returnButton.setOnAction(
                        event -> {
                            try {
                                var authManager = ApplicationCore.getInstance().getAuthManager();
                                var account = authManager.getCurrentAccount();
                                Transaction transact =  getTableView().getItems().get(getIndex());
                                Item currentItem = transact.getItem();
                                account.returnItem(currentItem);
                            } catch (TransactionException e) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Borrow Error");
                                alert.setHeaderText(e.getMessage());
                                alert.setContentText("Sorry for the inconvenience.");
                            }

                        }
                );
            }
        });
        //display the historyTable
        historyTable.setItems(getTransactions());
        historyTable.getColumns().addAll(hisotryTitle, historyGenre, historyRentalType, historyLoanType, historyItemStatus,historyAction);
        historyTable.getSortOrder().add(historyItemStatus);
        historyTable.sort();

    }


    @FXML
    private void comboBoxChanged() {
        if (this.comboBox.getValue().equals("Account Profile")) {
            var app = ApplicationCore.getInstance();
            var manager = app.getSceneManager();
            manager.showScene("account-info");
            this.comboBox.getSelectionModel().clearSelection();
//            accountPane.getChildren().remove(comboBox);
//            comboBox = new ComboBox();
//            comboBox.setItems(list);// do whatever else you need to format your ComboBox
//            accountPane.getChildren().add(comboBox);

        }
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
    void browseItem(ActionEvent event) {
        this.browsePane.setVisible(true);
        this.transaction.setVisible(false);
        this.optionLabel.setText("Borrow Item");
        this.statusMini.setText("Dashboard/ Borrow Item");
    }


    @FXML
    void historyButton(ActionEvent event) {
        this.browsePane.setVisible(false);
        this.transaction.setVisible(true);
        this.optionLabel.setText("Renting History");
        this.statusMini.setText("Dashboard/ Renting History");
    }

    @FXML
    void returnItem(ActionEvent event) {

    }

    @FXML
    void borrowItem(ActionEvent event) {

    }
}
