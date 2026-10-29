package service;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import model.Account;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.SavingsAccount;

public class MiniBank {
    public static void main(String[] args) {
        Account savings = new SavingsAccount("Bhavya", 5000, 1000);
        Account current = new CurrentAccount("Japan", 10000, 2000);
        Account fixedDeposit = new FixedDepositAccount("Shivam", 20000);

        try {
            savings.deposit(1000);
            System.out.println("Deposit successful. Balance: Rs. "
                    + savings.getBalance());

            savings.withdraw(8000);
        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
            System.out.println("Shortfall: Rs. " + e.getShortfall());
        } catch (InvalidAmountException e) {
            System.out.println("Invalid amount: " + e.getMessage());
        } finally {
            System.out.println("Withdrawal operation finished.");
        }

        try {
            savings.transfer(current, 1000);
            System.out.println("Savings balance: Rs. " + savings.getBalance());
            System.out.println("Current balance: Rs. " + current.getBalance());
        } catch (BankException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }

        try {
            fixedDeposit.withdraw(500);
        } catch (InsufficientFundsException e) {
            System.out.println("Fixed deposit withdrawal failed: "
                    + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("Invalid amount: " + e.getMessage());
        }

        try (BankSession session = new BankSession()) {
            session.displayMessage();
        }
    }
}

class BankSession implements AutoCloseable {
    public void displayMessage() {
        System.out.println("Bank session is open.");
    }

    @Override
    public void close() {
        System.out.println("Bank session closed automatically.");
    }
}
