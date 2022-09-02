package utils;

import auth.Account;
import auth.AccountManager;

public class AccountIO extends ObjectIO<Account> {
    public AccountIO(AccountManager manager, AccountUtils utils) {
        super(manager, utils, "accounts");
    }
}
