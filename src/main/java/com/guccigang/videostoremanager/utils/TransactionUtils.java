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
            String itemId = tokens[1];
            boolean resolved = Boolean.parseBoolean(tokens[2]);

            // Get the account and item from the managers
            Account account = accountManager.getAccountById(accountId);
            Item item = itemManager.get(itemId);

            // Link rented item to account if it's not returned (resolved)
            if (!resolved)
                account.addRental(item);

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
