package screens;

import auth.AuthManager;
import core.Application;
import utils.IOHelper;

public class ScreenManager {
    private static ScreenManager instance = null;

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

        var sc = IOHelper.getScanner();
        var choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.print("\nEnter your username: ");
                var username = sc.next();

                System.out.print("\nEnter your password: ");
                var password = sc.next();

                boolean success = AuthManager.signup(username, password);

                if (success) {
                    System.out.println("Signup successful!");
                } else {
                    System.out.println("Signup failed!");
                }

                break;

            case 2:
                System.out.print("\nEnter your username: ");
                username = sc.next();

                System.out.print("\nEnter your password: ");
                password = sc.next();

                success = AuthManager.login(username, password);

                if (success) {
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Login failed!");
                }

                break;

            case 3:
                System.out.println("\nSaving...\n");
                Application.stop();
                break;

            default:
                System.out.println("Invalid choice.");
                showAuthScreen();
                break;
        }
    }

    public void showMainMenu() {
        System.out.println("\nMain menu\n");
        System.out.println("Work in progress...");

        // Stop the application
        System.out.println("\nSaving...\n");
        Application.stop();
    }
}
