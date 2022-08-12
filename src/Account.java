import java.util.ArrayList;

public class Account {
    private String id;
    private String address;
    private String name;
    private ArrayList<Item> rentals;
    private String phone;
    private String password;
    private String username;
    private String level;

    public Account() {}

    public Account(String id, String address, String name, String phone, String password, String username) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.username = username;
        this.level = "GUEST";
        this.rentals = new ArrayList<Item>();
    }

    protected String getLevel() {
        return level;
    }

    protected void setLevel(String level) {
        this.level = level;
    }

    protected String getId() {
        return id;
    }

    protected String getAddress() {
        return address;
    }

    protected String getName() {
        return name;
    }

    protected ArrayList<Item> getRentals() {
        return rentals;
    }

    protected String getPhone() {
        return phone;
    }

    protected String getUsername() {
        return username;
    }

    protected void setId(String id) {
        this.id = id;
    }

    protected void setAddress(String address) {
        this.address = address;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setRentals(ArrayList<Item> rentals) {
        this.rentals = rentals;
    }

    protected void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString () {
        return "Account [id=" + id + ", address=" + address + ", name=" + name + ", rentals=" + rentals + ", phone=" + phone + ", password=" + password + ", username=" + username + ", level=" + level + "]";
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
