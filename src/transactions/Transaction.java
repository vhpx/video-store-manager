package transactions;

import auth.Account;
import items.Item;

public class Transaction {
    private Account account;
    private Item item;
    private boolean resolved = false;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isResolved() {
        return resolved;
    }

    public Transaction(Account account, Item item) {
        this.account = account;
        this.item = item;
    }

    public void resolve() {
        this.resolved = true;
    }

}
