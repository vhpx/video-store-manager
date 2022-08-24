package auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import utils.AccountIO;

public class AccountManager {
    private static AccountManager instance = null;
    private String fileName = "data/accounts.txt";

    private ArrayList<Account> accounts = new ArrayList<Account>();

    private AccountManager() {
        // Private constructor to prevent instantiation since
        // this is a singleton class (only one instance)
    }

    public static AccountManager getInstance() {
        if (instance == null)
            instance = new AccountManager();
        return instance;
    }

    public void initialize() {
        // Load the accounts from the local storage
        AccountIO.loadData(fileName);
    }

    public void stop() {
        // Save the accounts to the local storage
        AccountIO.saveData(fileName);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public Account getAccount(String username) {
        for (Account account : accounts)
            if (account.getUsername().equals(username))
                return account;

        return null;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void deleteAccount(Account account) {
        accounts.remove(account);
    }

    public void displayAll() {
        for (Account account : accounts) {
            System.out.println(account.toString());
        }
    }

    public void displayAccounts(ArrayList<Account> accounts) {
        for (Account account : accounts) {
            System.out.println(account.toString());
        }
    }

    // display all accounts sorted by id
    public void displayAccountsSortedById() {
        ArrayList<Account> sortedAccounts = this.getAccounts();
        Collections.sort(sortedAccounts, new Comparator<Account>() {
            public int compare(Account o1, Account o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        displayAccounts(sortedAccounts);
    }

    // display all accounts sorted by name
    public void displayAccountsSortedByName() {
        ArrayList<Account> sortedAccounts = this.getAccounts();
        Collections.sort(sortedAccounts, new Comparator<Account>() {
            public int compare(Account o1, Account o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        displayAccounts(sortedAccounts);
    }

    // display a group of account according to role
    public void displayAccountsByRole(String role) {
        for (Account account : this.getAccounts()) {
            if (account.getRole().equals(role)) {
                System.out.println(account);
            }
        }
    }
}
