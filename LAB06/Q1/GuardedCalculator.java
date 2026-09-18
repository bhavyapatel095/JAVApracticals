import java.util.InputMismatchException;
import java.util.Scanner;

public class GuardedCalculator 
{
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        boolean success = false;
        int attemptCounter = 0;

        System.out.println("===== Guarded Calculator Started =====");

        while (!success) 
            {
            attemptCounter++;
            System.out.println("\n**** Attempt #" + attemptCounter + " ****");
            
            try 
            {
                // 1. Read two numbers
                System.out.print("Enter first number: ");
                double num1 = scanner.nextDouble();

                System.out.print("Enter an operator (+, -, *, /, %): ");
                char operator = scanner.next().charAt(0);

                System.out.print("Enter second number: ");
                double num2 = scanner.nextDouble();


                double result = 0;
                switch (operator) 
                {
                    case '+': result = num1 + num2; 
                    break;
                    
                    case '-': result = num1 - num2; 
                    break;
                    
                    case '*': result = num1 * num2; 
                    break;
                    
                    case '%': result = num1 % num2; 
                     if (num2 == 0) 
                        {
                            throw new DivideByZeroException("Error: Cannot divide by zero.");
                        }

                        result = num1 / num2;
                        break;
                        
                    case '/':

                        if (num2 == 0) 
                        {
                            throw new DivideByZeroException("Error: Cannot divide by zero.");
                        }

                        result = num1 / num2;
                        break;

                    default:
                        System.out.println("Invalid operator! Please try again.");
                        continue; 
                }

                
                System.out.println("Result: " + num1 + " " + operator + " " + num2 + " = " + result);
                success = true;

            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Invalid input: Please enter valid numerical values.");
                scanner.nextLine();
            } 
            catch (DivideByZeroException e) 
            {
                System.out.println(e.getMessage());
            } 
            finally 
            {
                System.out.println("[Log] Attempt #" + attemptCounter + " processed.");
            }
        }

        System.out.println("\nCalculation successful! Program terminated.");
        scanner.close(); 
                       
    }
}
