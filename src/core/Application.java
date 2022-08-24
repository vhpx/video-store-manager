package core;

import auth.AuthManager;
import screens.ScreenManager;

public class Application {
    private static Application instance = null;

    public static AuthManager auth = AuthManager.getInstance();
    public static InternalManager internal = InternalManager.getInstance();
    public static ScreenManager screen = ScreenManager.getInstance();

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
        System.out.println("\nLoading...\n");

        // Initialize the internal manager
        internal.initialize();

        // Initialize the auth manager
        auth.initialize();
    }

    public static void stop() {
        // Stop the internal manager
        internal.stop();

        // Stop the auth manager
        auth.stop();

        // Exit the application
        System.out.println("\nApplication stopped.\n");
        System.exit(0);
    }

    public void execute() {
        // While user is not logged in, show the login screen
        while (!auth.isLoggedIn()) {
            screen.showAuthScreen();
        }

        // If user is logged in, show the main menu
        // internal.showMainMenu();

        screen.showMainMenu();
    }
}