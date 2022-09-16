package com.guccigang.videostoremanager.screens;

import java.util.Scanner;

import com.guccigang.videostoremanager.auth.Account;
import com.guccigang.videostoremanager.auth.AccountManager;
import com.guccigang.videostoremanager.core.ApplicationCore;
import com.guccigang.videostoremanager.errors.AccountException;
import com.guccigang.videostoremanager.errors.ItemException;
import com.guccigang.videostoremanager.items.Item;
import com.guccigang.videostoremanager.utils.IOHelper;
import com.guccigang.videostoremanager.auth.AuthManager;
import com.guccigang.videostoremanager.errors.TransactionException;
import com.guccigang.videostoremanager.items.ItemManager;

public class ScreenManager {
    private ItemManager items = null;
    private AccountManager accounts = null;
    private AuthManager auth = null;

    public ScreenManager(ItemManager items, AccountManager accounts, AuthManager auth) {
        this.items = items;
        this.accounts = accounts;
        this.auth = auth;
    }

    public void showAuthScreen() {
        System.out.println("\nAuth screen\n");

        System.out.println("1. Signup");
        System.out.println("2. Login");
        System.out.println("3. Exit");

        System.out.print("\nEnter your choice: ");

        Scanner sc = IOHelper.getScanner();
        int choice = sc.nextInt();

        switch (choice) {
            case 1 -> {
                System.out.print("\nEnter your username: ");
                String username = sc.next();

                System.out.print("\nEnter your password: ");
                String password = sc.next();

                var app = ApplicationCore.getInstance();
                var auth = app.getAuthManager();
                boolean success = auth.signup(username, password);

                if (success) {
                    System.out.println("Signup successful!");
                } else {
                    System.out.println("Signup failed!");
                }
            }

            case 2 -> {
                System.out.print("\nEnter your username: ");
                String username = sc.next();

                System.out.print("\nEnter your password: ");
                String password = sc.next();

                var app = ApplicationCore.getInstance();
                var auth = app.getAuthManager();
                boolean success = auth.login(username, password);

                if (success) {
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Login failed!");
                }
            }

            case 3 -> {
                System.out.println("\nSaving...\n");

                var app = ApplicationCore.getInstance();
                app.stop();
            }

            default -> {
                System.out.println("Invalid choice.");
                showAuthScreen();
            }
        }
    }

    public void showMainMenu() {
        System.out.println("\nMain menu\n");
        System.out.println("Work in progress...");

        // Stop the application
        System.out.println("\nSaving...\n");

        var app = ApplicationCore.getInstance();
        app.stop();
    }

    public void showAccountScreen() throws AccountException, ItemException, TransactionException {
        if (auth == null) {
            throw new AccountException("AuthManager is not initialized.");
        }

        if (accounts == null) {
            throw new AccountException("AccountManager is not initialized.");
        }

        if (items == null) {
            throw new AccountException("ItemManager is not initialized.");
        }

        System.out.println("\nAccount screen\n");

        System.out.println("1. Browse all items");
        System.out.println("2. List current rentals");
        System.out.println("3. Rent items");
        System.out.println("4. Return items");
        System.out.println("5. Update your account");
        System.out.println("6. Delete your account");
        System.out.println("7. Search account by id or name");
        System.out.println("8. Exit");

        System.out.print("\nEnter your choice: ");

        Scanner sc = IOHelper.getScanner();
        int choice = sc.nextInt();

        Account account = auth.getCurrentAccount();

        switch (choice) {
            case 1 -> {
                for (Item i : items.getAll()) {
                    System.out.println(i);
                }
            }

            case 2 -> account.showRentals();

            case 3 -> {
                // Rent item
                System.out.println("Please choose item to rent");
                items.displayAll();
                System.out.print("Enter item's id:");
                String id = sc.nextLine();
                Item item = items.getItem(id);
                account.rent(item);
                System.out.println("Successfully rent " + item.getTitle());
            }

            case 4 -> {
                // Return item
                System.out.println("Please choose item to return");
                account.showRentals();
                System.out.print("Enter item's id:");
                String id = sc.nextLine();
                Item item = items.getItem(account, id);
                account.returnItem(item);
                System.out.println("Successfully return " + item.getTitle());
            }

            case 5 -> {
                // Update account
                System.out.println("Please choose which information you want to update");
                showUpdateAccountScreen();
            }

            case 6 -> {
                // Delete account
                System.out.print("Enter YES to confirm that you want to delete your account: ");
                String yourChoice = sc.nextLine();
                if (yourChoice.equals("YES")) {
                    accounts.remove(account);
                    System.out.println("Successfully delete your account");
                    // I'm not sure what will happen next
                } else {
                    System.out.println("Your account will not be deleted");
                    showAccountScreen();
                }
            }

            case 7 -> {
                // Search account by id / name
                System.out.print("Enter id or name of account to want to search: ");
                String input = sc.nextLine();

                var result = accounts.searchAccount(input);
                accounts.displayAccountsInfo(result);
            }

            case 8 -> {
                System.out.println("\nSaving...\n");

                var app = ApplicationCore.getInstance();
                app.stop();
            }

            default -> {
                System.out.println("Invalid choice.");
                showAccountScreen();
            }

        }

    }

    public void showUpdateAccountScreen() throws AccountException, ItemException, TransactionException {
        if (auth == null) {
            throw new AccountException("AuthManager is not initialized.");
        }

        System.out.println("\nUpdate account screen\n");

        System.out.println("1. Update username");
        System.out.println("2. Update password");
        System.out.println("3. Update phone number");
        System.out.println("4. Update address");
        System.out.println("5. Back to account screen");
        System.out.println("6. Exit");

        System.out.print("\nEnter your choice: ");

        Scanner sc = IOHelper.getScanner();
        int choice = sc.nextInt();

        Account account = auth.getCurrentAccount();

        switch (choice) {
            case 1 -> {
                System.out.print("\nEnter your new username: ");
                String username = sc.next();
                account.updateUsername(username);
            }

            case 2 -> {
                System.out.print("\nEnter your old password: ");
                String oldPassword = sc.next();
                System.out.print("\nEnter your new password: ");
                String newPassword = sc.next();
                account.updatePassword(oldPassword, newPassword);
            }

            case 3 -> {
                System.out.print("\nEnter your new phone number: ");
                String phone = sc.next();
                account.updatePhone(phone);
            }

            case 4 -> {
                System.out.print("\nEnter your new address: ");
                String address = sc.next();
                account.updateAddress(address);
            }

            case 5 -> // back to account screen
                    showAccountScreen();

            case 6 -> {
                System.out.println("\nSaving...\n");

                var app = ApplicationCore.getInstance();
                app.stop();
            }

            default -> {
                System.out.println("Invalid choice.");
                showUpdateAccountScreen();
            }
        }
    }
}
