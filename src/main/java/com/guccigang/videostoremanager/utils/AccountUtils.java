package com.guccigang.videostoremanager.utils;

import java.util.Calendar;

import com.guccigang.videostoremanager.auth.Account;

public class AccountUtils extends ObjectUtils<Account> {

    public static boolean isIdValid(String id) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int maxLength = 9;

        if (id.length() != maxLength)
            return false;
        String regexPattern = "C\\d{3}";

        if (!id.matches(regexPattern))
            return false;

        return Integer.parseInt(id.substring(5, 9)) <= currentYear;
    }

    public static boolean isPhoneValid(String phone) {
        String regexPattern = "d{3}-\\d{3}-\\d{4}";
        return phone.matches(regexPattern);
    }

    public static boolean isRoleValid(String role) {
        return switch (role) {
            case "GUEST", "REGULAR", "VIP" -> true;
            default -> false;
        };
    }

    public Account parse(String str) {
        String[] tokens = str.split(", ");

        String id = tokens[0];
        String username = tokens[1];
        String password = tokens[2];
        String name = tokens[3];
        String address = tokens[4];
        String phone = tokens[5];
        String role = tokens[6];

        // int rentedItems = Integer.parseInt(tokens[7]);

        return new Account(id, username, password, address, phone, name, role);
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
                "0" // Integer.toString(account.getRentedItems())
        };

        return String.join(", ", tokens);
    }
}
