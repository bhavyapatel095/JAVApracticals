import java.util.Scanner;

class SeatBooking 
{
    int seatsLeft;
    int successfulBookings = 0;

    SeatBooking(int seats) 
    { 
        seatsLeft = seats;
    }

    void book(String userName) 
    {

    if (seatsLeft > 0) 
        {

        try 
        {
            Thread.sleep(10);
        } 
        catch (InterruptedException e) 
        { 
            System.out.println(e);
        }

            seatsLeft--;
            successfulBookings++;

    System.out.println(userName + " booked a seat.");
        } 
    else 
        {
            System.out.println(userName + " could not book a seat.");
        }
    }


synchronized void synchronizedBook(String userName) 
    { 
        if (seatsLeft > 0) 
            {
        try 
        {
            Thread.sleep(10);
        } 
        catch (InterruptedException e)  
        { 
            System.out.println(e);
        }

            seatsLeft--;
            successfulBookings++;

        System.out.println(userName + " booked a seat.");
            } 
        else 
            {
            System.out.println(userName + " could not book a seat.");
            }
    }
}

class BookingThread extends Thread 
{ 
    SeatBooking booking;
    String userName;
    boolean useSynchronization;

BookingThread(SeatBooking booking, String userName,boolean useSynchronization)
    { 
        this.booking = booking;
        this.userName = userName;
        this.useSynchronization = useSynchronization;
    }

public void run() 
    {
    if (useSynchronization) 
        {
            booking.synchronizedBook(userName);
        } 
    else 
        {
            booking.book(userName);
        }
    }
}

public class SeatBookingRace 
{

public static void main(String[] args) throws Exception 
    { 
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of seats: "); 
        int totalSeats = sc.nextInt();

        System.out.print("Enter number of users: "); 
        int numberOfUsers = sc.nextInt();

        System.out.print("Use synchronization? (yes/no): "); 
        String choice = sc.next();

        boolean useSynchronization = choice.equalsIgnoreCase("yes"); 
        SeatBooking booking = new SeatBooking(totalSeats);
        BookingThread[] users = new BookingThread[numberOfUsers];

    for (int i = 0; i < numberOfUsers; i++) 
        { 
            String userName = "User-" + (i + 1);
            users[i] = new BookingThread( booking,userName,useSynchronization);
        }

    for (int i = 0; i < numberOfUsers; i++) 
        { 
            users[i].start();
        }

    for (int i = 0; i < numberOfUsers; i++) 
        { 
            users[i].join();
        }

System.out.println("\n----- Booking Result	");

System.out.println("Total Seats: "+ totalSeats);

System.out.println("Total Users: "+ numberOfUsers);

System.out.println("Successful Bookings: "+ booking.successfulBookings);
System.out.println("Seats Left: "+ booking.seatsLeft);

    if (booking.successfulBookings > totalSeats) 
        {
            System.out.println("Overselling occurred!");
        } 
    else 
        {
            System.out.println("No overselling occurred.");
        }

        sc.close();
    }
}
