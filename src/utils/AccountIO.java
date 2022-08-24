package utils;

import java.util.ArrayList;

import auth.Account;
import auth.AccountManager;

public class AccountIO {
    private static AccountManager manager = AccountManager.getInstance();

    public static void loadData(String fileName) {
        ArrayList<String> lines = IOHelper.readFile(fileName);

        for (String line : lines) {
            Account account = AccountUtils.parse(line);
            manager.addAccount(account);
        }
    }

    public static void saveData(String fileName) {
        ArrayList<Account> accounts = manager.getAccounts();
        ArrayList<String> lines = new ArrayList<String>();

        for (Account account : accounts) {
            lines.add(AccountUtils.serialize(account));
        }

        IOHelper.createFile(fileName, lines);
    }
}
