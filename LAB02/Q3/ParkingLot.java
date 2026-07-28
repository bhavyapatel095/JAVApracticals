import java.util.*;

public class ParkingLot 
{
    private int twoWheelers;
    private int fourWheelers;
    private final int twoCap;
    private final int fourCap;
    private static long revenue=0;

    public ParkingLot(int twoCap, int fourCap) 
    {
        this.twoCap = twoCap;
        this.fourCap = fourCap;
        twoWheelers = 0;
        fourWheelers = 0;
    }
    public void park(String type)
    {
        switch (type.toUpperCase()) 
        {
            case "TWO":
                
                if(twoWheelers<twoCap)
                {
                    twoWheelers++;
                    revenue+=20;
                }
                else
                {
                    System.out.println("FULL");
                }
                
                break;
            case "FOUR":
                
                if(fourWheelers<fourCap)
                {
                    fourWheelers++;
                    revenue+=40;
                }
                else
                {
                    System.out.println("FULL");
                }
                break;
    
            default:
                System.out.println("Invalid type");
                
        }
    }
    public void leave(String t)
    {
        switch (t.toUpperCase()) 
        {
            case "TWO":
                
                if(twoWheelers>0)
                {
                    twoWheelers--;
    
                }
                else
                {
                    System.out.println("Empty");
                }
                
                break;
            case "FOUR":
                
                if(fourWheelers>0)
                {
                    fourWheelers--;
                    
                }
                else
                {
                    System.out.println("Empty");
                }
                break;
    
            default:
                System.out.println("Invalid type");
                
        }

    }

     public int getTwoWheelers() 
    {
        return twoWheelers;
    }

    public int getFourWheelers() 
    {
        return fourWheelers;
    }

    public static long getRevenue() 
    {
        return revenue;
    }
    public static void main(String[] args)
    {
        Scanner s=new Scanner(System.in);
         System.out.print("Enter two-wheeler capacity:");
        int two = s.nextInt();

        System.out.print("Enter four-wheeler capacity:");
        int four = s.nextInt();

        ParkingLot p= new ParkingLot(two, four);
        while(true)
        {
            System.out.println("1. Park Vehicle");
            System.out.println("2. Leave Vehicle");
            System.out.println("3. Show Status");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");
            int choice = s.nextInt();

            switch (choice) 
            {

                case 1:
                    System.out.print("Enter vehicle type: ");
                    String type = s.next();
                    p.park(type);

                    System.out.println("Two-Wheelers:" + p.getTwoWheelers());
                    System.out.println("Four-Wheelers:" + p.getFourWheelers());
                    break;
                case 2:
                    System.out.print("Enter vehicle type: ");
                    String ty = s.next();
                    p.leave(ty);
                    System.out.println("Two-Wheelers:" + p.getTwoWheelers());
                    System.out.println("Four-Wheelers:" + p.getFourWheelers());
                    break;
                case 3:
                     System.out.println("Two-Wheelers:" + p.getTwoWheelers());
                    System.out.println("Four-Wheelers:" + p.getFourWheelers());
                    System.out.println("Revenue: Rs. " + ParkingLot.getRevenue());
                    break;

                case 4:
                    System.out.println("Final Occupancy");
                    System.out.println("Two-Wheelers :" + p.getTwoWheelers());
                    System.out.println("Four-Wheelers:" + p.getFourWheelers());
                    System.out.println("Total Revenue:Rs. " + ParkingLot.getRevenue());

                    s.close();
                default:
                    System.out.println("Invalid Choice");



            }
        }
    }
}