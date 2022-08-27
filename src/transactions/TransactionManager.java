package transactions;

import auth.Account;
import items.Item;

import java.util.ArrayList;

public class TransactionManager {
    private static TransactionManager instance = null;
    private ArrayList<Transaction> transactions;

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

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public Transaction getTransaction (Account account, Item item) throws TransactionException {
        for (Transaction t : transactions) {
            if (t.getAccount().equals(account) && t.getItem().equals(item)) {
                return t;
            }
        }
        throw new TransactionException("Cannot get transaction");
    }
}
