import java.util.Scanner;
public class VendingMachine
{
    enum Coin
    {
        ONE, TWO, FIVE, TEN
    }
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) 
    {
        int snackPrice = 15;
        int totalAmount = 0;
        int coinValue = 0;
        Scanner sc = new Scanner(System.in);

        while (totalAmount<snackPrice) 
        {
            System.out.print("Enter coin (ONE, TWO, FIVE, TEN): ");
            String input = sc.next();
            Coin coin = null ;
            switch (input) 
            {
                case "ONE":
                    coin = Coin.ONE;
                    break;
                case "TWO":
                    coin = Coin.TWO;
                    break;
                case "FIVE":
                    coin = Coin.FIVE;
                    break;
                case "TEN":
                    coin = Coin.TEN;
                    break;
                default:
                    System.out.println("Invalid Coin!");
                    continue;
            }
            switch (coin) 
            {
                case ONE:
                    coinValue=1;
                    break;
                case TWO:
                    coinValue=2;
                    break;
                case FIVE:
                    coinValue=5;
                    break;
                case TEN:
                    coinValue=10;
                    break;
            }
            totalAmount += coinValue;
            System.out.println("Total paid: " + totalAmount);
        }
        System.out.println("Paid. Change: " + (totalAmount-snackPrice));
        sc.close();
    }
}