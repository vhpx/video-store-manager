package auth;

import utils.AccountIO;

import java.util.ArrayList;
import java.util.Comparator;

public class AccountManager {
    private static AccountManager instance = null;
    private final String fileName = "data/accounts.txt";

    private final ArrayList<Account> accounts = new ArrayList<>();

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
        ArrayList<Account> accounts = new ArrayList<>(this.getAccounts());
        accounts.sort(Comparator.comparing(Account::getId));
        displayAccounts(accounts);
    }

    // display all accounts sorted by name
    public void displayAccountsSortedByName() {
        ArrayList<Account> accounts = new ArrayList<>(this.getAccounts());
        accounts.sort(Comparator.comparing(Account::getName));
        displayAccounts(accounts);
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
