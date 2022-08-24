package auth;

import java.util.ArrayList;

import utils.IOHelper;

public class AuthManager {
    private static AuthManager instance = null;

    private AccountManager accountManager = AccountManager.getInstance();

    private String adminUsername = "admin";
    private String adminPassword = "admin";

    private String loggedInUserId = null;
    private boolean isAdmin = false;

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
        loggedInUserId = null;
        isAdmin = false;

        // Load the logged in user id from the database
        // loadLoggedInUser();
    }

    public void stop() {
        // Save the logged in user id to the database
        // saveLoggedInUser();
    }

    public boolean isLoggedIn() {
        return loggedInUserId != null;
    }

    public String getLoggedInUserId() {
        return loggedInUserId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void showLoginScreen() {
        if (signup()) {
            System.out.println("Login successful");
        } else {
            System.out.println("Login failed");
        }
    }

    public boolean login(String username, String password) {
        System.out.println("Logging in as " + username);

        // Check if username is admin
        if (username.equals(adminUsername)) {
            if (password.equals(adminPassword)) {
                // Set admin flag
                isAdmin = true;

                // Set logged in user id
                loggedInUserId = username;
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
                    // Set logged in user id
                    loggedInUserId = username;
                    return true;
                }

                // Invalid password
                return false;
            }
        }

        // Invalid username
        return false;
    }

    public boolean signup() {
        var sc = IOHelper.getScanner();

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        // Confirm password
        System.out.print("Confirm password: ");
        String confirmPassword = sc.nextLine();

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match");
            return false;
        }

        // Create account
        Account account = new Account(username, password);
        accountManager.addAccount(account);

        System.out.println("Account created");
        accountManager.displayAll();

        loggedInUserId = account.getId();
        isAdmin = false;

        return true;
    }

    public void logout() {
        System.out.println("Logging out");

        loggedInUserId = null;
        isAdmin = false;
    }

    public void changePassword(String oldPassword, String newPassword) {
        System.out.println("Changing password");
    }

    public void resetPassword(String username) {
        System.out.println("Resetting password");
    }

    public void changeUsername(String oldUsername, String newUsername) {
        System.out.println("Changing username");
    }
}