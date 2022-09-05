package com.guccigang.videostoremanager.utils;

import com.guccigang.videostoremanager.transactions.Transaction;
import com.guccigang.videostoremanager.transactions.TransactionManager;

public class TransactionIO extends ObjectIO<Transaction> {
    public TransactionIO(TransactionManager manager, TransactionUtils utils) {
        super(manager, utils, "transactions");
    }
}