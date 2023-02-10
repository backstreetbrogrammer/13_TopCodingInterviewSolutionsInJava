package com.backstreetbrogrammer.Q3_Deadlock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AccountTest {

    final Account account1 = new Account(100D);
    final Account account2 = new Account(200D);

    @Test
    @DisplayName("Test method to demonstrate deadlock")
    void testDeadlock() {
        for (int i = 0; i < 1000; i++) {
            Account.transfer(account2, account1, 50D);
            Account.transfer(account1, account2, 30D);
        }
    }

    @Test
    @DisplayName("Test method after fixing the deadlock issue")
    void testDeadlockFixed() {
        for (int i = 0; i < 1000; i++) {
            Account.transferDeadlockFixed(account2, account1, 50D);
            Account.transferDeadlockFixed(account1, account2, 30D);
        }
    }
}
