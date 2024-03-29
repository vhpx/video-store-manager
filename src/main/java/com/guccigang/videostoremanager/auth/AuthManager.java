package com.guccigang.videostoremanager.auth;

import com.guccigang.videostoremanager.core.ApplicationCore;

public class AuthManager {
    private static final String adminUsername = "admin";
    private static final String adminPassword = "admin";

    private static Account currentAccount = null;
    private static boolean isAdmin = false;

    // Initialize the auth manager
    public void initialize() {
        // Reset authentication flags
        currentAccount = null;
        isAdmin = false;
    }

    public boolean isLoggedIn() {
        return currentAccount != null || isAdmin;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean login(String username, String password) {
        try {
            System.out.println("\nLogging in as " + username + "...");

            // Check if username is admin
            if (username.equals(adminUsername)) {
                if (password.equals(adminPassword)) {
                    // Set admin flag
                    isAdmin = true;
                    return true;
                }

                // Invalid admin password
                return false;
            }

            var app = ApplicationCore.getInstance();
            var accountManager = app.getAccountManager();
            var authenticated = accountManager.authenticate(username, password);

            if (authenticated) {
                // Set the current account
                currentAccount = accountManager.getAccountByUsername(username);
                return true;
            }

            // Invalid username or password
            return false;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean signup(String username, String password) {
        try {
            System.out.println("Signing up as " + username + "...");

            // Check if username is admin
            if (username.equals(adminUsername)) {
                // Invalid username
                return false;
            }

            var app = ApplicationCore.getInstance();
            var accountManager = app.getAccountManager();
            var account = accountManager.getAccountByUsername(username);

            if (account != null)
                // Username already exists
                return false;

            // Create new account
            account = accountManager.createAccount(username, password);
            accountManager.add(account);

            // Set the current account
            currentAccount = account;

            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public void logout() {
        System.out.println("Logging out");
        currentAccount = null;
        isAdmin = false;
    }

    public void changeUsername(String password, String newUsername) {
        // Check if user is admin
        if (isAdmin) {
            System.out.println("Administrator username cannot be changed.");
            return;
        }

        // Check if username is taken
        var app = ApplicationCore.getInstance();
        var accountManager = app.getAccountManager();

        if (accountManager.getAccountByUsername(newUsername) != null) {
            System.out.println("Username is already taken");
            return;
        }

        // Check if password is correct
        if (!currentAccount.getPassword().equals(password)) {
            System.out.println("Invalid password");
            return;
        }

        // Change username
        currentAccount.setUsername(newUsername);
        System.out.println("Username changed");
    }

    public void changePassword(String oldPassword, String newPassword) {
        // Check if user is admin
        if (isAdmin) {
            System.out.println("Administrator password cannot be changed.");
            return;
        }

        // Check if password is correct
        if (!currentAccount.getPassword().equals(oldPassword)) {
            System.out.println("Invalid password");
            return;
        }

        // Change password
        currentAccount.setPassword(newPassword);
        System.out.println("Password changed");
    }
}