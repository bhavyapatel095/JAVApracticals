import java.util.Scanner;
class Counter 
{
int count = 0;

    void increment() 
    {
        count++;
    }

    synchronized void synchronizedIncrement() 
    { 
        count++;
    }
}

class MyThread extends Thread 
{ 
    Counter counter;
    int increments;
    boolean useSynchronization;

MyThread(Counter counter, int increments, boolean useSynchronization) 
{ 
    this.counter = counter;
    this.increments = increments;
    this.useSynchronization = useSynchronization;
}

public void run() 
{
    for (int i = 0; i < increments; i++) 
        {

    if (useSynchronization) 
        {
    counter.synchronizedIncrement();
        } 
    else 
        {
    counter.increment();
        }
        }
}
}

public class CounterRace 
{
public static void main(String[] args) throws Exception 
    { 
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of threads: "); 
        int numberOfThreads = sc.nextInt();

        System.out.print("Enter number of increments per thread: "); 
        int increments = sc.nextInt();

        System.out.print("Use synchronization? (yes/no): "); 
        String choice = sc.next();

        boolean useSynchronization = choice.equalsIgnoreCase("yes"); 
        Counter counter = new Counter();
        MyThread[] threads = new MyThread[numberOfThreads];

            for (int i = 0; i < numberOfThreads; i++) 
                { threads[i] = new MyThread(
                    counter,
                    increments,
                    useSynchronization);
                }

            for (int i = 0; i < numberOfThreads; i++) 
                { 
                    threads[i].start();
            }

            for (int i = 0; i < numberOfThreads; i++) 
                {
                     threads[i].join();
            }

            int expectedValue = numberOfThreads * increments; 
            System.out.println("\n----- Result	");
            System.out.println("Number of Threads: " + numberOfThreads);
             System.out.println("Increments per Thread: " + increments);
            System.out.println("Expected Counter Value: " + expectedValue); 
            System.out.println("Actual Counter Value: " + counter.count);

            if (counter.count == expectedValue) 
                {
                    System.out.println("Result is correct.");
                } 
            else
                {
                    System.out.println("Race condition occurred.");
                }

    sc.close();
    }
}
