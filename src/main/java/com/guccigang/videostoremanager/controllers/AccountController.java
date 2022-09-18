package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.auth.Account;
import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.errors.AccountException;
import com.guccigang.videostoremanager.errors.ItemException;
import com.guccigang.videostoremanager.errors.TransactionException;
import com.guccigang.videostoremanager.items.Item;
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
    GridPane borrowedPane;

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
    ComboBox<String> comboBox = new ComboBox<>();

    @FXML
    TableView<Item> borrowedTable = new TableView<>();
    @FXML
    private final TableColumn<Item, String> listTitle = new TableColumn<>("Title");
    @FXML
    private final TableColumn<Item, String> listGenre = new TableColumn<>("Genre");
    @FXML
    private final TableColumn<Item, String> listRentalType = new TableColumn<>("Rental Type");
    @FXML
    private final TableColumn<Item, String> listLoanType = new TableColumn<>("Loan Type");

    @FXML
    TableView<Item> rentTable = new TableView<>();

    @FXML
    private final TableColumn<Item, String> itemTitle = new TableColumn<>("Title");
    @FXML
    private final TableColumn<Item, String> itemGenre = new TableColumn<>("Genre");
    @FXML
    private final TableColumn<Item, String> itemRentalType = new TableColumn<>("Rental Type");
    @FXML
    private final TableColumn<Item, String> itemLoanType = new TableColumn<>("Loan Type");
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
    private final TableColumn<Transaction, Transaction> historyAction = new TableColumn<>("Action");
    @FXML
    private final TableColumn<Item, Item> itemAction = new TableColumn<>("Action");

    @FXML
    private final TableColumn<Item, Item> borrowedListAction = new TableColumn<>("Action");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboBox.setItems(list);
        this.borrowedPane.setVisible(true);
        this.transaction.setVisible(false);
        this.browsePane.setVisible(false);

        Image image = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/images/images.png")));
        circleImage.setFill(new ImagePattern(image));

        var app = ApplicationCore.getInstance();
        var auth = app.getAuthManager();

        if (auth.isLoggedIn()) {
            var username = auth.isAdmin() ? "admin" : auth.getCurrentAccount().getUsername();
            this.greetingLabel.setText("Hello, " + username + "!");
        }
        displayItemTable();
        displayHistoryTable();
        displayBorrowedListTable();
    }

    private ObservableList<Transaction> getTransactions() {
        var appCore = ApplicationCore.getInstance();
        var transactionManager = appCore.getTransactionManager();
        var transactions = transactionManager.getTransactions(appCore.getAuthManager().getCurrentAccount(), true);

        return FXCollections.observableArrayList(transactions);
    }

    private ObservableList<Item> getItems() {
        var appCore = ApplicationCore.getInstance();
        var itemManager = appCore.getItemManager();
        var items = itemManager.getAll();

        return FXCollections.observableArrayList(items);
    }

    private ObservableList<Item> getBorrowedItem() {
        var appCore = ApplicationCore.getInstance();
        var account = appCore.getAuthManager().getCurrentAccount();
        var items = account.getRentedItems();

        return FXCollections.observableArrayList(items);
    }

    private void displayBorrowedListTable() {
        // set the column value for the item table
        listTitle.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getTitle()));
        listGenre.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getGenre()));
        listRentalType.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getRentalType()));
        listLoanType.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getLoanType()));
        borrowedListAction.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        borrowedListAction.setCellFactory(param -> new TableCell<Item, Item>() {
            private final Button returnButton = new Button("Return");

            @Override
            protected void updateItem(Item item, boolean empty) {
                // rentButton.getStyleClass().add("buttonYellow");
                super.updateItem(item, empty);

                if (item == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(returnButton);
                returnButton.setOnAction(
                        event -> {
                            try {
                                var authManager = ApplicationCore.getInstance().getAuthManager();
                                var account = authManager.getCurrentAccount();
                                Item currentItem = getTableView().getItems().get(getIndex());
                                account.returnItem(currentItem);
                                // account.displayRental();
                            } catch (TransactionException e) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Return Error");
                                alert.setHeaderText(e.getMessage());
                                alert.setContentText("Sorry for the inconvenience.");
                                alert.showAndWait();
                            }
                        });
            }
        });
        borrowedTable.setItems(getBorrowedItem());
        borrowedTable.getColumns().addAll(listTitle, listGenre, listRentalType, listLoanType, borrowedListAction);
    }

    private void displayItemTable() {
        // set the column value for the item table
        itemTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        itemGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        itemRentalType.setCellValueFactory(new PropertyValueFactory<>("rentalType"));
        itemLoanType.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        itemFees.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));
        itemNoCoppy.setCellValueFactory(new PropertyValueFactory<>("stock"));
        itemAction.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue()));
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
                                Item currentItem = getTableView().getItems().get(getIndex());
                                System.out.println(currentItem.toString());
                                System.out.println(account.toString());
                                displayBorrowStatus(currentItem,account);
                                rentTable.refresh();
                                historyTable.refresh();
                                borrowedTable.refresh();
                                // account.displayRental();
                            } catch (ItemException e) {
                                throw new RuntimeException(e);
                            } catch (AccountException e) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Borrow Error");
                                alert.setHeaderText(e.getMessage());
                                alert.setContentText("Sorry for the inconvenience.");
                                alert.showAndWait();
                            }

                        });
            }
        });
        rentTable.setItems(getItems());
        rentTable.getColumns().addAll(itemTitle, itemGenre, itemRentalType, itemLoanType, itemFees, itemNoCoppy,
                itemAction);

    }
    private void displayBorrowStatus(Item item, Account account) throws ItemException, AccountException {
        if (!showConfirmBorrow())
            return;
        account.rentItem(item);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Borrow Successful");
        alert.setContentText("You successfully borrow the item!");
        alert.showAndWait();
    }
    static boolean showConfirmBorrow() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Your are about to borrow.");
        alert.setContentText("Are you sure that you want to borrow it?");
        return alert.showAndWait().orElseThrow() == ButtonType.OK;
    }




    private void displayHistoryTable() {
        // set column value for the history table
        hisotryTitle.setCellValueFactory(
                transaction -> new ReadOnlyStringWrapper(transaction.getValue().getItem().getTitle()));
        historyGenre.setCellValueFactory(
                transaction -> new ReadOnlyStringWrapper(transaction.getValue().getItem().getGenre()));
        historyRentalType.setCellValueFactory(
                transaction -> new ReadOnlyStringWrapper(transaction.getValue().getItem().getRentalType()));
        historyLoanType.setCellValueFactory(
                transaction -> new ReadOnlyStringWrapper(transaction.getValue().getItem().getLoanType()));
        historyItemStatus.setCellValueFactory(transaction -> new ReadOnlyStringWrapper(
                transaction.getValue().isResolved() ? "Returned" : "Borrowing"));
        //
        historyTable.setItems(getTransactions());
        historyTable.getColumns().addAll(hisotryTitle, historyGenre, historyRentalType, historyLoanType,
                historyItemStatus);

    }

    @FXML
    private void comboBoxChanged() {
        if (this.comboBox.getValue().equals("Account Profile")) {
            var app = ApplicationCore.getInstance();
            var manager = app.getSceneManager();
            manager.showScene("account-info");
            this.comboBox.getSelectionModel().clearSelection();
            // accountPane.getChildren().remove(comboBox);
            // comboBox = new ComboBox();
            // comboBox.setItems(list);// do whatever else you need to format your ComboBox
            // accountPane.getChildren().add(comboBox);

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
        this.borrowedPane.setVisible(false);
        this.browsePane.setVisible(true);
        this.transaction.setVisible(false);
        this.optionLabel.setText("Borrow Item");
        this.statusMini.setText("Dashboard/ Borrow Item");
    }

    @FXML
    void borrowedButton(ActionEvent event) {
        this.borrowedPane.setVisible(true);
        this.browsePane.setVisible(false);
        this.transaction.setVisible(false);
        this.optionLabel.setText("Current Borrowed List");
        this.statusMini.setText("Dashboard/ Current Borrowed List");
    }

    @FXML
    void historyButton(ActionEvent event) {
        this.borrowedPane.setVisible(false);
        this.browsePane.setVisible(false);
        this.transaction.setVisible(true);
        this.optionLabel.setText("Renting History");
        this.statusMini.setText("Dashboard/ Renting History");
    }

}
