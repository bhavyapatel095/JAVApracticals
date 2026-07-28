import java.util.Scanner;
public class TollBooth
{
    record Vehicle(String number, String type){}
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int totalToll=0;
        int bikeCount=0;
        int carCount=0;
        int truckCount=0;
        while(true)
        {
            System.out.print("Enter vehicle number (or done to stop): ");
            String number = sc.next();
            if (number.equals("done"))
            {
                break;
            }
            System.out.print("Enter vehicle type (bike/car/truck): ");
            String type = sc.next();
            Vehicle vehicle = new Vehicle(number, type);
            switch (vehicle.type())
            {
                case "bike":
                    totalToll += 20;
                    bikeCount++;
                    break;
                case "car":
                    totalToll += 50;
                    carCount++;
                    break;
                case "truck":
                    totalToll += 150;
                    truckCount++;
                    break;
                default:
                    System.out.println("Invalid vehicle type!");
            }
        }
        System.out.println("Total toll: " + totalToll);
        if(bikeCount >= carCount && bikeCount >= truckCount)
        {
            System.out.println("Most frequent: bike");
        } 
        else if(carCount >= bikeCount && carCount >= truckCount)
        {
            System.out.println("Most frequent: car");
        } 
        else
        {
            System.out.println("Most frequent: truck");
        }
        sc.close();
    }
}