package com.guccigang.videostoremanager.utils;

import com.guccigang.videostoremanager.auth.Account;
import com.guccigang.videostoremanager.auth.AccountManager;
import com.guccigang.videostoremanager.items.Item;
import com.guccigang.videostoremanager.items.ItemManager;
import com.guccigang.videostoremanager.transactions.Transaction;

public class TransactionUtils extends ObjectUtils<Transaction> {
    private final AccountManager accountManager;
    private final ItemManager itemManager;

    public TransactionUtils(AccountManager accountManager, ItemManager itemManager) {
        this.accountManager = accountManager;
        this.itemManager = itemManager;
    }

    public Transaction parse(String str) {
        try {
            String[] tokens = str.split(", ");

            String accountId = tokens[0];
            Account account = accountManager.getAccountById(accountId);

            String itemId = tokens[1];
            Item item = itemManager.getItem(itemId);

            boolean resolved = Boolean.parseBoolean(tokens[2]);

            return new Transaction(account, item, resolved);
        } catch (Exception e) {
            System.out.println("Error parsing transaction: " + str);
            System.out.println("Error: " + e.getMessage());

            return null;
        }
    }

    public String serialize(Transaction transaction) {
        String[] tokens = {
                transaction.getAccount().getId(),
                transaction.getItem().getId(),
                String.valueOf(transaction.isResolved())
        };

        return String.join(", ", tokens);
    }
}
