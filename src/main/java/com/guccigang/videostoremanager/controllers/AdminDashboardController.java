package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.auth.Account;
import com.guccigang.videostoremanager.auth.AccountManager;
import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.errors.AccountException;
import com.guccigang.videostoremanager.errors.ItemException;
import com.guccigang.videostoremanager.items.Item;
import com.guccigang.videostoremanager.items.ItemManager;
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
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminDashboardController implements Initializable {

    private AccountManager accountManager = ApplicationCore.getInstance().getAccountManager(); // get the account manager
    private ItemManager itemManager = ApplicationCore.getInstance().getItemManager(); // get the item manager

    @FXML
    private Button btnAccount;

    @FXML
    private TextField itemField;

    @FXML
    private TextField accountField;

    @FXML
    private Button btnItems;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnTransactions;

    @FXML
    private ComboBox<String> comboBoxItem = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBoxAccount  = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBoxSortItem = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBoxSortAccount  = new ComboBox<>();
    @FXML
    private Label lbiStatus;

    @FXML
    private Label lbiStatusMini;

    @FXML
    private GridPane pnlAccount;

    @FXML
    private GridPane pnlItems;
    @FXML
    private Pane pnlStatus;

    @FXML
    private GridPane pnlTransactions;

    @FXML
    private TableView<Item> itemsTable = new TableView<>();

    @FXML
    private TableView<Transaction> transactionsTable = new TableView<>();

    @FXML
    private TableView<Account> accountsTable = new TableView<>();

    @FXML
    private final TableColumn<Account, String> accountId = new TableColumn<>("ID");

    @FXML
    private final TableColumn<Account, String> accountAddress = new TableColumn<>("Address");

    @FXML
    private final TableColumn<Account, String> accountUsername = new TableColumn<>("Username");

    @FXML
    private final TableColumn<Account, String> accountPassword = new TableColumn<>("Password");

    @FXML
    private final TableColumn<Account, String> accountPhone = new TableColumn<>("Phone");

    @FXML
    private final TableColumn<Account, Integer> accountPoints = new TableColumn<>("Points");

    @FXML
    private final TableColumn<Account, String> accountRole = new TableColumn<>("Role");
    @FXML
    private final TableColumn<Account, String> accountName = new TableColumn<>("Name");

    @FXML
    private TableColumn<Item, Item.Genre> itemGenre = new TableColumn<>("Genre");

    @FXML
    private final TableColumn<Item, String> itemID = new TableColumn<>("ID");
    ;

    @FXML
    private TableColumn<Item, Item.LoanType> itemLoanType = new TableColumn<>("Loan Type");

    @FXML
    private TableColumn<Item, Double> itemRentalFees = new TableColumn<>("Fee");

    @FXML
    private TableColumn<Item, Item.RentalType> itemRentalType = new TableColumn<>("Rental Type");

    @FXML
    private TableColumn<Item, Integer> itemStockStatus = new TableColumn<>("Number of Copy");

    @FXML
    private final TableColumn<Item, String> itemTitle = new TableColumn<>("Title");

    // Transactions
    @FXML
    private TableColumn<Transaction, String> transAccID = new TableColumn<>("Account ID");

    @FXML
    private TableColumn<Transaction, String> transAccName = new TableColumn<>("Account Name");

    @FXML
    private TableColumn<Transaction, String> transItemID = new TableColumn<>("Item ID");

    @FXML
    private TableColumn<Transaction, String> transItemName = new TableColumn<>("Item Name");

    @FXML
    private TableColumn<Transaction, String> transStatus = new TableColumn<>("Status");



    @FXML
    private TableColumn<Item, Item> itemAction = new TableColumn<>("Action");
    @FXML
    private TableColumn<Account, Account> accountAction = new TableColumn<>("Action");

    @FXML
    void search(ActionEvent event) {

    }

    @Override
    // initialize the dashboard
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        this.comboBoxItem.setItems(FXCollections.observableArrayList("BY TITLE","BY ID"));
        this.comboBoxAccount.setItems(FXCollections.observableArrayList("BY NAME","BY ID"));
        this.comboBoxSortAccount.setItems(FXCollections.observableArrayList("BY NAME","BY ID","GUEST","REGULAR","VIP", "ALL"));
        this.comboBoxSortItem.setItems(FXCollections.observableArrayList("BY NAME","BY ID","ALL"));

        this.comboBoxSortAccount.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) { // when the value of the combo box is changed
                if (!newValue.equals(null))
                {
                    if (newValue.equals("ALL"))
                    {
                        accountsTable.setItems(getAccounts());
                        comboBoxSortAccount.setValue("Sort By");
                    }
                    else if (newValue.equals("BY NAME"))
                    {
                        accountsTable.setItems(FXCollections.observableArrayList(accountManager.sort(false)));
                        var list = accountManager.sort(false);
                        for (Account i : list)
                            System.out.println(i.toString());
                    }
                    else if (newValue.equals("BY ID"))
                        accountsTable.setItems(FXCollections.observableArrayList(accountManager.sort(true)));
                    else
                        accountsTable.setItems(FXCollections.observableArrayList(accountManager.getAll(newValue)));
                }
            }
        });

        this.comboBoxSortItem.valueProperty().addListener(new ChangeListener<String>() { // when the value of the combo box is changed
            @Override
            public void changed(ObservableValue<? extends String> observableValue,  String oldValue, String newValue) {
                if (!newValue.equals(null))
                {
                    if (newValue.equals("ALL"))
                    {
                        comboBoxSortItem.setSelectionModel(null);
                        itemsTable.setItems(getItems());
                    }
                    else if (newValue.equals("BY NAME"))
                        itemsTable.setItems(FXCollections.observableArrayList(itemManager.sort(false)));
                    else if (newValue.equals("BY ID"))
                        itemsTable.setItems(FXCollections.observableArrayList(itemManager.sort(true)));
                }
            }
        });

        this.itemField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue,  String oldValue, String newValue) { // when the value of the combo box is changed
                if (itemField.getText().length() == 0 && comboBoxSortItem.getSelectionModel().equals(null))
                    itemsTable.setItems(getItems());
            }
        });
        this.accountField.textProperty().addListener(new ChangeListener<String>() { // when the value of the combo box is changed
            @Override
            public void changed(ObservableValue<? extends String> observableValue,  String oldValue, String newValue) {
                if (accountField.getText().length() == 0 && comboBoxSortAccount.getSelectionModel().equals(null)) // if the text field is empty
                    accountsTable.setItems(getAccounts());
            }
        });
        this.pnlTransactions.setVisible(false);
        this.pnlAccount.setVisible(false);
        this.pnlItems.setVisible(true);


        displayTransaction();
        displayAccounts();
        displayItems();

    }

    private void displayAccounts() {

        // Set cell value factories for account table
        accountId.setCellValueFactory(new PropertyValueFactory<>("id"));
        accountAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        accountUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        accountPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        accountPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        accountPoints.setCellValueFactory(new PropertyValueFactory<>("points"));
        accountRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        accountName.setCellValueFactory(new PropertyValueFactory<>("name"));
        accountAction.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        accountAction.setCellFactory(param -> new TableCell<Account, Account>() {
//            ImageView createImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/videostoremanager/add.png"))));
//            ImageView deleteImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/videostoremanager/trash.png"))));
//            ImageView updateImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/videostoremanager/update.png"))));


            private final Button delete = new Button("Delete"); // create a delete button

            private final Button update = new Button("Update"); // create an update button


            @Override
            protected void updateItem(Account item, boolean empty) { // update the table
                delete.setPrefWidth(150);
                update.setPrefWidth(150);

                super.updateItem(item, empty); // call the super class

                // if the item is not empty
                if (item == null) {
                    setGraphic(null);
                    return;
                }
//                createImage.setPreserveRatio(true);
//                updateImage.setPreserveRatio(true);
//                deleteImage.setPreserveRatio(true);
//
//                createImage.setFitHeight(15);
//                updateImage.setFitHeight(15);
//                deleteImage.setFitHeight(15);
//
//
//                create.setGraphic(createImage);
//                delete.setGraphic(updateImage);
//                update.setGraphic(deleteImage);

                VBox vBox = new VBox();
                delete.getStyleClass().add("buttonYellow");
                vBox.getChildren().addAll(delete,update);
                setGraphic(vBox);
                delete.setOnAction(
                        event -> {
                            var itemManager = ApplicationCore.getInstance().getAccountManager(); // get the item manager
                            Account currenAccount = getTableView().getItems().get(getIndex()); // get the current item
                            itemManager.remove(currenAccount); // remove the item
                        }
                );
                update.setOnAction(
                        event -> {
                            var sceneManager =  ApplicationCore.getInstance().getSceneManager(); // get the scene manager
//                            Flag.setAccount(getTableView().getItems().get(getIndex()));
//                            Flag.check =0;
                            //sceneManager.
                            sceneManager.showScene("account-editor");
                        }
                );


            }
        });


        // Display all accounts
        accountsTable.setItems(getAccounts());
        accountsTable.getColumns().addAll(accountId,accountName, accountAddress, accountPhone, accountPoints, accountRole,  accountUsername, accountPassword,accountAction);
    }

    private void displayItems()
    {
        // Set cell value factories for item table
        itemTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        itemID.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemRentalFees.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));
        itemStockStatus.setCellValueFactory(new PropertyValueFactory<>("stock"));
        itemRentalType.setCellValueFactory(new PropertyValueFactory<>("rentalType"));
        itemLoanType.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        itemGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));

        itemAction.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        itemAction.setCellFactory(param -> new TableCell<Item, Item>() {

//            ImageView createImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/videostoremanager/add.png"))));
//            ImageView deleteImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/videostoremanager/trash.png"))));
//            ImageView updateImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/videostoremanager/update.png"))));

            private final Button delete = new Button("Delete");

            private final Button update = new Button("Update");

            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null) {
                    setGraphic(null);
                    return;
                }
//                createImage.setPreserveRatio(true);
//                updateImage.setPreserveRatio(true);
//                deleteImage.setPreserveRatio(true);
//
//                createImage.setFitHeight(15);
//                updateImage.setFitHeight(15);
//                deleteImage.setFitHeight(15);
//
//
//                create.setGraphic(createImage);
//                delete.setGraphic(updateImage);
//                update.setGraphic(deleteImage);

                VBox vBox = new VBox();
                vBox.getChildren().addAll(delete,update);
                setGraphic(vBox);
                delete.setOnAction(
                        event -> {
                            var itemManager = ApplicationCore.getInstance().getItemManager(); // get the item manager
                            Item currentItem = getTableView().getItems().get(getIndex()); // get the current item
                            itemManager.remove(currentItem); // remove the item
                        }
                );

                update.setOnAction(
                        event -> {
                            var sceneManager =  ApplicationCore.getInstance().getSceneManager(); // get the scene manager
                            Flag.setCheck(0);
                            Flag.setItem(getTableView().getItems().get(getIndex())); // set the current item
                            sceneManager.showScene("item-editor");
                        }
                );


            }
        });

        // Display all items
        itemsTable.setItems(getItems());
        itemsTable.getColumns().addAll( itemID,itemTitle,itemGenre,itemRentalType,itemLoanType,itemStockStatus,itemAction);

    }

    private void displayTransaction()
    {
        // Set cell value factories for transaction table
        transAccID.setCellValueFactory(transaction -> new ReadOnlyStringWrapper(transaction.getValue().getAccount().getId()));
        transAccName.setCellValueFactory(transaction -> new ReadOnlyStringWrapper(transaction.getValue().getAccount().getUsername()));
        transItemID.setCellValueFactory(transaction -> new ReadOnlyStringWrapper(transaction.getValue().getItem().getId()));
        transItemName.setCellValueFactory(transaction -> new ReadOnlyStringWrapper(transaction.getValue().getItem().getTitle()));
        transStatus.setCellValueFactory(transaction -> new ReadOnlyStringWrapper(transaction.getValue().isResolved() ? "Returned" : "Borrowing"));

        transactionsTable.setItems(getTransactions());
        transactionsTable.getColumns().addAll(transAccID, transAccName, transItemID, transItemName, transStatus);
    }
    private ObservableList<Account> getAccounts() { // get all accounts
        var appCore = ApplicationCore.getInstance();
        var accountManager = appCore.getAccountManager();
        var accounts = accountManager.getAll();

        return FXCollections.observableArrayList(accounts);
    }

    private ObservableList<Account> getAccounts(String role) { // get all accounts
        var appCore = ApplicationCore.getInstance();
        var accountManager = appCore.getAccountManager();
        var accounts = accountManager.getAll(role);

        return FXCollections.observableArrayList(accounts);
    }

    private ObservableList<Item> getItems() { // get all items
        var appCore = ApplicationCore.getInstance();
        var itemManager = appCore.getItemManager();
        var items = itemManager.getAll();

        return FXCollections.observableArrayList(items);
    }

    private ObservableList<Transaction> getTransactions() { // get all transactions
        var appCore = ApplicationCore.getInstance();
        var transactionManager = appCore.getTransactionManager();
        var transactions = transactionManager.getAll();

        return FXCollections.observableArrayList(transactions);
    }

    @FXML
    // Handle button click event
    private void handleClick(ActionEvent event) {
        if (event.getSource() == btnAccount) { // if the account button is clicked
            this.pnlTransactions.setVisible(false);
            this.pnlAccount.setVisible(true);
            this.pnlItems.setVisible(false);
            lbiStatusMini.setText("/Menu/Account");
            lbiStatus.setText("Account Management");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(201, 198, 193), CornerRadii.EMPTY, Insets.EMPTY))); // set background color
            pnlAccount.toFront();
        } else if (event.getSource() == btnItems) { // if the items button is clicked
            this.pnlTransactions.setVisible(false);
            this.pnlAccount.setVisible(false);
            this.pnlItems.setVisible(true);
            lbiStatusMini.setText("/Menu/Items");
            lbiStatus.setText("Items Menu");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(201, 198, 193), CornerRadii.EMPTY, Insets.EMPTY))); // set background color
            pnlItems.toFront();
        } else if (event.getSource() == btnTransactions) { // if the transactions button is clicked
            this.pnlTransactions.setVisible(true);
            this.pnlAccount.setVisible(false);
            this.pnlItems.setVisible(false);
            lbiStatusMini.setText("/Menu/Transactions");
            lbiStatus.setText("Transactions");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(201, 198, 193), CornerRadii.EMPTY, Insets.EMPTY))); // set background color
            pnlTransactions.toFront();
        } else if (event.getSource() == btnLogOut) { // if the logout button is clicked
            if (AccountController.showLogoutConfirmation()) {
                var app = ApplicationCore.getInstance();
                var auth = app.getAuthManager();

                // Log the user out
                auth.logout();

                // Show the auth screen
                var manager = app.getSceneManager();
                manager.showScene("auth");
            }
        }
    }

    @FXML
    private void createItem()
    {
        var sceneManager = ApplicationCore.getInstance().getSceneManager(); // get the scene manager
        Flag.setCheck(1);
        sceneManager.showScene("item-editor");
    }

    @FXML
    private void createAccount()
    {
        var sceneManager = ApplicationCore.getInstance().getSceneManager(); // get the scene manager
        Flag.check = 1;
        sceneManager.showScene("account-editor");
    }
    @FXML
    private void searchItem()
    {
        String type;
        if(comboBoxItem.getSelectionModel().getSelectedItem() ==null) // if the combobox is empty
            return;
        type = comboBoxItem.getSelectionModel().getSelectedItem();
        itemsTable.setItems(filterList(getItems(),itemField.getText(),type));//,comboBoxItem.getSelectionModel().getSelectedItem()));
    }
    private ObservableList<Item> filterList(List<Item>list, String searchText,String type){//,String type){
        var itemManager = ApplicationCore.getInstance().getItemManager(); // get the item manager
        List<Item> filteredList = new ArrayList<>(); // create a new list
        for (Item item : list){ // loop through the list
            if(searchByType(item, searchText, type)) filteredList.add(item);
        }
        return FXCollections.observableList(itemManager.searchItem(searchText)); // return the filtered list
    }

    private boolean searchByType(Item item, String searchText,String type)
    {

        if (type.equals("By Title"))
            return item.getTitle().equals(searchText)?true:false;
        if (type.equals("By ID"))
            return item.getId().equals(searchText)?true:false;
        return false;
    }

    @FXML
    // Account search bar handler
    private void searchAccount()
    {
        String type;
        if(comboBoxItem.getSelectionModel().getSelectedItem() ==null) // if the combobox is empty
            return;
        type = comboBoxItem.getSelectionModel().getSelectedItem(); // get the selected item
        accountsTable.setItems(filterAccountList(getAccounts(),accountField.getText(),type));
    }
    private ObservableList<Account> filterAccountList(List<Account>list, String searchText,String type){ // filter the list
        var accountManager = ApplicationCore.getInstance().getAccountManager(); // get the account manager
        List<Account> filteredList = new ArrayList<>(); // create a new list
        for (Account account : list){ // loop through the list
            if(searchAccountType(account, searchText, type)) filteredList.add(account); // add the account to the list
        }
        return FXCollections.observableList(accountManager.searchAccount(searchText)); // return the filtered list
    }
    private boolean searchAccountType(Account account, String searchText,String type)
    {
        if(type.equals(null))
            return false;
        if (type.equals("By Name"))
            return account.getName().equals(searchText)?true:false;
        if (type.equals("By ID"))
            return account.getId().equals(searchText)?true:false;
        return true;
    }




}

