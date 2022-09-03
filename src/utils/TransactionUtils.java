package utils;

import auth.Account;
import auth.AccountManager;
import items.Item;
import items.ItemManager;
import transactions.Transaction;

public class TransactionUtils extends ObjectUtils<Transaction> {
    private AccountManager accountManager = null;
    private ItemManager itemManager = null;

    public TransactionUtils(AccountManager accountManager, ItemManager itemManager) {
        this.accountManager = accountManager;
        this.itemManager = itemManager;
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void setItemManager(ItemManager itemManager) {
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
