package transactions;

import auth.Account;
import items.Item;
import utils.TransactionIO;

import java.util.ArrayList;

public class TransactionManager {
    private static TransactionManager instance = null;
    private final String fileName = "data/transactions.txt";

    private ArrayList<Transaction> transactions = new ArrayList<>();

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
        TransactionIO transactionIO = new TransactionIO(this);
        transactionIO.loadData(fileName);
    }

    public void stop() {
        // Save the transactions to the local storage
        TransactionIO transactionIO = new TransactionIO(this);
        transactionIO.saveData(fileName);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public Transaction getTransaction(Account account, Item item) throws TransactionException {
        for (Transaction t : transactions) {
            if (t.getAccount().equals(account) && t.getItem().equals(item)) {
                return t;
            }
        }
        throw new TransactionException("Cannot get transaction");
    }

    public ArrayList<Transaction> getTransactions(Account account) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Transaction t : this.transactions) {
            if (t.getAccount().equals(account)) {
                transactions.add(t);
            }
        }
        return transactions;
    }

    public ArrayList<Transaction> getTransactions(Account account, boolean resolved) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Transaction t : this.transactions) {
            if (t.getAccount().equals(account) && t.isResolved() == resolved) {
                transactions.add(t);
            }
        }
        return transactions;
    }
}
