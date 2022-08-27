package auth;

public class VIPAccount extends Account{
    private int point;

    public VIPAccount(int point) {
        this.point = point;
    }

    public VIPAccount(String username, String password, int point) {
        super(username, password);
        this.point = point;
    }

    public VIPAccount(String id, String username, String password, String address, String phone, String name, String role, int point) {
        super(id, username, password, address, phone, name, "VIP");
        this.point = point;
    }
}
