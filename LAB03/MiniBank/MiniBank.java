import java.util.Scanner;

public class MiniBank {
    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        EXIT
    }

    record BankInfo(String name, String branch) {
    }

    public static void main(String[] args) {
        BankInfo bankInfo = new BankInfo("MiniBank", "Kheroli Branch");
        Scanner s = new Scanner(System.in);

        Account[] accounts = {
            new Account("Bhavya", 5000),
            new Account("Japan", 10000),
            new Account("Shivam")
        };

        Customer.Address address =
                new Customer.Address("Main Road", "Kheroli", "123456");

        Customer customer = new Customer(
                "Bhavya",
                "bhavya@example.com",
                "9876543210",
                address
        );

        Customer copiedCustomer = customer.clone();

        System.out.println("=================================");
        System.out.println("      " + bankInfo.name());
        System.out.println("      Branch: " + bankInfo.branch());
        System.out.println("=================================");

        // Print accounts using toString()
        System.out.println("\nAccounts:");
        for (Account account : accounts) {
            System.out.println(account.toString());
        }

        // Compare two accounts using equals()
        System.out.println("\nAre the first two accounts equal? "
                + accounts[0].equals(accounts[1]));

        // Check an object's type using instanceof
        Object object = accounts[0];
        if (object instanceof Account) {
            System.out.println("The object is an Account.");
        }

        System.out.println("\nCustomer: " + copiedCustomer.getName());
        System.out.println("Customer ID: " + copiedCustomer.getCustomerId());
        System.out.println("Address: " + copiedCustomer.getAddress());

        // Print the formatted account statement
        System.out.println("\n" + accounts[0].getStatement());

        boolean run = true;

        while (run) {
            System.out.println("\n------ Main Menu ------");
            System.out.println("1) Open Account");
            System.out.println("2) Deposit");
            System.out.println("3) Withdraw");
            System.out.println("4) Transfer");
            System.out.println("5) Exit");
            System.out.print("Enter your choice: ");

            int choice = s.nextInt();
            MenuOption option;

            switch (choice) {
                case 1 -> option = MenuOption.OPEN_ACCOUNT;
                case 2 -> option = MenuOption.DEPOSIT;
                case 3 -> option = MenuOption.WITHDRAW;
                case 4 -> option = MenuOption.TRANSFER;
                case 5 -> option = MenuOption.EXIT;
                default -> option = null;
            }

            if (option == null) {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }

            switch (option) {
                case OPEN_ACCOUNT -> {
                    System.out.println("\nAccounts:");
                    for (Account account : accounts) {
                        System.out.println(account);
                    }
                }

                case DEPOSIT -> {
                    int index = selectAccount(s, accounts);
                    if (index == -1) {
                        break;
                    }

                    System.out.print("Enter amount: ");
                    long amount = s.nextLong();

                    if (accounts[index].deposit(amount)) {
                        System.out.println("Deposit successful.");
                    } else {
                        System.out.println("Amount must be greater than zero.");
                    }

                    System.out.println(accounts[index]);
                }

                case WITHDRAW -> {
                    int index = selectAccount(s, accounts);
                    if (index == -1) {
                        break;
                    }

                    System.out.print("Enter amount: ");
                    long amount = s.nextLong();

                    if (accounts[index].withdraw(amount)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println(
                                "Withdrawal failed. Check the amount and balance."
                        );
                    }

                    System.out.println(accounts[index]);
                }

                case TRANSFER ->
                    System.out.println("Transfer feature will be implemented later.");

                case EXIT -> {
                    System.out.println("Thank you for using MiniBank. Goodbye!");
                    run = false;
                }
            }
        }

        s.close();
    }

    private static int selectAccount(Scanner s, Account[] accounts) {
        System.out.println("Select account:");

        for (int i = 0; i < accounts.length; i++) {
            System.out.println((i + 1) + ". " + accounts[i].getOwnerName());
        }

        int choice = s.nextInt();

        if (choice < 1 || choice > accounts.length) {
            System.out.println("Invalid account.");
            return -1;
        }

        return choice - 1;
    }
}