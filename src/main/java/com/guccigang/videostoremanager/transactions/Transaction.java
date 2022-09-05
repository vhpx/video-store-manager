package com.guccigang.videostoremanager.transactions;

import com.guccigang.videostoremanager.auth.Account;

import com.guccigang.videostoremanager.items.Item;

public class Transaction {
    private final Account account;
    private final Item item;
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
