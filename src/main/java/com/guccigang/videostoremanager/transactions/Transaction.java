package com.guccigang.videostoremanager.transactions;

import com.guccigang.videostoremanager.auth.Account;

import com.guccigang.videostoremanager.core.Entity;
import com.guccigang.videostoremanager.items.Item;

public class Transaction extends Entity {
    private final Account account;
    private final Item item;
    private boolean resolved = false;

    public Transaction(Account account, Item item) {
        super(account.getId() + item.getId());
        this.account = account;
        this.item = item;
        this.resolved = false;
    }

    public Transaction(Account account, Item item, boolean resolved) {
        super(account.getId() + item.getId());
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
