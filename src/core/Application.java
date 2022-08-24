package core;

import auth.AuthManager;

public class Application {
    private static Application instance = null;

    public static AuthManager auth = AuthManager.getInstance();
    public static InternalManager internal = InternalManager.getInstance();

    private Application() {
        // Private constructor to prevent instantiation since
        // this is a singleton class (only one instance)
    }

    public static Application getInstance() {
        if (instance == null)
            instance = new Application();
        return instance;
    }

    public void initialize() {
        // Initialize the internal manager
        internal.initialize();

        // Initialize the auth manager
        auth.initialize();
    }

    public void stop() {
        // Stop the internal manager
        internal.stop();

        // Stop the auth manager
        auth.stop();
    }

    public void execute() {
        System.out.println("Preparing to login...\n");

        // While user is not logged in, show the login screen
        while (!auth.isLoggedIn()) {
            auth.showLoginScreen();
        }

        // If user is logged in, show the main menu
        // internal.showMainMenu();

        System.out.println("You are already logged in.");
        stop();
    }
}