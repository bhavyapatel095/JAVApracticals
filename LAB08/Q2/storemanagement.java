
import java.util.Scanner;

class outofstockexception extends Exception {

    private int shortage;

    public outofstockexception(int shortage) {
        super("out of stock! shortage: " + shortage);
        this.shortage = shortage;
    }

    public int getshortage() {
        return shortage;
    }
}

class invalidquantityexception extends Exception {

    public invalidquantityexception() {
        super("invalid quantity! must be greater than 0.");
    }
}

class store {

    private int stock;

    public store(int stock) {
        this.stock = stock;
    }

    public void issue(String item, int qty)
            throws outofstockexception, invalidquantityexception {

        if (qty <= 0) {
            throw new invalidquantityexception();
        }

        if (qty > stock) {
            int shortage = qty - stock;
            throw new outofstockexception(shortage);
        }

        stock -= qty;

        System.out.println("issued: " + qty + " " + item);
        System.out.println("left in stock: " + stock);
    }
}

public class storemanagement {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("enter starting stock: ");
        int stock = sc.nextInt();

        store s = new store(stock);

        System.out.print("enter number of requests: ");
        int requests = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= requests; i++) {
            System.out.println("\nrequest " + i);

            System.out.print("enter item name: ");
            String item = sc.nextLine();

            System.out.print("enter quantity: ");
            int qty = sc.nextInt();
            sc.nextLine();

            try {
                s.issue(item, qty);
            } catch (outofstockexception e) {
                System.out.println("error: " + e.getMessage());
            } catch (invalidquantityexception e) {
                System.out.println("error: " + e.getMessage());
            }
        }

        sc.close();
    }
}


