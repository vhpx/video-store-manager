public class AccountManager {
    private AccountManager() {}
    private static AccountManager instance = null;

    public static AccountManager getInstance() {
        if (instance == null) instance = new AccountManager();
        return instance;
    }
}
