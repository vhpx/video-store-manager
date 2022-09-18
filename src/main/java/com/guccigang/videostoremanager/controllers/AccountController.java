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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AccountController implements Initializable {
    private final ObservableList<String> list = FXCollections.observableArrayList("Account Profile");
    private final ObservableList<String> sortOptions = FXCollections.observableArrayList("Alphabet", "Rental Type", "Genre");

    @FXML
    TextField itemField;

    @FXML
    TextField historyField;

    @FXML
    TextField borrowedField;

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
    private final TableColumn<Item, String> itemNoCopy = new TableColumn<>("Copy Available");

    @FXML
    private TableView<Transaction> historyTable = new TableView<>();

    @FXML
    private final TableColumn<Transaction, String> historyTitle = new TableColumn<>("Title");

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

    // Initialize the controller class
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.comboBox.setItems(list);
        this.borrowedPane.setVisible(true);
        this.transaction.setVisible(false);
        this.browsePane.setVisible(false);
        this.historyField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            // This method is called whenever the text in the search field is changed
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue.length()==0)
                    historyTable.setItems(getTransactions());
            }
        });
        this.itemField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            // This method is called whenever the text in the search field is changed
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue.length()==0)
                    rentTable.setItems(getItems());
            }
        });
        this.borrowedField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            // This method is called whenever the text in the search field is changed
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue.length()==0)
                    borrowedTable.setItems(getBorrowedItem());
            }
        });
        // Set the table columns
        Image image = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/images/images.png")));
        circleImage.setFill(new ImagePattern(image));

        var app = ApplicationCore.getInstance();
        var auth = app.getAuthManager();

        // Set the greeting label
        if (auth.isLoggedIn()) { // If the user is logged in
            var username = auth.isAdmin() ? "admin" : auth.getCurrentAccount().getUsername(); // Get the username
            this.greetingLabel.setText("Hello, " + username + "!");
        }

        displayItemTable();
        displayHistoryTable();
        displayBorrowedListTable();
    }

    // This method is called whenever the user clicks on the "Account Profile" button
    private ObservableList<Transaction> getTransactions() {
        var appCore = ApplicationCore.getInstance(); // Get the application core
        var transactionManager = appCore.getTransactionManager(); // Get the transaction manager
        var transactions = transactionManager.getTransactions(appCore.getAuthManager().getCurrentAccount(), true); // Get the transactions

        return FXCollections.observableArrayList(transactions); // Return the transactions
    }

    // Get items
    private ObservableList<Item> getItems() {
        var appCore = ApplicationCore.getInstance(); // Get the application core
        var itemManager = appCore.getItemManager(); // Get the item manager
        var items = itemManager.getAll(); // Get the items

        return FXCollections.observableArrayList(items); // Return the items
    }

    // Get borrowed items
    private ObservableList<Item> getBorrowedItem() {
        var appCore = ApplicationCore.getInstance(); // Get the application core
        var account = appCore.getAuthManager().getCurrentAccount(); // Get the current account
        var items = account.getRentedItems(); // Get the items

        return FXCollections.observableArrayList(items); // Return the items
    }

    // Display the item table
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
            // Update item button
            protected void updateItem(Item item, boolean empty) {
                returnButton.getStyleClass().add("buttonYellow");
                returnButton.setPrefWidth(150);
                super.updateItem(item, empty);

                // If the cell is not empty
                if (item == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(returnButton);
                returnButton.setOnAction(
                        event -> {
                            try {
                                var authManager = ApplicationCore.getInstance().getAuthManager(); // Get the auth manager
                                var account = authManager.getCurrentAccount(); // Get the current account
                                Item currentItem = getTableView().getItems().get(getIndex()); // Get the current item
                                displayReturnStatus(currentItem, account); // Display the return status


                                borrowedTable.setItems(getBorrowedItem()); // Update the borrowed table
                                rentTable.setItems(getItems()); // Update the rent table
                                historyTable.setItems(getTransactions()); // Update the history table

                                borrowedTable.refresh(); // Refresh the borrowed table
                                rentTable.refresh(); // Refresh the rent table
                                historyTable.refresh(); // Refresh the history table

                                // account.displayRental();
                            } catch (TransactionException e) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION); // Create an alert
                                alert.setTitle("Return Error");
                                alert.setHeaderText(e.getMessage()); // Set the header text
                                alert.setContentText("Sorry for the inconvenience.");
                                alert.showAndWait(); // Show the alert
                            }
                        });
            }
        });

        // Set the items
        borrowedTable.setItems(getBorrowedItem());
        borrowedTable.getColumns().addAll(listTitle, listGenre, listRentalType, listLoanType, borrowedListAction);
    }

    // Display item return status
    private void displayReturnStatus(Item item, Account account) throws TransactionException {
        if (!showConfirmationReturn())
            return;
        account.returnItem(item);
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Create an alert
        alert.setTitle("Congratulation!");
        alert.setHeaderText("Return Successful");
        alert.setContentText("You successfully return the item!");
        alert.showAndWait(); // Show the alert
    }

    // Display a confirmation to return item
    static boolean showConfirmationReturn() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // Create an alert
        alert.setTitle("Confirmation");
        alert.setHeaderText("Your are about to return this item.");
        alert.setContentText("Are you sure that you want to return it?");
        return alert.showAndWait().orElseThrow() == ButtonType.OK; // Show the alert
    }

    // Display item table
    private void displayItemTable() {
        // set the column value for the item table
        itemTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        itemGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        itemRentalType.setCellValueFactory(new PropertyValueFactory<>("rentalType"));
        itemLoanType.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        itemFees.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));
        itemNoCopy.setCellValueFactory(new PropertyValueFactory<>("stock"));
        itemAction.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        itemAction.setCellFactory(param -> new TableCell<Item, Item>() {
            private final Button rentButton = new Button("Rent");

            @Override
            // Update item button
            protected void updateItem(Item item, boolean empty) {
                rentButton.getStyleClass().add("buttonYellow");
                rentButton.setPrefWidth(150);
                super.updateItem(item, empty);

                // If the cell is not empty
                if (item == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(rentButton);
                rentButton.setOnAction(
                        event -> {
                            try {
                                var authManager = ApplicationCore.getInstance().getAuthManager(); // Get the auth manager
                                var account = authManager.getCurrentAccount(); // Get the current account
                                Item currentItem = getTableView().getItems().get(getIndex()); // Get the current item
                                System.out.println(currentItem.toString()); // Print the current item
                                System.out.println(account.toString()); // Print the current account
                                displayBorrowStatus(currentItem, account); // Display the borrow status

                                borrowedTable.setItems(getBorrowedItem()); // Update the borrowed table
                                rentTable.setItems(getItems()); // Update the rent table
                                historyTable.setItems(getTransactions()); // Update the history table

                                borrowedTable.refresh(); // Refresh the borrowed table
                                rentTable.refresh(); // Refresh the rent table
                                historyTable.refresh();
                                // account.displayRental();
                            } catch (ItemException e) {
                                throw new RuntimeException(e); // Throw the exception
                            } catch (AccountException e) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION); // Create an alert
                                alert.setTitle("Borrow Error");
                                alert.setHeaderText(e.getMessage()); // Set the header text
                                alert.setContentText("Sorry for the inconvenience.");
                                alert.showAndWait(); // Show the alert
                            }

                        });
            }
        });
        rentTable.setItems(getItems());
        rentTable.getColumns().addAll(itemTitle, itemGenre, itemRentalType, itemLoanType, itemFees, itemNoCopy,
                itemAction);

    }
    
    // Display borrowed status
    private void displayBorrowStatus(Item item, Account account) throws ItemException, AccountException {
        if (!showConfirmBorrow())
            return;
        account.rentItem(item);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Borrow Successful");
        alert.setContentText("You successfully borrow the item!");
        alert.showAndWait();
    }
    
    // Display a confirmation to borrow
    static boolean showConfirmBorrow() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Your are about to borrow this item.");
        alert.setContentText("Are you sure that you want to borrow it?");
        return alert.showAndWait().orElseThrow() == ButtonType.OK;
    }

    // Display history table
    private void displayHistoryTable() {
        // set column value for the history table
        historyTitle.setCellValueFactory(
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
        historyTable.getColumns().addAll(historyTitle, historyGenre, historyRentalType, historyLoanType,
                historyItemStatus);

    }

    @FXML
    // Display the account information
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
    // Log out
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

    // Display a logout confirmation message
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

    @FXML
    private void searchHistory() {
        historyTable.setItems(filterListTransaction(getTransactions(),historyField.getText())); // Update the history table
    }

    @FXML
    private void searchBorrowedItem()
    {
        borrowedTable.setItems(filterList(getItems(),borrowedField.getText()));
    }

    @FXML
    private void searchItem()
    {
        rentTable.setItems(filterList(getItems(),itemField.getText()));
    }

    // Filter the list of items
    private ObservableList<Item> filterList(List<Item> list, String searchText){
        List<Item> filteredList = new ArrayList<>();
        for (Item item : list){
            if(searchByType(item, searchText)) filteredList.add(item);
        }
        return FXCollections.observableList(filteredList);
    }
    private ObservableList<Transaction> filterListTransaction(List<Transaction> list, String searchText){
        List<Transaction> filteredList = new ArrayList<>();
        for (Transaction i : list){
            if(searchByType(i.getItem(), searchText)) filteredList.add(i);
        }
        return FXCollections.observableList(filteredList);
    }

    private boolean searchByType(Item item, String searchText)
    {
       return item.getTitle().equalsIgnoreCase(searchText)? true:false;
    }
}