package com.guccigang.videostoremanager.auth;

import java.util.ArrayList;

import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.core.Constants;
import com.guccigang.videostoremanager.core.Entity;
import com.guccigang.videostoremanager.errors.AccountException;
import com.guccigang.videostoremanager.items.Item;
import com.guccigang.videostoremanager.transactions.Transaction;

import com.guccigang.videostoremanager.errors.ItemException;
import com.guccigang.videostoremanager.errors.TransactionException;
import com.guccigang.videostoremanager.utils.AccountUtils;

public class Account extends Entity {
    private String username;
    private String password;
    private String address;
    private String phone;
    private String name;
    private String role;
    private int points = 0;

    private final ArrayList<Item> rentedItems = new ArrayList<Item>();

    public Account(String id, String username, String password) {
        super(id);
        this.username = username;
        this.password = password;
        this.role = "GUEST";
    }

    public Account(String id, String username, String password, String address, String phone, String name,
                   String role, int points) {
        this(id, username, password);
        this.address = address;
        this.phone = phone;
        this.name = name;
        this.role = role;
        this.points = points;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public void setId(String id) throws Exception {
        if (!AccountUtils.isValidId(id))
            throw new AccountException("Invalid item id: " + id);

        super.setId(id);
    }

    public String getUsername() {
        return username;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    protected void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    protected void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    protected void setRole(String role) {
        this.role = role;
    }

    public int getPoints() {
        return points;
    }

    protected void setPoints(int points) {
        this.points = points;
    }

    public ArrayList<Item> getRentedItems() {
        return rentedItems;
    }

    public void addRental(Item rental) {
        this.rentedItems.add(rental);
        System.out.println("• Linked item " + rental.getId() + " to account " + this.getId());
    }

    public void removeRental(Item rental) {
        this.rentedItems.remove(rental);
    }

    public void rentItem(Item item) throws ItemException, AccountException {
        if (canRent(item)) {
            var app = ApplicationCore.getInstance();
            var transactionManager = app.getTransactionManager();
            var itemManager = app.getItemManager();

            // Create a new transaction
            Transaction transaction = new Transaction(this, item);

            // Add the transaction to the transaction manager
            transactionManager.add(transaction);

            // If the account is a VIP and has at least 100 points, remove 100 points
            // and announce that this rental is free
            if (this.role.equals(Constants.ROLE_VIP) && this.points >= 100) {
                this.points -= 100;
                System.out.println("• Account " + this.getId() + " has " + this.points + " points left.");
                System.out.println("• Rental is free for account " + this.getId() + " this time.");
            } else {
                // Otherwise, display the price of the rental
                System.out.println("• Account " + this.getId() + " has to pay " + item.getRentalFee() + " for this rental.");
            }

            // Decrease the stock of the item
            itemManager.decreaseStock(item);

            // Add the item to the rented items list
            addRental(item);
        } else {
            throw new AccountException("Account " + this.getId() + " cannot rent item " + item.getId());
        }
    }

    public void returnItem(Item item) throws TransactionException {
        var app = ApplicationCore.getInstance();
        var transactionManager = app.getTransactionManager();
        var itemManager = app.getItemManager();

        // Get the current transaction
        Transaction transaction = transactionManager.getTransaction(this, item);

        // Resolve the transaction and increase the stock
        transaction.resolve();
        itemManager.increaseStock(item);

        // Remove the item from the rented items list
        removeRental(item);

        // Update the points
        updatePoints();
    }

    private void updatePoints() {
        // If the current account is not a VIP, check if it can be upgraded
        if (!this.role.equals(Constants.ROLE_VIP)) {
            var app = ApplicationCore.getInstance();
            var transactionManager = app.getTransactionManager();

            // Get the number of completed transactions of this account
            var completed = transactionManager.countTransactions(this, true);

            // If the current account is a REGULAR user and has completed 5 transactions, upgrade it to VIP
            if (this.role.equals(Constants.ROLE_REGULAR) && completed >= 5) {
                this.role = Constants.ROLE_VIP;
                System.out.println("• Account " + this.getId() + " has been upgraded to VIP");
            }

            // If the current account is a guest and has completed 3 transaction, upgrade it to REGULAR
            if (this.role.equals(Constants.ROLE_GUEST) && completed >= 3) {
                this.role = Constants.ROLE_REGULAR;
                System.out.println("• Account " + this.getId() + " has been upgraded to REGULAR");
            }

            return;
        }

        // Add points to the current account since it is a VIP
        points += Constants.getPointPerTransaction();
    }

    private boolean isAlreadyRented(Item item) {
        // Check if this item was already rented by this account
        for (Item i : rentedItems)
            if (i.getId().equals(item.getId()))
                return true;

        return false;
    }

    private boolean canRent(Item item) throws AccountException, ItemException {
        // If the current item is already rented by this account
        if (isAlreadyRented(item))
            throw new AccountException("This item is already rented by this account");

        // If the current item is not in stock, return false
        if (!item.isInStock())
            throw new ItemException("This item is not in stock");

        // If the current account is either a REGULAR or a VIP, return true
        if (this.getRole().equals("REGULAR") || this.getRole().equals("VIP"))
            return true;

        // Otherwise, the account is a GUEST, which can only rent 2 items at a time
        if (this.getRentedItems().size() >= 2)
            throw new AccountException("This account cannot rent more than 2 items at a time");

        // The GUEST account cannot rent an item with loan type "TWO_DAYS"
        if (!item.getLoanType().equals("TWO_DAYS"))
            throw new AccountException("This account cannot rent this item for 2 days");

        return true;
    }

    public void updatePassword(String newPassword) throws AccountException {
        if (newPassword.equals(this.password))
            throw new AccountException("Your new password is being used. Please enter the new one!");

        if (newPassword.length() == 0)
            throw new AccountException("Please enter in anything.");

        this.password = newPassword;
    }

    public void updatePhone(String newPhoneNum) throws AccountException {
        if (newPhoneNum.equals(this.phone))
            throw new AccountException("Your new phone number is being used. Please enter the new one!");

        if (newPhoneNum.length() == 0)
            throw new AccountException("Please enter in anything.");

        if (newPhoneNum.matches("\\d{3}-\\d{3}-\\d{4}"))
            throw new AccountException("The phone number format should be XXX-XXX-XXXX!");

        this.phone = newPhoneNum;
    }

    public void updateAddress(String newAddress) throws AccountException {
        if (newAddress.equals(this.address))
            throw new AccountException("Your current address is being used. Please enter the new one!");

        if (newAddress.length() == 0)
            throw new AccountException("Please enter in anything.");

        this.address = newAddress;
    }

    public void updateName(String newName) throws AccountException {
        if (newName.equals(this.name))
            throw new AccountException("Please enter the name that is different than the one you using!");

        if (name.length() == 0)
            throw new AccountException("You name cannot be empty.");

        this.name = newName;
    }

    public void displayRental() {
        for (Item i : rentedItems)
            System.out.println(i.toString());
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", points=" + points +
                ", rentedItems=" + rentedItems +
                '}';
    }
}
