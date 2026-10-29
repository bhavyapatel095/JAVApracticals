package app.main;

import app.model.Item;
import app.service.ItemService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ItemApp {
    public static void main(String[] args) {
        ItemService service = new ItemService();
        Path file = Paths.get("items.dat");

        try {
            if (java.nio.file.Files.exists(file)) {
                service.load(file);
                System.out.println("Data loaded successfully.");
            }
        } catch (Exception e) {
            System.out.println("Could not load data: " + e.getMessage());
        }

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n1. Add item");
                System.out.println("2. List items");
                System.out.println("3. Save data");
                System.out.println("4. Exit");
                System.out.print("Enter choice: ");

                String choice = sc.nextLine();

                try {
                    switch (choice) {
                        case "1":
                            System.out.print("Enter item name: ");
                            service.add(sc.nextLine());
                            System.out.println("Item added.");
                            break;

                        case "2":
                            for (Item item : service.all()) {
                                System.out.println(item.getName());
                            }
                            break;

                        case "3":
                            service.save(file);
                            System.out.println("Data saved successfully.");
                            break;

                        case "4":
                            System.out.println("Goodbye!");
                            return;

                        default:
                            System.out.println("Invalid choice.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
}