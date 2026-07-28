class Customer implements Cloneable {
    private String name;
    private String email;
    private String mobile;
    private final String customerId;
    private Address address;
    private static long customerCounter = 101;
    public static class Address {
        private String line;
        private String city;
        private String pincode;
        public Address(String line, String city, String pincode) {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }
        public String getLine() {
            return line;
        }
        public String getCity() {
            return city;
        }
        public String getPincode() {
            return pincode;
        }
    }
    private static String generateCustomerId() {
        return "CUST" + customerCounter++;
    }
    public Customer(String name, String email, String mobile, Address address) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.customerId = generateCustomerId();
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getMobile() {
        return mobile;
    }
    public String getCustomerId() {
        return customerId;
    }
    public Address getAddress() {
        return address;
    }
    @Override
    public Customer clone() {
        try {
            return (Customer) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;
    private static int accountCounter = 1;
    private static String generateAccountNumber() {
        return String.format("AC%04d", accountCounter++);
    }
    public Account(String ownerName, long openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }
    public Account(String ownerName) {
        this(ownerName, 0);
    }
    public void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
    public boolean withdraw(long amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
    public static void transfer(Account from, Account to, long amount) {
        if (from.withdraw(amount)) {
            to.deposit(amount);
            System.out.println("Transfer Successful");
        } else {
            System.out.println("Transfer Failed");
        }
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public long getBalance() {
        return balance;
    }
    public boolean isActive() {
        return active;
    }
    @Override
    public String toString() {
        return "Account Number : " + accountNumber +
                ", Owner : " + ownerName +
                ", Balance : " + balance;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Account))
            return false;
        Account other = (Account) o;
        return accountNumber.equals(other.accountNumber);
    }

    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }
}
public class MiniBank {
    record BankInfo(String name, String branch) {
    }
    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        EXIT
    }
    public static void main(String[] args) {
        BankInfo bankInfo = new BankInfo("MiniBank", "Petlad Branch");
        System.out.println("==================================");
        System.out.println("        " + bankInfo.name());
        System.out.println("      " + bankInfo.branch());
        System.out.println("==================================");
        Customer customer1 = new Customer(
                "Rahul",
                "rahul@gmail.com",
                "9876543210",
                new Customer.Address(
                        "101 Green Park",
                        "Anand",
                        "388001"));
        Customer customer2 = new Customer(
                "Priya",
                "priya@gmail.com",
                "9876501234",
                new Customer.Address(
                        "22 Lake View",
                        "Vadodara",
                        "390001"));
        Customer customer3 = new Customer(
                "Amit",
                "amit@gmail.com",
                "9999988888",
                new Customer.Address(
                        "15 River Road",
                        "Ahmedabad",
                        "380001"));
        Account[] accounts = new Account[3];
        accounts[0] = new Account(customer1.getName(), 5000);
        accounts[1] = new Account(customer2.getName());
        accounts[2] = new Account(customer3.getName(), 10000);
        accounts[0].deposit(1000);
        accounts[1].deposit(3000);
        if (accounts[2].withdraw(2500))
            System.out.println("Withdrawal Successful");
        else
            System.out.println("Withdrawal Failed");
        if (accounts[1].withdraw(5000))
            System.out.println("Withdrawal Failed");
        else
            System.out.println("Withdrawal Failed");
        Account.transfer(accounts[0], accounts[1], 1500);
        System.out.println("\n===== Account Details =====");
        for (Account account : accounts) {
            System.out.println(account);
        }
        System.out.println("\nComparing Accounts");
        System.out.println("Account 1 equals Account 2 : " +
                accounts[0].equals(accounts[1]));
        System.out.println("Account 1 equals Account 1 : " +
                accounts[0].equals(accounts[0]));
        Object obj = accounts[0];
        if (obj instanceof Account) {
            System.out.println("\nobj is an Account object.");
        }
        Customer copy = customer1.clone();
        System.out.println("\nCustomer Clone");
        System.out.println("Original ID : " + customer1.getCustomerId());
        System.out.println("Cloned ID   : " + copy.getCustomerId());
        System.out.println("\nCustomer Address");
        System.out.println("City : " +
                customer1.getAddress().getCity());
        System.out.println("Pincode : " +
                customer1.getAddress().getPincode());
    }
}