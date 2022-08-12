public class InternalManager {
    private InternalManager() {
    }

    private static InternalManager instance = null;

    public static InternalManager getInstance() {
        if (instance == null)
            instance = new InternalManager();
        return instance;
    }

    public AccountManager accounts = AccountManager.getInstance();
    public ItemManager items = ItemManager.getInstance();
    public TransactionManager transactions = TransactionManager.getInstance();
}
