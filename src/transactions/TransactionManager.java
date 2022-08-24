package transactions;

public class TransactionManager {
    private static TransactionManager instance = null;

    private TransactionManager() {
        // Private constructor to prevent instantiation since
        // this is a singleton class (only one instance)
    }

    public static TransactionManager getInstance() {
        if (instance == null)
            instance = new TransactionManager();
        return instance;
    }

    public void initialize() {
        // Load the transactions from the local storage
    }
}
