public class MiniBank {
    public static void main(String[] args) {
        Account[] accounts = {
            new SavingsAccount("Bhavya", 5000, 1000),
            new CurrentAccount("Japan", 10000, 2000),
            new FixedDepositAccount("Shivam", 20000)
        };

        System.out.println("MiniBank Account Details");
        System.out.println("=========================");

        for (Account account : accounts) {
            System.out.println(account);
            System.out.println("Interest rate: " + account.interestRate() + "%");
            System.out.printf("Estimated monthly interest: Rs. %.2f%n",
                    account.monthlyInterest());

            if (account instanceof SavingsAccount savingsAccount) {
                System.out.println("Minimum balance: Rs. "
                        + savingsAccount.getMinBalance());
            }

            System.out.println();
        }

        // Demonstrate each account's withdrawal rule.
        System.out.println("Withdrawal examples:");
        System.out.println("Savings withdrawal of Rs. 4500: "
                + accounts[0].withdraw(4500));
        System.out.println("Current withdrawal of Rs. 11000: "
                + accounts[1].withdraw(11000));
        System.out.println("Fixed deposit withdrawal of Rs. 1000: "
                + accounts[2].withdraw(1000));
    }
}