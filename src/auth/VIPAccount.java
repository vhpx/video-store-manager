package auth;

public class VIPAccount extends Account{
    private int rewardedPoint;

    public VIPAccount(int rewardedPoint) {
        this.rewardedPoint = rewardedPoint;
    }

    public VIPAccount(String username, String password, int rewardedPoint) {
        super(username, password);
        this.rewardedPoint = rewardedPoint;
    }

    public VIPAccount(String id, String username, String password, String address, String phone, String name, String role, int rewardedPoint) {
        super(id, username, password, address, phone, name, "VIP");
        this.rewardedPoint = rewardedPoint;
    }
}
