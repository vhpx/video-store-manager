package com.guccigang.videostoremanager.utils;

public class Utilities {
    public static String askInfo(String message) {
        System.out.print(message);

        var sc = IOHelper.getScanner();
        return sc.nextLine();
    }
}
