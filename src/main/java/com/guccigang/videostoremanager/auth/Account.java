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

    private ArrayList<Item> rentedItems = new ArrayList<Item>();

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

    protected void setRentedItems(ArrayList<Item> rentedItems) {
        this.rentedItems = rentedItems;
    }

    public void addRental(Item rental) {
        this.rentedItems.add(rental);
    }

    public void removeRental(Item rental) {
        this.rentedItems.remove(rental);
    }

    public void rent(Item item) throws ItemException, AccountException {
        if (canRent(item)) {
            var app = ApplicationCore.getInstance();
            var transactionManager = app.getTransactionManager();
            var itemManager = app.getItemManager();

            Transaction transaction = new Transaction(this, item);

            transactionManager.add(transaction);
            itemManager.decreaseStock(item);

            this.points -= Constants.getPointDeducted();
            addRental(item);
        } else {
            throw new AccountException("This account cannot rent this item");
        }
    }

    public void returnItem(Item item) throws TransactionException {
        var app = ApplicationCore.getInstance();
        var transactionManager = app.getTransactionManager();
        var itemManager = app.getItemManager();

        Transaction transaction = transactionManager.getTransaction(this, item);

        transaction.resolve();
        itemManager.increaseStock(item);

        this.points += Constants.getPointReceived();
        removeRental(item);
    }

    public boolean isRented(Item item) {
        // Check if this item was already rented by this account
        for (Item i : rentedItems) {
            if (i.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public boolean canRent(Item item) throws AccountException {
        if (isRented(item))
            throw new AccountException("This account has rented this item");

        if (this.getRole().equals("GUEST")) {
            if (this.getRentedItems().size() <= 2) {
                throw new AccountException("Guest account can rent maximum 2 items");
            }
            if (item.getLoanType().equals("TWO_DAY")) {
                throw new AccountException("Guest account cannot borrow a 2-day item");
            }
        }

        if (this.points < Constants.getPointDeducted()) {
            throw new AccountException("This account has not enough points");
        }

        return true;
    }

    public void updatePassword(String oldPassword, String newPassword) throws AccountException {
        if (oldPassword.equals(this.password)) {
            this.password = newPassword;
        } else {
            throw new AccountException("Incorrect password.");
        }
    }
}
