package service;

import java.nio.file.Path;
import java.util.Scanner;
import model.Account;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.SavingsAccount;

record BankInfo(String name, String branch) {
}

enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    STATEMENT,
    LIST_ACCOUNTS,
    BATCH,
    REPORT,
    EXIT
}

public class MiniBank {
    private static String read(Scanner scanner, String label) {
        System.out.print(label + ": ");
        return scanner.nextLine().trim();
    }

    public static void main(String[] args) {
        System.out.println(new BankInfo("MiniBank", "Kheroli Branch"));

        try {
            BankService bank = new BankService(Path.of("data"));

            // Use your Practical 2 account data on the first run.
            if (bank.listAccounts().isEmpty()) {
                bank.registerAccount(
                        new SavingsAccount("Bhavya", 5000, 1000)
                );
                bank.registerAccount(
                        new CurrentAccount("Japan", 10000, 2000)
                );
                bank.registerAccount(
                        new FixedDepositAccount("Shivam", 0)
                );
            }

            try (Scanner scanner = new Scanner(System.in)) {
                boolean exit = false;

                while (!exit) {
                    System.out.println("\n1. Open account");
                    System.out.println("2. Deposit");
                    System.out.println("3. Withdraw");
                    System.out.println("4. Transfer");
                    System.out.println("5. Statement");
                    System.out.println("6. List accounts");
                    System.out.println("7. Batch deposits");
                    System.out.println("8. Report");
                    System.out.println("9. Exit");

                    try {
                        int choice = Integer.parseInt(
                                read(scanner, "Enter choice")
                        );

                        if (choice < 1 || choice > MenuOption.values().length) {
                            System.out.println("Invalid choice.");
                            continue;
                        }

                        MenuOption option = MenuOption.values()[choice - 1];

                        switch (option) {
                            case OPEN_ACCOUNT -> {
                                String type = read(
                                        scanner, "Type SAVINGS/CURRENT/FD"
                                );
                                String name = read(scanner, "Name");
                                String email = read(scanner, "Email");
                                String mobile = read(scanner, "Mobile");
                                String pan = read(scanner, "PAN");
                                String ifsc = read(scanner, "IFSC");
                                long opening = Long.parseLong(
                                        read(scanner, "Opening balance")
                                );

                                Account account = bank.openAccount(
                                        type, name, email, mobile,
                                        pan, ifsc, opening
                                );
                                System.out.println("Opened: " + account);
                            }

                            case DEPOSIT, WITHDRAW -> {
                                showAccounts(bank);
                                String id = read(scanner, "Account number");
                                long amount = Long.parseLong(
                                        read(scanner, "Amount")
                                );

                                if (option == MenuOption.DEPOSIT) {
                                    bank.deposit(id, amount);
                                } else {
                                    bank.withdraw(id, amount);
                                }

                                System.out.println(
                                        "New balance: Rs. " + bank.balance(id)
                                );
                            }

                            case TRANSFER -> {
                                showAccounts(bank);
                                String from = read(scanner, "From account");
                                String to = read(scanner, "To account");
                                long amount = Long.parseLong(
                                        read(scanner, "Amount")
                                );

                                bank.transfer(from, to, amount);
                                System.out.println("Transfer completed.");
                            }

                            case STATEMENT -> {
                                String id = read(scanner, "Account number");
                                System.out.println(bank.statement(id));
                            }

                            case LIST_ACCOUNTS -> showAccounts(bank);

                            case BATCH -> {
                                showAccounts(bank);
                                String id = read(scanner, "Account number");
                                int tasks = Integer.parseInt(
                                        read(scanner, "Number of deposits")
                                );
                                long amount = Long.parseLong(
                                        read(scanner, "Amount per deposit")
                                );

                                bank.batchDeposit(id, tasks, amount);
                                System.out.println(
                                        "Batch completed. Balance: Rs. "
                                                + bank.balance(id)
                                );
                            }

                            case REPORT -> System.out.println(bank.report());

                            case EXIT -> {
                                bank.save();
                                exit = true;
                                System.out.println("Accounts saved. Goodbye!");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("MiniBank could not start: " + e.getMessage());
        }
    }

    private static void showAccounts(BankService bank) {
        System.out.println("\nAccounts:");
        for (Account account : bank.listAccounts()) {
            System.out.println(account);
        }
    }
}