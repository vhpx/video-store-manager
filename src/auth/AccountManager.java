package auth;

import transactions.Transaction;
import transactions.TransactionManager;
import utils.AccountIO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class AccountManager {
    private static AccountManager instance = null;
    private final String fileName = "data/accounts.txt";

    private final ArrayList<Account> accounts = new ArrayList<>();
    private final TransactionManager transactionManager = TransactionManager.getInstance();

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
        AccountIO accountIO = new AccountIO(this);
        accountIO.loadData(fileName);
    }

    public void stop() {
        // Save the accounts to the local storage
        AccountIO accountIO = new AccountIO(this);
        accountIO.saveData(fileName);
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

    // this method only display information needed when search account
    public void displayAccountsInfo(ArrayList<Account> accounts) {
        for (Account account : accounts) {
            System.out.println(account.getName()
                    + ", " + account.getId()
                    + ", " + account.getPhone()
                    + ", " + account.getAddress());
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

    boolean isIdUsed(String id) {
        for (Account account : this.getAccounts()) {
            if (account.getId().equals(id))
                return true;
        }
        return false;
    }

    public void levelUp(Account account) {
        if (Objects.equals(account.getRole(), "VIP"))
            return;

        ArrayList<Transaction> resolvedTransactions = transactionManager.getTransactions(account, true);

        if (Objects.equals(account.getRole(), "REGULAR") && resolvedTransactions.size() >= 5) {
            account.setRole("VIP");
            return;
        }

        if (Objects.equals(account.getRole(), "GUEST") && resolvedTransactions.size() >= 3) {
            account.setRole("REGULAR");
        }
    }

    public ArrayList<Account> searchAccount(String input) throws AccountException {
        ArrayList<Account> list = new ArrayList<Account>();
        for (Account a : this.accounts) {
            if (input.equals(a.getName()) || input.equals(a.getId())) {
                list.add(a);
            }
        }
        if (list.size() == 0) {
            throw new AccountException("No result matched");
        }
        return list;
    }
}
