package com.guccigang.videostoremanager.core;

import com.guccigang.videostoremanager.VSMApplication;
import com.guccigang.videostoremanager.auth.AccountManager;
import com.guccigang.videostoremanager.auth.AuthManager;
import com.guccigang.videostoremanager.items.ItemManager;
import com.guccigang.videostoremanager.scenes.SceneManager;
import com.guccigang.videostoremanager.transactions.TransactionManager;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationCore {
    private static ApplicationCore instance = null;

    private ApplicationCore() {
        // The constructor has been made protected
        // to child classes to prevent object instantiation
        // since only one instance of the class is allowed.
    }

    public static ApplicationCore getInstance() {
        // The instance is created only if it does not exist
        if (instance == null) instance = new ApplicationCore();

        // Return the existing instance
        return instance;
    }

    // instantiate application managers
    private final InternalManager internal = new InternalManager();
    private final AuthManager auth = new AuthManager();
    private final SceneManager scenes = new SceneManager();

    /**
     * Initialize the application managers with local data.
     */
    public void initialize() throws IOException {
        // Initialize all managers
        internal.initialize();
        auth.initialize();
        scenes.initialize();
    }

    public void link(VSMApplication app, Stage stage) {
        // Link the application and the stage
        // to the scene manager
        scenes.link(app, stage);
    }

    /**
     * Stop the application managers and save the data to the local storage.
     */
    public void stop() {
        // Stop all managers
        internal.stop();
        auth.stop();
        // Exit the application
    }
    public void exit() {
        // Stop all managers
        // Exit the application
        exitApplication();
    }

    public void execute() {
        // While user is not logged in, show the login screen
        if (!auth.isLoggedIn()) {
            scenes.showScene(Constants.getDefaultScene());
        } else if (auth.isAdmin()) {
            // If user is admin, show the admin screen
            scenes.showScene("admin-dashboard");
        } else {
            // If user is not admin, show the user screen
            scenes.showScene("user-dashboard");
        }
    }

    private void exitApplication() {
        System.out.println("\nApplication stopped.\n");
        scenes.closeWindow();
    }

    public AuthManager getAuthManager() {
        return auth;
    }

    public SceneManager getSceneManager() {
        return scenes;
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