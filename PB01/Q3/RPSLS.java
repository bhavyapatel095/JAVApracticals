import java.util.Random;
import java.util.Scanner;

public class RPSLS 
{

    enum Move 
    {
        ROCK, PAPER, SCISSORS, LIZARD, SPOCK
    }

    static int winner(Move a, Move b) 
    {

        if (a == b)
            return 0;

        switch (a) 
        {

            case ROCK:
                if (b == Move.SCISSORS || b == Move.LIZARD)
                    return 1;
                else
                    return -1;

            case PAPER:
                if (b == Move.ROCK || b == Move.SPOCK)
                    return 1;
                else
                    return -1;

            case SCISSORS:
                if (b == Move.PAPER || b == Move.LIZARD)
                    return 1;
                else
                    return -1;

            case LIZARD:
                if (b == Move.PAPER || b == Move.SPOCK)
                    return 1;
                else
                    return -1;

            case SPOCK:
                if (b == Move.ROCK || b == Move.SCISSORS)
                    return 1;
                else
                    return -1;
        }

        return 0;
    }

    public static void main(String[] args) 
    {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int playerScore = 0, computerScore = 0;

        for (int i = 1; i <= 5; i++) 
        {

            System.out.println("\n---Round " + i + "---");
            System.out.println("1. ROCK");
            System.out.println("2. PAPER");
            System.out.println("3. SCISSORS");
            System.out.println("4. LIZARD");
            System.out.println("5. SPOCK");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            Move player = null;

            switch (choice) 
            
            {
                case 1:
                    player = Move.ROCK;
                    break;
                case 2:
                    player = Move.PAPER;
                    break;
                case 3:
                    player = Move.SCISSORS;
                    break;
                case 4:
                    player = Move.LIZARD;
                    break;
                case 5:
                    player = Move.SPOCK;
                    break;
                default:
                    System.out.println("Invalid choice");
                    i--;
                    continue;
            }

            int computerChoice = random.nextInt(5) + 1;
            Move computer = null;

            switch (computerChoice) 
            {
                case 1:
                    computer = Move.ROCK;
                    break;
                case 2:
                    computer = Move.PAPER;
                    break;
                case 3:
                    computer = Move.SCISSORS;
                    break;
                case 4:
                    computer = Move.LIZARD;
                    break;
                case 5:
                    computer = Move.SPOCK;
                    break;
            }

            System.out.println("Your Move: " + player);
            System.out.println("Computer Move: " + computer);

            int result = winner(player, computer);

            if (result == 1) 
            {
                System.out.println("You win this round.");
                playerScore++;
            } 
            else if (result == -1) 
            {
                System.out.println("Computer wins this round.");
                computerScore++;
            } 
            else 
            {
                System.out.println("Round Tie.");
            }
        }

        System.out.println("\nFinal Score");
        System.out.println("You: " + playerScore);
        System.out.println("Computer: " + computerScore);

        if (playerScore > computerScore)
            System.out.println("You win " + playerScore + "-" + computerScore);
        else if (computerScore > playerScore)
            System.out.println("Computer wins " + computerScore + "-" + playerScore);
        else
            System.out.println("Match Tie " + playerScore + "-" + computerScore);

        sc.close();
    }
}