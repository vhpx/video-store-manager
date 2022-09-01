package transactions;

import auth.Account;
import items.Item;

public class Transaction {
    private Account account;
    private Item item;
    private boolean resolved = false;

    public Transaction(Account account, Item item) {
        this.account = account;
        this.item = item;
        this.resolved = false;
    }

    public Transaction(Account account, Item item, boolean resolved) {
        this.account = account;
        this.item = item;
        this.resolved = resolved;
    }

    public Account getAccount() {
        return account;
    }

    public Item getItem() {
        return item;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void resolve() {
        this.resolved = true;
    }
}
