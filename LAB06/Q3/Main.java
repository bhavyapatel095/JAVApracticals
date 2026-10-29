import discount.DiscountRule;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<Double> prices = Arrays.asList(
                1000.0,
                2000.0,
                3000.0,
                5000.0
        );

        Scanner sc = new Scanner(System.in);

        System.out.println("Discount Engine");
        System.out.println("1. 10% Discount");
        System.out.println("2. 20% Discount");
        System.out.println("3. Flat Rs.500 Discount");

        System.out.print("Choose discount: ");
        int choice = sc.nextInt();

        DiscountRule rule;

        switch (choice) {

            case 1:
                rule = price -> price * 0.90;
                break;

            case 2:
                rule = price -> price * 0.80;
                break;

            case 3:
                rule = price -> price - 500;
                break;

            default:
                System.out.println("Invalid choice");
                sc.close();
                return;
        }

        System.out.println("\nOriginal Price -> Final Price");

        for (double price : prices) {

            double finalPrice = rule.apply(price);

            if (finalPrice < 0) {
                finalPrice = 0;
            }

            System.out.println(
                    "Rs." + price + " -> Rs." + finalPrice
            );
        }

        sc.close();
    }
}