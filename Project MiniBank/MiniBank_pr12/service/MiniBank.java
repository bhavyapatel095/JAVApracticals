package service;

import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import model.Account;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.SavingsAccount;

public class MiniBank {
    public static void main(String[] args) throws InterruptedException {
        AccountStore store = new AccountStore();

        Account account1 = new SavingsAccount("Bhavya", 5000, 1000);
        Account account2 = new CurrentAccount("Japan", 10000, 2000);
        Account account3 = new FixedDepositAccount("Shivam", 20000);

        store.add(account1);
        store.add(account2);
        store.add(account3);

        store.addTransaction("Bhavya opened account " + account1.getAccountNumber());
        store.addTransaction("Japan opened account " + account2.getAccountNumber());
        store.addTransaction("Shivam opened account " + account3.getAccountNumber());

        System.out.println("Accounts sorted by balance:");
        for (Account account : store.sortedByBalance()) {
            System.out.println(account);
        }

        System.out.println("\nFind account by number:");
        Account found = store.get(account1.getAccountNumber());
        System.out.println(found);

        System.out.println("\nTransaction history:");
        for (String transaction : store.getTransactionHistory()) {
            System.out.println(transaction);
        }

        // Give the transaction processor its thread-safe account map.
        TransactionProcessor processor = new TransactionProcessor();
        for (Account account : store.getAll()) {
            processor.addAccount(account);
        }

        processor.submit(new AccountTransactionTask(
                processor, account1.getAccountNumber(), true, 500
        ));
        processor.submit(new AccountTransactionTask(
                processor, account2.getAccountNumber(), false, 1000
        ));

        processor.stop();

        System.out.println("\nBalances after concurrent transactions:");
        for (Account account : store.getAll()) {
            System.out.println(account);
        }

        System.out.println("\nAccounts with balance above Rs. 5000:");
        for (Account account : store.getAccountsAbove(5000)) {
            System.out.println(account);
        }
    }
}

class AccountTransactionTask implements Runnable {
    private final TransactionProcessor processor;
    private final String accountNumber;
    private final boolean deposit;
    private final long amount;

    public AccountTransactionTask(
            TransactionProcessor processor,
            String accountNumber,
            boolean deposit,
            long amount) {
        this.processor = processor;
        this.accountNumber = accountNumber;
        this.deposit = deposit;
        this.amount = amount;
    }

    @Override
    public void run() {
        Account account = processor.getAccount(accountNumber);

        if (account == null) {
            System.out.println("Account not found: " + accountNumber);
            return;
        }

        try {
            if (deposit) {
                account.deposit(amount);
                System.out.println("Deposited Rs. " + amount
                        + " into " + accountNumber);
            } else {
                account.withdraw(amount);
                System.out.println("Withdrew Rs. " + amount
                        + " from " + accountNumber);
            }
        } catch (InsufficientFundsException | InvalidAmountException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }
}
