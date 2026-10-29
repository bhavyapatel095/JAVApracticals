import java.util.Scanner;

public class MiniBank 
{

    // Record to store bank information
    record BankInfo(String name, String branch) {}

    // Enum for menu options
    enum MenuOption 
    {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        EXIT
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) 
    {

        Scanner sc = new Scanner(System.in);

        // Create and display bank information
        BankInfo bankInfo = new BankInfo("MiniBank", "Kheroli Branch");

        System.out.println("=================================");
        System.out.println("      " + bankInfo.name());
        System.out.println("      Branch: " + bankInfo.branch());
        System.out.println("=================================");

        while (true) 
        {

            // Display menu
            System.out.println("\n------ Main Menu ------");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            // Switch expression
            String message = switch (choice) 
            
            {
                case 1 -> "Open Account - To be implemented in a later lab.";
                case 2 -> "Deposit - To be implemented in a later lab.";
                case 3 -> "Withdraw - To be implemented in a later lab.";
                case 4 -> "Transfer - To be implemented in a later lab.";
                case 5 -> "Thank you for using MiniBank. Goodbye!";
                default -> "Invalid choice! Please try again.";
            };

            System.out.println(message);

            if (choice == 5) 
            {
                break;
            }
        }

        sc.close();
    }
}