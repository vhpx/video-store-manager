package auth;

import java.util.ArrayList;

public class AuthManager {
    private static AuthManager instance = null;

    private static final AccountManager accountManager = AccountManager.getInstance();

    private static final String adminUsername = "admin";
    private static final String adminPassword = "admin";

    private static Account currentAccount = null;
    private static boolean isAdmin = false;

    private AuthManager() {
        // Private constructor to prevent instantiation since
        // this is a singleton class (only one instance)
    }

    public static AuthManager getInstance() {
        if (instance == null)
            instance = new AuthManager();
        return instance;
    }

    // Initialize the auth manager
    public void initialize() {
        // Reset authentication flags
        currentAccount = null;
        isAdmin = false;

        // Load the logged-in user id from the database
        // loadLoggedInUser();
    }

    public void stop() {
        // Save the logged-in user id to the database
        // saveLoggedInUser();
    }

    public boolean isLoggedIn() {
        return currentAccount != null || isAdmin;
    }

    public String getLoggedInUserId() {
        return currentAccount.getId();
    }
    public Account getCurrentAccount() {return currentAccount;}

    public boolean isAdmin() {
        return isAdmin;
    }

    public static boolean login(String username, String password) {
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

        // Get accounts from the account manager
        ArrayList<Account> accounts = accountManager.getAccounts();

        // Check if username is registered
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                // Check if password is correct
                if (account.getPassword().equals(password)) {
                    // Set current account
                    currentAccount = account;

                    return true;
                }

                // Invalid password
                return false;
            }
        }

        // Invalid username
        return false;
    }

    public static boolean signup(String username, String password) {
        System.out.println("Signing up as " + username + "...");

        // Create account
        Account account = new Account(username, password);
        accountManager.addAccount(account);

        System.out.println("Account created");
        accountManager.displayAll();

        currentAccount = account;
        isAdmin = false;

        return true;
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
        if (accountManager.getAccount(newUsername) != null) {
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