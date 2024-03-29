package com.guccigang.videostoremanager.auth;

import java.util.*;

import com.guccigang.videostoremanager.errors.AccountException;
import com.guccigang.videostoremanager.utils.AccountIO;
import com.guccigang.videostoremanager.utils.AccountUtils;

import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.core.Constants;
import com.guccigang.videostoremanager.core.Manager;
import javafx.collections.FXCollections;

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
        // Loop through the account list to find the account with the same id and then return
        for (Account account : getAll())
            if (account.getId().equals(id))
                return account;

        return null;
    }

    public Account getAccountByUsername(String username) {
        // Loop through the account list to find the account with the same name and then return
        for (Account account : getAll())
            if (account.getUsername().equals(username))
                return account;

        return null;
    }

    public boolean authenticate(String username, String password) {
        // Check if the username exist, if exist then check if the password is correct
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

    public ArrayList<Account> sort(boolean type) {
        // Sort account by ID or by Name
        // type True is sort by ID, type False is sort by Name
        var result = this.getAll();
        if (type)
            Collections.sort(result,new AccountIDComparator());
        else
            Collections.sort(result,new AccountNameComparator());
        return result;
    }

    public ArrayList<Account> searchAccount(String input) {
        // Loop through the account list to find the account with the same name or id and then return
        var result = new ArrayList<Account>();

        for (Account a : getAll())
            if (input.equals(a.getName()) || input.equals(a.getId()))
                result.add(a);

        return result;
    }
}
class AccountIDComparator implements Comparator <Account>{
    @Override
    public int compare(Account o1, Account o2) {
        if (o2.getId().compareTo(o2.getId()) == 1)
            return 1;
        return -1;
    }
}
class AccountNameComparator implements Comparator <Account>{
    @Override
    public int compare(Account o1, Account o2) {
        if (o2.getName().compareTo(o2.getName()) == 1)
            return 1;
        if (o2.getName().compareTo(o2.getName()) == -1)
            return -1;
        return 0;
    }
}
