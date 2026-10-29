public class MiniBank {
    public static void main(String[] args) {
        Account[] accounts = {
            new Account("Asha", 5000),
            new Account("Ravi"),
            new Account("Neha", 2500)
        };

        accounts[0].deposit(1000);
        accounts[1].deposit(3000);

        boolean withdrawalSuccessful = accounts[2].withdraw(500);
        System.out.println("Neha's withdrawal successful: " + withdrawalSuccessful);

        boolean transferSuccessful =
                Account.transfer(accounts[0], accounts[1], 2000);
        System.out.println("Transfer successful: " + transferSuccessful);

        System.out.println("\nAccount balances:");
        for (Account account : accounts) {
            System.out.println(
                    account.getAccountNumber()
                    + " | Owner: " + account.getOwnerName()
                    + " | Balance: Rs. " + account.getBalance()
            );
        }
    }
}