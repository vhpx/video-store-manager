package transactions;

public class TransactionManager {
    private TransactionManager() {
    }

    private static TransactionManager instance = null;

    public static TransactionManager getInstance() {
        if (instance == null)
            instance = new TransactionManager();
        return instance;
    }

    public void initialize() {
        // Load the transactions from the local storage
    }
}
