public class InternalManager {
    private InternalManager() {}
    private static InternalManager instance = null;

    public static InternalManager getInstance() {
        if (instance == null) instance = new InternalManager();
        return instance;
    }

    public static AccountManager accounts = AccountManager.getInstance();
    public static ItemManager items = ItemManager.getInstance();
    public static TransactionManager transactions = TransactionManager.getInstance();
}
