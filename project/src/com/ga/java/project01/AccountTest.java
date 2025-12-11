package com.ga.java.project01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {


    private Account account1;
    private Account account2;

    @BeforeEach
    void setUp() {
        account1 = new Account("test001", 500, "mastercard", "checking");
        account2 = new Account("test002", 300, "mastercard", "checking");
    }

    @Test
    void testDeposit() {
        boolean result = account1.deposit(200);
        assertTrue(result);
        assertEquals(700, account1.getBalance());
    }

    @Test
    void testWithdrawWithinBalance() {
        boolean result = account1.withdraw(200);
        assertTrue(result);
        assertEquals(300, account1.getBalance());
    }

    @Test
    void testTransfer() {
        Account.createAccount(account1);
        Account.createAccount(account2);

        boolean result = account1.transfer(account2.accountId, 200);
        assertTrue(result);
        assertEquals(300, account1.getBalance());
        assertEquals(500, account2.getBalance());
    }

    @Test
    void testTransferInsufficientFunds() {
        boolean result = account1.transfer(account2.accountId, 600);
        assertFalse(result);
        assertEquals(500, account1.getBalance());
        assertEquals(300, account2.getBalance());
    }
}

