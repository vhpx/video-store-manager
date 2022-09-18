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
        System.out.println("• Linked item " + rental.getId() + " to account " + this.getId());
    }

    public void removeRental(Item rental) {
        this.rentedItems.remove(rental);
    }

    public void rent(Item item) throws ItemException, AccountException {
        System.out.println("inside rent");
        if (canRent(item)) {
            System.out.println("can rent");
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

//        if (this.points < Constants.getPointDeducted()) {
//            throw new AccountException("This account has not enough points");
//        }

        return true;
    }

    public void updatePassword(String newPassword) throws AccountException {
        if (newPassword.equals(this.password))
            throw new AccountException("Your new password is being used. Please enter the new one!");
        else if (newPassword.length() == 0)
            throw new AccountException("Please enter in anything.");
        this.password = newPassword;

    }
    public void updatePhone(String newPhoneNum) throws AccountException {
        if (newPhoneNum.equals(this.phone))
            throw new AccountException("Your new phone number is being used. Please enter the new one!");
        else if (newPhoneNum.length() == 0)
            throw new AccountException("Please enter in anything.");
        else if (newPhoneNum.matches("\\d{3}-\\d{3}-\\d{4}"))
            throw new AccountException("The phone number format should be XXX-XXX-XXXX!");
        this.phone = newPhoneNum;
    }

    public void updateAddress(String newAddress) throws AccountException {
        if (newAddress.equals(this.address))
            throw new AccountException("Your current address is being used. Please enter the new one!");
        else if (newAddress.length() == 0)
            throw new AccountException("Please enter in anything.");

        this.address = newAddress;
    }

    public void updateName(String newName) throws AccountException {
        if (newName.equals(this.name))
            throw new AccountException("Please enter the name that is different than the one you using!");
        else if (name.length() == 0)
            throw new AccountException("You name cannot be empty.");

        this.name = newName;
    }


    public void displayRental()
    {
        for (Item i: rentedItems)
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

