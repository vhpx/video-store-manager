package auth;

import items.Item;
import items.ItemException;
import items.ItemManager;
import transactions.Transaction;
import transactions.TransactionException;
import transactions.TransactionManager;
import utils.IOHelper;

import java.util.ArrayList;

public class Account {
    AccountManager accountManager = AccountManager.getInstance();
    TransactionManager transactionManager = TransactionManager.getInstance();
    ItemManager itemManager = ItemManager.getInstance();
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

    public Account(String username, String password) {
        String id = generateId();

        if (id == null) {
            System.out.println("Error: Could not generate id");
            return;
        }

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

    private String generateId() {
        // IDs have the format:
        // C-000 to C-999 for customers

        // Check for unused IDs
        for (int i = 1; i < 1000; i++) {
            // Pad ID with leading zeros
            String id = "C" + String.format("%03d", i);

            if (!accountManager.isIdUsed(id))
                return id;
        }

        // No IDs available.
        return null;
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
            itemManager.decreaseStock(item);
            this.addRental(item);
            Transaction transaction = new Transaction(this, item);
            transactionManager.addTransaction(transaction);
            this.point -= POINT_DEDUCTED;
        } else {
            throw new AccountException("This account cannot rent this item");
        }
    }

    public void returnItem (Item item) throws TransactionException {
        itemManager.increaseStock(item);
        Transaction transaction = transactionManager.getTransaction(this, item);
        transaction.resolve();
        this.removeRental(item);
        this.point += POINT_RECEIVED;
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
        if (isRented(item)) throw new AccountException("This account has rented this item");

        if (this.getRole().equals("GUEST")) {
            if (this.getCurrentRentals().size() <= 2) {
                throw new AccountException("Guest account can rent maximum 2 items");
            }
            if (item.getLoanType().equals("TWO_DAY")) {
                throw new AccountException("Guest account cannot borrow a 2-day item");
            }
        }
        return true;
    }

    public void changeUsername(String newUsername) {
        this.username = newUsername;
    }

    public void changePassword(String oldPassword, String newPassword) throws AccountException {
        if (oldPassword.equals(this.password)) {
            this.password = newPassword;
        } else {
            throw new AccountException("Incorrect password.");
        }
    }

    public void changeAddress(String newAddress) {
        this.setAddress(newAddress);
    }

    public void changePhone(String newPhone) {
        this.setPhone(phone);
    }

}

