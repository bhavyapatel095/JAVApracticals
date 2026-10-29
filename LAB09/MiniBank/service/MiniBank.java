package service;

import exception.InvalidAmountException;
import model.Account;
import model.SavingsAccount;

public class MiniBank {
    public static void main(String[] args) {
        Account account = new SavingsAccount("Thread Test", 0, 0);

        int numberOfThreads = 10;
        int depositsPerThread = 10_000;
        long depositAmount = 1;

        Thread[] threads = new Thread[numberOfThreads];

        System.out.println("Starting deposit threads...");

        for (int i = 0; i < numberOfThreads; i++) {
            AccountWorker worker =
                    new AccountWorker(account, depositsPerThread, depositAmount);

            threads[i] = new Thread(worker, "Deposit-Thread-" + (i + 1));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Main thread was interrupted.");
                return;
            }
        }

        long expectedBalance =
                (long) numberOfThreads * depositsPerThread * depositAmount;

        System.out.println("\nAll threads have finished.");
        System.out.println("Expected balance: Rs. " + expectedBalance);
        System.out.println("Actual balance:   Rs. " + account.getBalance());
    }
}

class AccountWorker implements Runnable {
    private final Account account;
    private final int times;
    private final long amount;

    public AccountWorker(Account account, int times, long amount) {
        this.account = account;
        this.times = times;
        this.amount = amount;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " started.");

        for (int i = 0; i < times; i++) {
            try {
                account.deposit(amount);
            } catch (InvalidAmountException e) {
                System.out.println(threadName + ": " + e.getMessage());
                return;
            }
        }

        System.out.println(threadName + " finished.");
    }
}