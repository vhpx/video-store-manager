package core;

import auth.AccountManager;
import items.ItemManager;
import transactions.TransactionManager;

public class InternalManager {
    private static InternalManager instance = null;

    public AccountManager accounts = AccountManager.getInstance();
    public ItemManager items = ItemManager.getInstance();
    public TransactionManager transactions = TransactionManager.getInstance();

    private InternalManager() {
        // Private constructor to prevent instantiation since
        // this is a singleton class (only one instance)
    }

    public static InternalManager getInstance() {
        if (instance == null)
            instance = new InternalManager();
        return instance;
    }

    public void initialize() {
        // Initialize all internal managers
        accounts.initialize();
        items.initialize();
        transactions.initialize();
    }

    public void stop() {
        // Stop all internal managers
        accounts.stop();
        items.stop();
        transactions.stop();
    }
}
