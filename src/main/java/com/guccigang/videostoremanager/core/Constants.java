package com.guccigang.videostoremanager.core;

import com.guccigang.videostoremanager.scenes.ScenePackage;

public class Constants {
    private static final String APP_NAME = "Video Store Manager";

    public static String getAppName() {
        return APP_NAME;
    }

    public static String ROLE_GUEST = "GUEST";
    public static String ROLE_REGULAR = "REGULAR";
    public static String ROLE_VIP = "VIP";

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

    private static final int POINT_PER_TRANSACTION = 10;
    private static final int POINT_FOR_FREE_RENT = 100;

    public static int getPointPerTransaction() {
        return POINT_PER_TRANSACTION;
    }

    public static int getPointForFreeRent() {
        return POINT_FOR_FREE_RENT;
    }

    private static final ScenePackage[] availableScenes = {
            // Authentication
            new ScenePackage("auth"),
            // Customer View
            new ScenePackage("user-dashboard"),
            new ScenePackage("account-info"),
            new ScenePackage("item-editor"),
            new ScenePackage("account-editor"),

            // Admin View
            new ScenePackage("admin-dashboard"),
    };

    public static ScenePackage[] getAvailableScenes() {
        return availableScenes;
    }

    public static ScenePackage getDefaultScene() {
        // Return the first scene in the list
        return availableScenes[0];
    }
}
