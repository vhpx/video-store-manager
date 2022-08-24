package core;

import auth.AuthManager;

public class Application {
    private Application() {
    }

    private static Application instance = null;

    public static Application getInstance() {
        if (instance == null)
            instance = new Application();
        return instance;
    }

    public static AuthManager auth = AuthManager.getInstance();
    public static InternalManager internal = InternalManager.getInstance();

    public void initialize() {
        // Initialize the internal manager
        internal.initialize();

        // Initialize the auth manager
        auth.initialize();
    }

    public void start() {
        System.out.println("Preparing to login...\n");

        // While user is not logged in, show the login screen
        while (!auth.isLoggedIn()) {
            auth.showLoginScreen();
        }

        // If user is logged in, show the main menu
        // internal.showMainMenu();

        System.out.println("You are already logged in.");
    }
}