package com.guccigang.videostoremanager.core;

import com.guccigang.videostoremanager.scenes.ScenePackage;

public class Constants {
    private static final String APP_NAME = "Video Store Manager";

    public static String getAppName() {
        return APP_NAME;
    }

    private static final String DATA_PATH = "/data/";

    private static final String ITEMS_FILE = "items.txt";
    private static final String ACCOUNTS_FILE = "accounts.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";

    public static String getItemsFileName() {
        return DATA_PATH + ITEMS_FILE;
    }

    public static String getAccountsFileName() {
        return DATA_PATH + ACCOUNTS_FILE;
    }

    public static String getTransactionsFileName() {
        return DATA_PATH + TRANSACTIONS_FILE;
    }

    private static final ScenePackage[] scenesToLoad = {
            // Authentication
            new ScenePackage("auth"),
            //new ScenePackage("signup"), // TODO: Merge with auth

            // Customer View
            new ScenePackage("user-dashboard"),
            new ScenePackage("account-info"),

            // Admin View
            new ScenePackage("admin-dashboard"),
    };

    public static ScenePackage[] getScenesToLoad() {
        return scenesToLoad;
    }

    public static String getDefaultScene() {
        return "auth";
    }
}
