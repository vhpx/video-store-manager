package auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AccountManager {
    private static AccountManager instance = null;
    private ArrayList<Account> accounts = new ArrayList<Account>();

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
        Account a1 = new Account("1", "jdoe1", "password", "123 Main St.", "123-456-7890", "Fane Doe", "ADMIN");
        Account a3 = new Account("2", "jdoe2", "password", "123 Main St.", "123-456-7890", "Jane Doe", "USER");
        Account a4 = new Account("3", "jdoe3", "password", "123 Main St.", "123-456-7890", "Jane Doe", "USER");
        Account a2 = new Account("4", "jsmith", "password", "123 Main St.", "123-456-7890", "John Smith", "ADMIN");

        addAccount(a1);
        addAccount(a2);
        addAccount(a3);
        addAccount(a4);
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

    public static void main(String[] args) {
        AccountManager accountManager = AccountManager.getInstance();

        Account a1 = new Account("1", "jdoe1", "password", "123 Main St.", "123-456-7890", "Fane Doe", "ADMIN");
        Account a3 = new Account("2", "jdoe2", "password", "123 Main St.", "123-456-7890", "Jane Doe", "USER");
        Account a4 = new Account("3", "jdoe3", "password", "123 Main St.", "123-456-7890", "Jane Doe", "USER");
        Account a2 = new Account("4", "jsmith", "password", "123 Main St.", "123-456-7890", "John Smith", "ADMIN");

        accountManager.addAccount(a1);
        accountManager.addAccount(a2);
        accountManager.addAccount(a3);
        accountManager.addAccount(a4);

        accountManager.accounts.get(1).setRole("REGULAR");
        accountManager.accounts.get(2).setRole("VIP");
        accountManager.accounts.get(3).setRole("VIP");
        accountManager.accounts.get(4).setRole("REGULAR");

        System.out.println("Before sorting:");
        accountManager.displayAccounts(accountManager.getAccounts());

        System.out.println("\n\nSorting by id:");
        accountManager.displayAccountsSortedById();

        System.out.println("\n\nSorting by name:");
        accountManager.displayAccountsSortedByName();

        System.out.println("\n\nAfter sorting:");
        accountManager.displayAccounts(accountManager.getAccounts());

        System.out.println("\n\nDisplay GUEST accounts:");
        accountManager.displayAccountsByRole("GUEST");

        System.out.println("\n\nDisplay REGULAR accounts:");
        accountManager.displayAccountsByRole("REGULAR");

        System.out.println("\n\nDisplay VIP accounts:");
        accountManager.displayAccountsByRole("VIP");

    }

}
