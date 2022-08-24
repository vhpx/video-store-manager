package utils;

import auth.Account;

public class AccountUtils {
    public static Account parse(String str) {
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

    public static String serialize(Account account) {
        String[] tokens = {
                account.getId(),
                account.getUsername(),
                account.getPassword(),
                account.getName(),
                account.getAddress(),
                account.getPhone(),
                account.getRole()
        };

        return String.join(", ", tokens);
    }
}
