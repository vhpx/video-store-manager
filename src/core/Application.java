package core;

import auth.AccountManager;
import auth.AuthManager;
import items.ItemManager;
import screens.ScreenManager;
import transactions.TransactionManager;

public class Application {
    private static Application instance = null;

    private Application() {
        // The constructor has been made protected
        // to child classes to prevent object instantiation
        // since only one instance of the class is allowed.
    }

    public static Application getInstance() {
        // The instance is created only if it does not exist
        if (instance == null)
            instance = new Application();

        // Return the existing instance
        return instance;
    }

    // instantiate application managers with empty constructors
    private InternalManager internal = new InternalManager();
    private AuthManager auth = new AuthManager();
    private ScreenManager screen = null;

    /**
     * Initialize the application managers with local data.
     */
    public void start() {
        // Link managers
        var itemManager = internal.items;
        var accountManager = internal.accounts;

        screen = new ScreenManager(itemManager, accountManager, auth);

        // Initialize all managers
        internal.start();
        auth.start();
    }

    /**
     * Stop the application managers and save the data to the local storage.
     */
    public void stop() {
        // Stop all managers
        internal.stop();
        auth.stop();

        // Exit the application
        exit();
    }

    public void execute() {
        // While user is not logged in, show the login screen
        while (!auth.isLoggedIn()) {
            screen.showAuthScreen();
        }

        // Once user is logged in, show the main screen
        screen.showMainMenu();
    }

    private void exit() {
        System.out.println("\nApplication stopped.\n");
        System.exit(0);
    }

    public InternalManager getInternal() {
        return internal;
    }

    public AuthManager getAuth() {
        return auth;
    }

    public ScreenManager getScreen() {
        return screen;
    }

    public AccountManager getAccountManager() {
        return internal.accounts;
    }

    public ItemManager getItemManager() {
        return internal.items;
    }

    public TransactionManager getTransactionManager() {
        return internal.transactions;
    }
}