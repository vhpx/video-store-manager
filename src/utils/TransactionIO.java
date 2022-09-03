package utils;

import transactions.Transaction;
import transactions.TransactionManager;

public class TransactionIO extends ObjectIO<Transaction> {
    public TransactionIO(TransactionManager manager, TransactionUtils utils) {
        super(manager, utils, "transactions");
    }
}