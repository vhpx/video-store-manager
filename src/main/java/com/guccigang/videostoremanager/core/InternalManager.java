package com.guccigang.videostoremanager.core;

import com.guccigang.videostoremanager.auth.AccountManager;
import com.guccigang.videostoremanager.items.ItemManager;
import com.guccigang.videostoremanager.transactions.TransactionManager;

public class InternalManager {
    // Setup initial flags
    private boolean initialized = false;
    private boolean stopped = false;

    // instantiate internal managers with empty constructors
    public AccountManager accounts = new AccountManager();
    public ItemManager items = new ItemManager();
    public TransactionManager transactions = new TransactionManager();

    /**
     * Initialize the internal managers with local data.
     * This method is called when the program starts and only be called once.
     */
    public void start() {
        // Do not initialize if already initialized
        if (initialized)
            return;

        // Initialize all managers
        accounts.start();
        items.start();
        transactions.start(accounts, items);

        // Set the initialized flag to true
        initialized = true;
    }

    /**
     * Stop the internal managers and save the data to the local storage.
     * This method is called when the program stops and only be called once.
     */
    public void stop() {
        // Do not stop if already stopped
        if (stopped)
            return;

        // Stop all managers
        accounts.stop();
        items.stop();
        transactions.stop();

        // Set the stopped flag to true
        stopped = true;
    }
}
