package utils;

import java.util.ArrayList;

import errors.AccountException;
import transactions.Transaction;
import transactions.TransactionManager;

public class TransactionIO extends ObjectIO<TransactionManager> {
    public TransactionIO(TransactionManager manager) {
        super(manager);
    }

    public void loadData(String fileName) {
        try {
            ArrayList<String> lines = IOHelper.readFile(fileName);

            for (String line : lines) {
                Transaction transaction = TransactionUtils.parse(line);
                manager.addTransaction(transaction);
            }

            System.out.println("Loaded " + lines.size() + " transactions from \"" + fileName + "\" file.");
        } catch (AccountException e) {
            System.out.println("Error loading transactions from \"" + fileName + "\" file.");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error loading transactions from \"" + fileName + "\" file.");
        }
    }

    public void saveData(String fileName) {
        ArrayList<Transaction> transactions = manager.getTransactions();
        ArrayList<String> lines = new ArrayList<>();

        for (Transaction transaction : transactions) {
            lines.add(TransactionUtils.serialize(transaction));
        }

        IOHelper.createFile(fileName, lines);
        System.out.println("Saved " + transactions.size() + " transactions to \"" + fileName + "\" file.");
    }
}
