import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AccountManager {

    private static AccountManager instance = null;
    private ArrayList<Account> accounts;

    private AccountManager() {
    }

    public static AccountManager getInstance() {
        if (instance == null) instance = new AccountManager();
        return instance;
    }


    protected ArrayList<Account> getAccounts() {
        return accounts;
    }

    // display all accounts
    public void displayAccounts(ArrayList<Account> accounts) {
        for (Account account : accounts) {
            System.out.println(account);
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

    // display a group of account according to level
    public void displayAccountsByLevel(String level) {
        for (Account account : this.getAccounts()) {
            if (account.getLevel().equals(level)) {
                System.out.println(account);
            }
        }
    }


    public static void main(String[] args) {
        AccountManager accountManager = AccountManager.getInstance();
        accountManager.accounts = new ArrayList<Account>();
        accountManager.accounts.add(new Account("6", "123 Main St.", "Fane Doe", "123-456-7890", "password", "jdoe"));
        accountManager.accounts.add(new Account("2", "123 Main St.", "Cohn Smith", "123-456-7890", "password", "jdoe"));
        accountManager.accounts.add(new Account("1", "123 Main St.", "Aohn Doe", "123-456-7890", "password", "jdoe"));
        accountManager.accounts.add(new Account("3", "123 Main St.", "Eohn Doe", "123-456-7890", "password", "jdoe"));
        accountManager.accounts.add(new Account("5", "123 Main St.", "Dane Smith", "123-456-7890", "password", "jdoe"));
        accountManager.accounts.add(new Account("4", "123 Main St.", "Bane Doe", "123-456-7890", "password", "jdoe"));

        accountManager.accounts.get(1).setLevel("REGULAR");
        accountManager.accounts.get(2).setLevel("VIP");
        accountManager.accounts.get(3).setLevel("VIP");
        accountManager.accounts.get(5).setLevel("REGULAR");

        System.out.println("Before sorting:");
        accountManager.displayAccounts(accountManager.getAccounts());

        System.out.println("\n\nSorting by id:");
        accountManager.displayAccountsSortedById();

        System.out.println("\n\nSorting by name:");
        accountManager.displayAccountsSortedByName();

        System.out.println("\n\nAfter sorting:");
        accountManager.displayAccounts(accountManager.getAccounts());

        System.out.println("\n\nDisplay GUEST accounts:");
        accountManager.displayAccountsByLevel("GUEST");

        System.out.println("\n\nDisplay REGULAR accounts:");
        accountManager.displayAccountsByLevel("REGULAR");

        System.out.println("\n\nDisplay VIP accounts:");
        accountManager.displayAccountsByLevel("VIP");

    }

}
