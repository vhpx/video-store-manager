package com.guccigang.videostoremanager.transactions;

import java.util.ArrayList;

import com.guccigang.videostoremanager.auth.Account;
import com.guccigang.videostoremanager.core.Constants;
import com.guccigang.videostoremanager.items.Item;
import com.guccigang.videostoremanager.utils.TransactionIO;
import com.guccigang.videostoremanager.utils.TransactionUtils;
import com.guccigang.videostoremanager.auth.AccountManager;
import com.guccigang.videostoremanager.core.Manager;
import com.guccigang.videostoremanager.errors.TransactionException;
import com.guccigang.videostoremanager.items.ItemManager;

public class TransactionManager extends Manager<Transaction> {
    public void initialize(AccountManager accounts, ItemManager items) {
        // Initialize transaction I/O helper
        var utils = new TransactionUtils(accounts, items);
        var transactionIO = new TransactionIO(this, utils);
        var fileName = Constants.getTransactionsFileName();

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

        throw new TransactionException("Transaction not found.");
    }

    public Transaction getTransaction(Account account, Item item, boolean resolved) throws TransactionException {
        for (Transaction t : getAll())
            if (t.getAccount().equals(account) && t.getItem().equals(item) && t.isResolved() == resolved)
                return t;

        throw new TransactionException("Transaction not found.");
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
