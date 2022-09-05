package com.guccigang.videostoremanager.auth;

import java.util.ArrayList;

import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.errors.AccountException;
import com.guccigang.videostoremanager.items.Item;
import com.guccigang.videostoremanager.transactions.Transaction;

import com.guccigang.videostoremanager.errors.ItemException;
import com.guccigang.videostoremanager.errors.TransactionException;

public class Account {
    private String id;
    private String username;
    private String password;
    private String address;
    private String phone;
    private String name;
    private String role;
    private int point = 20;

    private final int POINT_RECEIVED = 50;
    private final int POINT_DEDUCTED = 10;

    private ArrayList<Item> currentRentals;

    public Account() {
    }

    public Account(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = "GUEST";
    }

    public Account(String id, String username, String password, String address, String phone, String name,
            String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.name = name;
        this.role = role;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
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

    public ArrayList<Item> getCurrentRentals() {
        return currentRentals;
    }

    protected void setCurrentRentals(ArrayList<Item> currentRentals) {
        this.currentRentals = currentRentals;
    }

    protected int getPoint() {
        return point;
    }

    protected void setPoint(int point) {
        this.point = point;
    }

    public void addRental(Item rental) {
        this.currentRentals.add(rental);
    }

    public void removeRental(Item rental) {
        this.currentRentals.remove(rental);
    }

    @Override
    public String toString() {
        String quantity = (currentRentals != null) ? String.valueOf(currentRentals.size()) : "0";
        return id + ", " + name + ", " + address + ", " + phone + ", " + quantity + ", " + role + ", " + username + ", "
                + password;
    }

    public void rent(Item item) throws ItemException, AccountException {
        if (canRent(item)) {
            var app = ApplicationCore.getInstance();
            var transactionManager = app.getTransactionManager();
            var itemManager = app.getItemManager();

            Transaction transaction = new Transaction(this, item);

            transactionManager.add(transaction);
            itemManager.decreaseStock(item);

            this.point -= POINT_DEDUCTED;
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

        this.point += POINT_RECEIVED;
        removeRental(item);
    }

    public void showRentals() {
        for (Item i : currentRentals) {
            System.out.println(i.toString());
        }
    }

    public boolean isRented(Item item) {
        // check if this item was already rented by this account
        for (Item i : currentRentals) {
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
            if (this.getCurrentRentals().size() <= 2) {
                throw new AccountException("Guest account can rent maximum 2 items");
            }
            if (item.getLoanType().equals("TWO_DAY")) {
                throw new AccountException("Guest account cannot borrow a 2-day item");
            }
        }

        if (this.point < POINT_DEDUCTED) {
            throw new AccountException("This account has not enough points");
        }

        return true;
    }

    public void updateUsername(String newUsername) {
        this.username = newUsername;
    }

    public void updatePassword(String oldPassword, String newPassword) throws AccountException {
        if (oldPassword.equals(this.password)) {
            this.password = newPassword;
        } else {
            throw new AccountException("Incorrect password.");
        }
    }

    public void updateAddress(String newAddress) {
        this.setAddress(newAddress);
    }

    public void updatePhone(String newPhone) {
        this.setPhone(phone);
    }
}
