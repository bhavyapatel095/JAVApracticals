import java.util.Scanner;

public class ItemApp {
    public static void main(String[] args) {
        ItemService service = new ItemService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add");
            System.out.println("2. List");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Enter item name: ");
                        service.add(sc.nextLine());
                        System.out.println("Item added successfully");
                        break;

                    case "2":
                        for (Item item : service.all()) {
                            System.out.println(item.getName());
                        }
                        break;

                    case "3":
                        System.out.println("Exiting...");
                        sc.close();
                        return;

                    default:
                        service.invalidAction();
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}