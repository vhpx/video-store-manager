package auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import utils.AccountUtils;
import utils.IOHelper;

public class AccountManager {
    private static AccountManager instance = null;
    private ArrayList<Account> accounts = new ArrayList<Account>();

    private String dataFileName = "data/accounts.txt";

    private AccountManager() {
    }

    public static AccountManager getInstance() {
        if (instance == null)
            instance = new AccountManager();
        return instance;
    }

    protected ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void initialize() {
        // Load the accounts from the local storage
        loadData();
    }

    public void stop() {
        // Save the accounts to the local storage
        saveData();
    }

    public void addAccount(Account account) {
        System.out.println("Adding account " + account.getUsername());
        accounts.add(account);
    }

    public void deleteAccount(Account account) {
        System.out.println("Deleting account " + account.getUsername());
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

    private void loadData() {
        ArrayList<String> lines = IOHelper.readFile(dataFileName);

        for (String line : lines) {
            Account account = AccountUtils.parse(line);
            addAccount(account);
        }
    }

    private void saveData() {
        ArrayList<String> lines = new ArrayList<String>();

        for (Account account : accounts) {
            lines.add(AccountUtils.serialize(account));
        }

        IOHelper.createFile(dataFileName, lines);
    }
}
