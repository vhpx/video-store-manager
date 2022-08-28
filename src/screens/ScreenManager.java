package screens;

import auth.Account;
import auth.AccountException;
import auth.AccountManager;
import auth.AuthManager;
import core.Application;
import items.Item;
import items.ItemException;
import items.ItemManager;
import transactions.TransactionException;
import utils.IOHelper;

import java.util.Scanner;

public class ScreenManager {
    private static ScreenManager instance = null;
    private final ItemManager itemManager = ItemManager.getInstance();
    private final AccountManager accountManager = AccountManager.getInstance();
    private final AuthManager authManager = AuthManager.getInstance();

    private ScreenManager() {
        // Private constructor to prevent instantiation since
        // this is a singleton class (only one instance)
    }

    public static ScreenManager getInstance() {
        if (instance == null)
            instance = new ScreenManager();
        return instance;
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

                boolean success = AuthManager.signup(username, password);

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

                boolean success = AuthManager.login(username, password);

                if (success) {
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Login failed!");
                }
            }

            case 3 -> {
                System.out.println("\nSaving...\n");
                Application.stop();
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
        Application.stop();
    }

    public void showAccountScreen() throws AccountException, ItemException, TransactionException {
        System.out.println("\nAccount screen\n");

        System.out.println("1. Browse all items");
        System.out.println("2. List current rentals");
        System.out.println("3. Rent items");
        System.out.println("4. Return items");
        System.out.println("5. Update your account");
        System.out.println("6. Delete your account");
        System.out.println("7. Exit");

        System.out.print("\nEnter your choice: ");

        Scanner sc = IOHelper.getScanner();
        int choice = sc.nextInt();

        Account account = authManager.getCurrentAccount();

        switch (choice) {
            case 1 -> {
                for (Item i : itemManager.getItems()) {
                    System.out.println(i);
                }
            }
            case 2 -> {
                account.showRentals();
            }
            case 3 -> {
                // Rent item
                System.out.println("Please choose item to rent");
                itemManager.displayAll();
                System.out.print("Enter item's id:");
                String id = sc.nextLine();
                Item item = itemManager.getItem(id);
                account.rent(item);
                System.out.println("Successfully rent " + item.getTitle());
            }
            case 4 -> {
                // Return item
                System.out.println("Please choose item to return");
                account.showRentals();
                System.out.print("Enter item's id:");
                String id = sc.nextLine();
                Item item = itemManager.getItem(account, id);
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
                System.out.println("Enter YES to confirm that you want to delete your account");
                String yourChoice = sc.nextLine();
                if (yourChoice.equals("YES")) {
                    accountManager.deleteAccount(account);
                    System.out.println("Successfully delete your account");
                    // I'm not sure what will happen next
                } else {
                    System.out.println("Your account will not be deleted");
                    showAccountScreen();
                }
            }
            case 7 -> {
                System.out.println("\nSaving...\n");
                Application.stop();
            }

            default -> {
                System.out.println("Invalid choice.");
                showAccountScreen();
            }

        }

    }

    public void showUpdateAccountScreen() throws AccountException, ItemException, TransactionException {
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

        Account account = authManager.getCurrentAccount();

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
            case 5 -> {
                // back to account screen
                showAccountScreen();
            }
            case 6 -> {
                System.out.println("\nSaving...\n");
                Application.stop();
            }
            default -> {
                System.out.println("Invalid choice.");
                showUpdateAccountScreen();
            }
        }
    }
}
