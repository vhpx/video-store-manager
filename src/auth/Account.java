package auth;

import java.util.ArrayList;

import items.Item;

public class Account {
    private String id;
    private String username;
    private String password;
    private String address;
    private String phone;
    private String name;
    private String role;
    private ArrayList<Item> rentals;

    public Account() {
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

    protected String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
    }

    protected String getUsername() {
        return username;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    protected String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected String getAddress() {
        return address;
    }

    protected void setAddress(String address) {
        this.address = address;
    }

    protected String getPhone() {
        return phone;
    }

    protected void setPhone(String phone) {
        this.phone = phone;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected String getRole() {
        return role;
    }

    protected void setRole(String role) {
        this.role = role;
    }

    protected ArrayList<Item> getRentals() {
        return rentals;
    }

    protected void setRentals(ArrayList<Item> rentals) {
        this.rentals = rentals;
    }

    public void addRental(Item rental) {
        this.rentals.add(rental);
    }

    public void removeRental(Item rental) {
        this.rentals.remove(rental);
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", username=" + username + ", password=" + password + ", address=" + address
                + ", phone=" + phone + ", name=" + name + ", role=" + role + ", rentals=" + rentals + "]";
    }

    public void rentItem(Item item) {

    }

    public void returnItem(Item item) {

    }

    public void verifyPassword(String password) {

    }

    public void changeUsername(String username) {

    }

    public void changePassword(String oldPassword, String newPassword) {

    }

    public void changeAddress(String address) {

    }

    public void changePhone(String phone) {

    }
}
