import java.util.*;

public class CinemaShow 
{
    @SuppressWarnings("unused")
    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked=0;
    public CinemaShow(String t,int c)
    {
        title=t;
        capacity=c;
        seatsAvailable=capacity;
    }
    public CinemaShow(String t)
    {
        this(t,100);
    }

    public boolean book (int n)
    {
        if(n<=seatsAvailable)
        {
            seatsAvailable-=n;
            totalBooked+=n;
            return true;
        }
        else
        {
            return false;
        }
    }
    public void cancel(int n)
    {
        seatsAvailable+=n;
        if(seatsAvailable>capacity)
        {
            seatsAvailable=capacity;
        }
    }
    public int getSeatsAvailable()
    {
        return seatsAvailable;
    }
    public static int getTotalBooked()
    {
        return totalBooked;
    }
    public static void main(String[] args)
    {
       
        Scanner s= new Scanner(System.in);

        
        int cap;
        System.out.print("Enter capcaity of cinema:");
        cap=s.nextInt();
        s.nextLine();

        String t;
        System.out.print("Enter movie:");
        t=s.nextLine();

         CinemaShow c= new CinemaShow(t,cap);

        while (true){
            System.out.println("1. Book ticket");
            System.out.println("2. Cancel ticket");
            System.out.println("3. Show Available Seats");
            System.out.println("4. Exit");
            System.out.print("Enter your choice:");
            int choice=s.nextInt();
            switch ((choice)) 
            {
                case 1:
                    System.out.print("Enter number of tickets:");
                    int ti=s.nextInt();
                    boolean result = c.book(ti);
                    System.out.println("Booking Successful:" + result);
                    System.out.println("Seats Available:" + c.getSeatsAvailable());
                    break;
                
                case 2:
                     System.out.print("Enter number of tickets to cancel:");
                    int cancel = s.nextInt();

                    c.cancel(cancel);

                    System.out.println("Seats Available:" + c.getSeatsAvailable());
                    break;
                case 3:
                    System.out.println("Seats Available:" + c.getSeatsAvailable());
                    break;
                case 4:
                    
                    System.out.println("Total Successful Bookings: " + CinemaShow.getTotalBooked());
                   
                    s.close();
                    return;
                default:
                    System.out.println("Invalid Choice!");
            }
            

        }
        


    }
}