package com.guccigang.videostoremanager.auth;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import com.guccigang.videostoremanager.errors.AccountException;
import com.guccigang.videostoremanager.utils.AccountIO;
import com.guccigang.videostoremanager.utils.AccountUtils;

import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.core.Constants;
import com.guccigang.videostoremanager.core.Manager;

public class AccountManager extends Manager<Account> {
    public void initialize() {
        // Initialize account I/O helper
        var helper = new AccountIO(this, new AccountUtils());
        var fileName = Constants.getAccountsFileName();

        // Set the manager's I/O helper and file name to operate on
        setIO(helper, fileName);

        // Load the accounts from the local storage
        load();
    }

    public void stop() {
        // Save the accounts to the local storage
        save();
    }

    public Account getAccountById(String id) {
        for (Account account : getAll())
            if (account.getId().equals(id))
                return account;

        return null;
    }

    public Account getAccountByUsername(String username) {
        for (Account account : getAll())
            if (account.getUsername().equals(username))
                return account;

        return null;
    }

    public boolean authenticate(String username, String password) {
        Account account = getAccountByUsername(username);
        if (account == null)
            return false;
        else
            return account.authenticate(password);
    }

    private String generateId() {
        // IDs have the format:
        // C-000 to C-999 for customers

        // Check for unused IDs
        for (int i = 1; i < 1000; i++) {
            // Pad ID with leading zeros
            String id = "C" + String.format("%03d", i);

            if (!isIdUsed(id))
                return id;
        }

        // No IDs available.
        return null;
    }

    public Account createAccount(String username, String password) throws AccountException {
        // Check if username is empty
        if (username.isEmpty())
            throw new AccountException("Username cannot be empty");

        // Check if password is empty
        if (password.isEmpty())
            throw new AccountException("Password cannot be empty");

        // Check if the username is admin
        if (username.equals("admin"))
            throw new AccountException("The username 'admin' is reserved.");

        // Check if account already exists
        if (getAccountByUsername(username) != null)
            throw new AccountException("Account already exists.");
        else {
            // Generate ID
            String id = generateId();

            if (id == null)
                throw new AccountException("No IDs available.");

            // Create account
            var account = new Account(id, username, password);
            add(account);

            // Announce account creation
            System.out.println("Account created: " + account);

            return account;
        }
    }

    // Get all accounts with a given role
    public ArrayList<Account> getAll(String role) {
        var accounts = new ArrayList<Account>();

        for (Account account : getAll())
            if (account.getRole().equals(role))
                accounts.add(account);

        return accounts;
    }

    boolean isIdUsed(String id) {
        for (Account account : getAll())
            if (account.getId().equals(id))
                return true;
        return false;
    }

    public void levelUp(Account account) {
        if (Objects.equals(account.getRole(), "VIP"))
            return;

        var app = ApplicationCore.getInstance();
        var transactionManager = app.getTransactionManager();
        var resolvedAmount = transactionManager.countTransactions(account, true);

        if (Objects.equals(account.getRole(), "REGULAR") && resolvedAmount >= 5) {
            account.setRole("VIP");
            return;
        }

        if (Objects.equals(account.getRole(), "GUEST") && resolvedAmount >= 3) {
            account.setRole("REGULAR");
        }
    }

    public ArrayList<Account> searchAccount(String input) {
        var result = new ArrayList<Account>();

        for (Account a : getAll())
            if (input.equals(a.getName()) || input.equals(a.getId()))
                result.add(a);

        return result;
    }
}
