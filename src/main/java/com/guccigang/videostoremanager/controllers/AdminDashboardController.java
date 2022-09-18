package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.auth.Account;
import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.errors.AccountException;
import com.guccigang.videostoremanager.errors.ItemException;
import com.guccigang.videostoremanager.items.Item;
import com.guccigang.videostoremanager.transactions.Transaction;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
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

import java.util.Objects;

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
    private ComboBox<String> comboBoxItem = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBoxAccount  = new ComboBox<>();

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
    private TextField transactionSearchBar;

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
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        this.comboBoxItem.setItems(FXCollections.observableArrayList("By Name","By ID"));
        this.comboBoxAccount.setItems(FXCollections.observableArrayList("By Name","By ID"));
        this.pnlTransactions.setVisible(false);
        this.pnlAccount.setVisible(false);
        this.pnlItems.setVisible(true);
        displayTransaction();
        displayAccounts();
        displayItems();

    }


    private void displayAccounts()
    {

        // Set cell value factories for account table
        accountId.setCellValueFactory(new PropertyValueFactory<>("id"));
        accountAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        accountUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        accountPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        accountPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        accountPoints.setCellValueFactory(new PropertyValueFactory<>("points"));
        accountRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        accountAction.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        accountAction.setCellFactory(param -> new TableCell<Account, Account>() {
//            ImageView createImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/videostoremanager/add.png"))));
//            ImageView deleteImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/videostoremanager/trash.png"))));
//            ImageView updateImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guccigang/videostoremanager/update.png"))));


            private final Button delete = new Button("Delete");

            private final Button update = new Button("Update");


            @Override
            protected void updateItem(Account item, boolean empty) {
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
                delete.getStyleClass().add("buttonYellow");
                vBox.getChildren().addAll(delete,update);
                setGraphic(vBox);
                delete.setOnAction(
                        event -> {
                            var itemManager = ApplicationCore.getInstance().getAccountManager();
                            Account currenAccount = getTableView().getItems().get(getIndex());
                            itemManager.remove(currenAccount);
                        }
                );
                update.setOnAction(
                        event -> {
                            var sceneManager =  ApplicationCore.getInstance().getSceneManager();
                            Flag.setAccount(getTableView().getItems().get(getIndex()));
                            Flag.check =0;
                            sceneManager.showScene("account-editor");
                        }
                );


            }
        });


        // Display all accounts
        accountsTable.setItems(getAccounts());
        accountsTable.getColumns().addAll(accountId, accountAddress, accountUsername, accountPassword, accountPhone, accountPoints, accountRole,accountAction);
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
                            var itemManager = ApplicationCore.getInstance().getItemManager();
                            Item currentItem = getTableView().getItems().get(getIndex());
                            itemManager.remove(currentItem);
                        }
                );

                update.setOnAction(
                        event -> {
                            var sceneManager =  ApplicationCore.getInstance().getSceneManager();
                            Flag.setCheck(0);
                            Flag.setItem(getTableView().getItems().get(getIndex()));
                            sceneManager.showScene("item-editor");
                        }
                );


            }
        });

        itemsTable.setItems(getItems());
        itemsTable.getColumns().addAll(itemTitle, itemID,itemGenre,itemRentalType,itemLoanType,itemStockStatus,itemAction);

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





    private ObservableList<Account> getAccounts() {
        var appCore = ApplicationCore.getInstance();
        var accountManager = appCore.getAccountManager();
        var accounts = accountManager.getAll();

        return FXCollections.observableArrayList(accounts);
    }

    private ObservableList<Account> getAccounts(String role) {
        var appCore = ApplicationCore.getInstance();
        var accountManager = appCore.getAccountManager();
        var accounts = accountManager.getAll(role);

        return FXCollections.observableArrayList(accounts);
    }

    private ObservableList<Item> getItems() {
        var appCore = ApplicationCore.getInstance();
        var itemManager = appCore.getItemManager();
        var items = itemManager.getAll();

        return FXCollections.observableArrayList(items);
    }

    private ObservableList<Transaction> getTransactions() {
        var appCore = ApplicationCore.getInstance();
        var transactionManager = appCore.getTransactionManager();
        var transactions = transactionManager.getAll();

        return FXCollections.observableArrayList(transactions);
    }

    @FXML
    private void handleClick(ActionEvent event) {
        if (event.getSource() == btnAccount) {
            this.pnlTransactions.setVisible(false);
            this.pnlAccount.setVisible(true);
            this.pnlItems.setVisible(false);
            lbiStatusMini.setText("/Menu/Account");
            lbiStatus.setText("Account Management");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(201, 198, 193), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlAccount.toFront();
        } else if (event.getSource() == btnItems) {
            this.pnlTransactions.setVisible(false);
            this.pnlAccount.setVisible(false);
            this.pnlItems.setVisible(true);
            lbiStatusMini.setText("/Menu/Items");
            lbiStatus.setText("Items Menu");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(201, 198, 193), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlItems.toFront();
        } else if (event.getSource() == btnTransactions) {
            this.pnlTransactions.setVisible(true);
            this.pnlAccount.setVisible(false);
            this.pnlItems.setVisible(false);
            lbiStatusMini.setText("/Menu/Transactions");
            lbiStatus.setText("Transactions");
            pnlStatus.setBackground(new Background(new BackgroundFill(Color.rgb(201, 198, 193), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlTransactions.toFront();
        } else if (event.getSource() == btnLogOut) {
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
        var sceneManager = ApplicationCore.getInstance().getSceneManager();
        Flag.setCheck(1);
        sceneManager.showScene("item-editor");
    }
    @FXML
    private void searchItem()
    {
    }
    @FXML
    private void createAccount()
    {
        var sceneManager = ApplicationCore.getInstance().getSceneManager();
        Flag.check = 1;
        sceneManager.showScene("account-editor");
    }@FXML
    private void searchAccount()
    {

    }





}

