import java.util.regex.Pattern;

public class MiniBank {

    public static void main(String[] args) {
        System.out.println("Mobile tests:");
        System.out.println("Valid:   " + Validator.isValidMobile("9876543210"));
        System.out.println("Invalid: " + Validator.isValidMobile("12345"));

        System.out.println("\nEmail tests:");
        System.out.println("Valid:   " + Validator.isValidEmail("user@example.com"));
        System.out.println("Invalid: " + Validator.isValidEmail("user@"));

        System.out.println("\nPAN tests:");
        System.out.println("Valid:   " + Validator.isValidPan("ABCDE1234F"));
        System.out.println("Invalid: " + Validator.isValidPan("ABC123"));

        System.out.println("\nIFSC tests:");
        System.out.println("Valid:   " + Validator.isValidIfsc("SBIN0001234"));
        System.out.println("Invalid: " + Validator.isValidIfsc("123"));

        System.out.println("\nAmount tests:");
        System.out.println("Valid:   " + Validator.isValidAmount("500"));
        System.out.println("Invalid: " + Validator.isValidAmount("-10"));

        String line = "DEPOSIT AC0001 500";

        try {
            Command command = CommandParser.parse(line);

            System.out.println("\nParsed command:");
            System.out.println("Type: " + command.type());
            System.out.println("Account number: " + command.accountNumber());
            System.out.println("Amount: " + command.amount());

            Account account = new Account("Bhavya", 5000);
            System.out.println("\n" + StatementFormatter.buildStatement(account));
        } catch (IllegalArgumentException e) {
            System.out.println("Command error: " + e.getMessage());
        }
    }
}

class Validator {
    private static final Pattern MOBILE_PATTERN =
            Pattern.compile("^[6-9][0-9]{9}$");

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PAN_PATTERN =
            Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");

    private static final Pattern IFSC_PATTERN =
            Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");

    private static final Pattern AMOUNT_PATTERN =
            Pattern.compile("^[1-9][0-9]*$");

    private Validator() {
    }

    public static boolean isValidMobile(String mobile) {
        return mobile != null && MOBILE_PATTERN.matcher(mobile).matches();
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPan(String pan) {
        return pan != null && PAN_PATTERN.matcher(pan).matches();
    }

    public static boolean isValidIfsc(String ifsc) {
        return ifsc != null && IFSC_PATTERN.matcher(ifsc).matches();
    }

    public static boolean isValidAmount(String amount) {
        if (amount == null || !AMOUNT_PATTERN.matcher(amount).matches()) {
            return false;
        }

        try {
            return Long.parseLong(amount) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER
}

record Command(TransactionType type, String accountNumber, long amount) {
}

class CommandParser {
    private CommandParser() {
    }

    public static Command parse(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("Command cannot be empty.");
        }

        String[] parts = line.trim().split("\\s+");

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Command must have three parts: TYPE ACCOUNT_NUMBER AMOUNT."
            );
        }

        try {
            TransactionType type = TransactionType.valueOf(
                    parts[0].toUpperCase()
            );

            String accountNumber = parts[1];

            if (!accountNumber.matches("AC[0-9]{4}")) {
                throw new IllegalArgumentException("Invalid account number.");
            }

            if (!Validator.isValidAmount(parts[2])) {
                throw new IllegalArgumentException(
                        "Amount must be a positive whole number."
                );
            }

            long amount = Long.parseLong(parts[2]);
            return new Command(type, accountNumber, amount);

        } catch (IllegalArgumentException e) {
            if (e instanceof IllegalArgumentException
                    && e.getMessage() != null
                    && (e.getMessage().startsWith("Invalid")
                    || e.getMessage().startsWith("Amount"))) {
                throw e;
            }

            throw new IllegalArgumentException(
                    "Unknown transaction type: " + parts[0]
            );
        }
    }
}

class StatementFormatter {
    private StatementFormatter() {
    }

    public static String buildStatement(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }

        StringBuilder statement = new StringBuilder();
        statement.append("------ Account Statement ------\n");
        statement.append("Account number: ")
                .append(account.getAccountNumber()).append("\n");
        statement.append("Owner: ")
                .append(account.getOwnerName()).append("\n");
        statement.append("Balance: Rs. ")
                .append(account.getBalance()).append("\n");
        statement.append("Active: ")
                .append(account.isActive()).append("\n");
        statement.append("-------------------------------");

        return statement.toString();
    }
}