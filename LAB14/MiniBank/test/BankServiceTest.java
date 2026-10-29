package test;

import exception.BankException;
import exception.InsufficientFundsException;
import java.nio.file.Path;
import model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import service.BankService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankServiceTest {
    @TempDir
    Path folder;

    private Account openBhavyaAccount(BankService bank) throws BankException {
        return bank.openAccount(
                "SAVINGS",
                "Bhavya",
                "bhavya@example.com",
                "9876543210",
                "ABCDE1234F",
                "SBIN0001234",
                1000
        );
    }

    @Test
    void depositIncreasesBalance() throws Exception {
        BankService bank = new BankService(folder);
        Account account = openBhavyaAccount(bank);

        bank.deposit(account.getAccountNumber(), 500);

        assertEquals(1500, bank.balance(account.getAccountNumber()));
    }

    @Test
    void withdrawalOverBalanceThrowsException() throws Exception {
        BankService bank = new BankService(folder);
        Account account = openBhavyaAccount(bank);

        assertThrows(
                InsufficientFundsException.class,
                () -> bank.withdraw(account.getAccountNumber(), 5000)
        );

        assertEquals(1000, bank.balance(account.getAccountNumber()));
    }

    @Test
    void concurrentDepositsKeepBalanceCorrect() throws Exception {
        BankService bank = new BankService(folder);
        Account account = openBhavyaAccount(bank);

        bank.batchDeposit(account.getAccountNumber(), 100, 10);

        assertEquals(2000, bank.balance(account.getAccountNumber()));
    }

    @Test
    void savedStateLoadsAndNewAccountGetsDifferentId() throws Exception {
        BankService bank = new BankService(folder);
        Account account = openBhavyaAccount(bank);
        bank.deposit(account.getAccountNumber(), 500);
        bank.save();

        BankService loadedBank = new BankService(folder);

        assertEquals(1500, loadedBank.balance(account.getAccountNumber()));

        Account nextAccount = openBhavyaAccount(loadedBank);
        assertNotEquals(
                account.getAccountNumber(),
                nextAccount.getAccountNumber()
        );
    }
}