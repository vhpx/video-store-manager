package com.guccigang.videostoremanager.utils;

import com.guccigang.videostoremanager.auth.Account;
import com.guccigang.videostoremanager.auth.AccountManager;

public class AccountIO extends ObjectIO<Account> {
    public AccountIO(AccountManager manager, AccountUtils utils) {
        super(manager, utils, "accounts");
    }
}
