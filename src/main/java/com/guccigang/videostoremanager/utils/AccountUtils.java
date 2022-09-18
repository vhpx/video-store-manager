package com.guccigang.videostoremanager.utils;

import java.util.Calendar;

import com.guccigang.videostoremanager.auth.Account;

public class AccountUtils extends ObjectUtils<Account> {
    public static boolean isValidId(String id) {
        // The id is "C001" to "C999"
        if (id.length() != 4)
            return false;

        if (id.charAt(0) != 'C')
            return false;

        try {
            int num = Integer.parseInt(id.substring(1));
            return num >= 1 && num <= 999;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Account parse(String str) {
        // Split the string into an array of strings
        String[] tokens = str.split(", ");

        // Get the raw data
        String id = tokens[0];
        String username = tokens[1];
        String password = tokens[2];
        String name = tokens[3];
        String address = tokens[4];
        String phone = tokens[5];
        String role = tokens[6];

        return new Account(id, username, password, address, phone, name, role, 0);
    }

    public String serialize(Account account) {
        String[] tokens = {
                account.getId() != null ? account.getId() : "UNKNOWN",
                account.getUsername() != null ? account.getUsername() : "UNKNOWN",
                account.getPassword() != null ? account.getPassword() : "UNKNOWN",
                account.getName() != null ? account.getName() : "UNKNOWN",
                account.getAddress() != null ? account.getAddress() : "UNKNOWN",
                account.getPhone() != null ? account.getPhone() : "UNKNOWN",
                account.getRole() != null ? account.getRole() : "UNKNOWN",
                Integer.toString(account.getPoints())
        };

        return String.join(", ", tokens);
    }
}
