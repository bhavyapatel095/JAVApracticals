import java.util.Scanner; class SharedTotal {
long total = 0;

void add(long value) {
total += value;
}

synchronized void synchronizedAdd(long value) { total += value;
}
}

class SumThread extends Thread { int[] numbers;
int start;
int end;
SharedTotal sharedTotal; int method;

SumThread(int[] numbers, int start, int end,
SharedTotal sharedTotal, int method) {

this.numbers = numbers; this.start = start;
this.end = end;
this.sharedTotal = sharedTotal; this.method = method;
}

public void run() {
for (int i = start; i < end; i++) { if (method == 1) {
// No synchronization
sharedTotal.add(numbers[i]);

} else {
// Synchronized method
sharedTotal.synchronizedAdd(numbers[i]);
}
}
}
}

public class ParallelArraySum {

public static void main(String[] args) throws Exception { Scanner sc = new Scanner(System.in);
System.out.print("Enter array size: "); int size = sc.nextInt();

System.out.print("Enter number of threads: "); int numberOfThreads = sc.nextInt();

System.out.print("Enter maximum value of array elements: "); int maxValue = sc.nextInt();

// Creating array
int[] numbers = new int[size];
// Filling array with values
for (int i = 0; i < size; i++) {
numbers[i] = (i % maxValue) + 1;
}

// Calculate correct answer using single thread
long correctTotal = 0;

for (int number : numbers) { correctTotal += number;
}

System.out.println("\nCorrect Total: " + correctTotal);

// Run both methods
for (int method = 1; method <= 2; method++) {

SharedTotal sharedTotal = new SharedTotal();

SumThread[] threads = new SumThread[numberOfThreads]; int part = size / numberOfThreads;
long startTime = System.nanoTime();

// Creating threads
for (int i = 0; i < numberOfThreads; i++) { int start = i * part;
int end;

if (i == numberOfThreads - 1) { end = size;
} else {
end = start + part;
}

threads[i] = new SumThread( numbers,
start, end,
sharedTotal, method);
}
for (int i = 0; i < numberOfThreads; i++) { threads[i].start();
}

// Waiting for threads
for (int i = 0; i < numberOfThreads; i++) { threads[i].join();
}

long endTime = System.nanoTime();

long timeTaken = endTime - startTime;

System.out.println("\n----- Method " + method + "	");

if (method == 1) {
System.out.println(
"Method: Without Synchronization");
} else {
System.out.println(
"Method: Synchronized Method");
}

System.out.println(
"Calculated Total: "
+ sharedTotal.total);

System.out.println(
"Time Taken: "
+ timeTaken
+ " nanoseconds");

if (sharedTotal.total == correctTotal) {
System.out.println("Result is CORRECT.");
} else {
System.out.println("Result is WRONG.");
}
}

sc.close();
}
}
