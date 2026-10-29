

class MyResource implements AutoCloseable {

    void doWork() {
        System.out.println("Resource is doing some work...");
        throw new RuntimeException("Original error occurred");
    }

    @Override
    public void close() {
        System.out.println("Resource is closed");
        throw new RuntimeException("Error while closing resource");
    }
}

public class main {

    public static void main(String[] args) {

        try (MyResource resource = new MyResource()) {

            System.out.println("Resource is opened");
            resource.doWork();

        } catch (Exception e) {

            System.out.println("Main error: " + e.getMessage());

            for (Throwable error : e.getSuppressed()) {
                System.out.println("Suppressed error: " + error.getMessage());
            }
        }
    }
}

