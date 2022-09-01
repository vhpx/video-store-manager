package utils;

import auth.Account;
import auth.AccountManager;
import errors.AccountException;
import items.Item;
import items.ItemManager;
import transactions.Transaction;

public class TransactionUtils {
    private static AccountManager accountManager = AccountManager.getInstance();
    private static ItemManager itemManager = ItemManager.getInstance();

    public static Transaction parse(String str) throws AccountException {
        String[] tokens = str.split(", ");

        String accountId = tokens[0];
        Account account = accountManager.getAccountById(accountId);

        String itemId = tokens[1];
        Item item = itemManager.getItem(itemId);

        boolean resolved = Boolean.parseBoolean(tokens[2]);

        return new Transaction(account, item, resolved);
    }

    public static String serialize(Transaction transaction) {
        String[] tokens = {
                transaction.getAccount().getId(),
                transaction.getItem().getId(),
                String.valueOf(transaction.isResolved())
        };

        return String.join(", ", tokens);
    }
}
