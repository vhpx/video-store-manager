package transactions;

import auth.Account;
import auth.AccountManager;
import core.Constant;
import core.Manager;
import errors.TransactionException;
import items.Item;
import items.ItemManager;
import utils.TransactionIO;
import utils.TransactionUtils;

import java.util.ArrayList;

public class TransactionManager extends Manager<Transaction> {
    public void start(AccountManager accounts, ItemManager items) {
        // Initalize transaction I/O helper
        var utils = new TransactionUtils(accounts, items);
        var transactionIO = new TransactionIO(this, utils);
        var fileName = Constant.getTransactionsFileName();

        // Set the manager's I/O helper and file name to operate on
        setIO(transactionIO, fileName);

        // Load the transactions from the local storage
        load();
    }

    public void stop() {
        // Save the transactions to the local storage
        save();
    }

    public Transaction getTransaction(Account account, Item item) throws TransactionException {
        for (Transaction t : getAll())
            if (t.getAccount().equals(account) && t.getItem().equals(item))
                return t;

        throw new TransactionException("Cannot get transaction");
    }

    public ArrayList<Transaction> getTransactions(Account account) {
        var transactions = new ArrayList<Transaction>();
        for (Transaction t : getAll())
            if (t.getAccount().equals(account))
                transactions.add(t);

        return transactions;
    }

    public ArrayList<Transaction> getTransactions(Account account, boolean resolved) {
        var transactions = new ArrayList<Transaction>();
        for (Transaction t : getAll())
            if (t.getAccount().equals(account) && t.isResolved() == resolved)
                transactions.add(t);

        return transactions;
    }

    public int countTransactions(Account account) {
        return getTransactions(account).size();
    }

    public int countTransactions(Account account, boolean resolved) {
        return getTransactions(account, resolved).size();
    }
}
