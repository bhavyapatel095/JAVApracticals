package service;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import model.Account;
import model.CurrentAccount;
import model.SavingsAccount;

public class MiniBank {
    public static void main(String[] args) throws InterruptedException {
        Account account = new SavingsAccount("Bhavya", 10000, 0);

        // Submit deposit and withdrawal tasks to a four-thread pool.
        TransactionProcessor processor = new TransactionProcessor();

        for (int i = 0; i < 10; i++) {
            processor.submit(new TransactionTask(account, true, 100));
            processor.submit(new TransactionTask(account, false, 50));
        }

        processor.stop();
        System.out.println("Balance after thread-pool tasks: Rs. "
                + account.getBalance());

        // Producer-consumer example using a shared array buffer.
        TransactionBuffer buffer = new TransactionBuffer(3);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.put(new TransactionTask(account, true, 10));
                    System.out.println("Produced transaction " + i);
                }

                buffer.finish();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            try {
                Runnable task;

                while ((task = buffer.take()) != null) {
                    task.run();
                    System.out.println("Consumer processed a transaction.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        System.out.println("Balance after producer-consumer: Rs. "
                + account.getBalance());

        // Opposite-direction transfers acquire locks in account-number order.
        Account accountA = new CurrentAccount("Account A", 1000, 0);
        Account accountB = new CurrentAccount("Account B", 1000, 0);

        Thread transferAtoB = new Thread(
                () -> doTransfer(accountA, accountB, 100),
                "Transfer-A-to-B"
        );

        Thread transferBtoA = new Thread(
                () -> doTransfer(accountB, accountA, 100),
                "Transfer-B-to-A"
        );

        transferAtoB.start();
        transferBtoA.start();
        transferAtoB.join();
        transferBtoA.join();

        System.out.println("Account A balance: Rs. " + accountA.getBalance());
        System.out.println("Account B balance: Rs. " + accountB.getBalance());
    }

    private static void doTransfer(Account from, Account to, long amount) {
        try {
            System.out.println(Thread.currentThread().getName() + " started.");
            from.transfer(to, amount);
            System.out.println(Thread.currentThread().getName() + " finished.");
        } catch (BankException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }
}

class TransactionProcessor {
    private final ExecutorService executor =
            Executors.newFixedThreadPool(4);

    public void submit(Runnable task) {
        executor.execute(task);
    }

    public void stop() throws InterruptedException {
        executor.shutdown();

        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
            executor.shutdownNow();
            executor.awaitTermination(30, TimeUnit.SECONDS);
        }
    }
}

class TransactionTask implements Runnable {
    private final Account account;
    private final boolean deposit;
    private final long amount;

    public TransactionTask(Account account, boolean deposit, long amount) {
        this.account = account;
        this.deposit = deposit;
        this.amount = amount;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

        try {
            if (deposit) {
                account.deposit(amount);
                System.out.println(threadName + " deposited Rs. " + amount);
            } else {
                account.withdraw(amount);
                System.out.println(threadName + " withdrew Rs. " + amount);
            }
        } catch (InsufficientFundsException | InvalidAmountException e) {
            System.out.println(threadName + ": " + e.getMessage());
        }
    }
}

class TransactionBuffer {
    private final Runnable[] items;
    private int head;
    private int tail;
    private int count;
    private boolean finished;

    public TransactionBuffer(int capacity) {
        items = new Runnable[capacity];
    }

    public synchronized void put(Runnable task) throws InterruptedException {
        while (count == items.length) {
            wait();
        }

        items[tail] = task;
        tail = (tail + 1) % items.length;
        count++;
        notifyAll();
    }

    public synchronized Runnable take() throws InterruptedException {
        while (count == 0 && !finished) {
            wait();
        }

        if (count == 0 && finished) {
            return null;
        }

        Runnable task = items[head];
        items[head] = null;
        head = (head + 1) % items.length;
        count--;
        notifyAll();
        return task;
    }

    public synchronized void finish() {
        finished = true;
        notifyAll();
    }
}