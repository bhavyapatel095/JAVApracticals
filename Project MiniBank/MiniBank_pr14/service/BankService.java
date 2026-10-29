package service;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InvalidAmountException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;
import model.Account;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.SavingsAccount;
import util.AnnotationValidator;
import util.ReportGenerator;
import util.StatePersister;
import util.StatementFormatter;
import util.TransactionLog;
import util.Validator;

public class BankService {
    private final Repository<Account> repository = new Repository<>();
    private final ArrayList<String> history = new ArrayList<>();
    private final Path folder;

    public BankService(Path folder) throws IOException, ClassNotFoundException {
        this.folder = folder;
        Files.createDirectories(folder.resolve("logs"));

        Path stateFile = folder.resolve("accounts.dat");
        if (Files.exists(stateFile)) {
            repository.saveAll(StatePersister.load(stateFile));
        }

        Path logFile = folder.resolve("logs").resolve("daily.log");
        if (Files.exists(logFile)) {
            history.addAll(Files.readAllLines(logFile));
        }
    }

    public synchronized void registerAccount(Account account) {
        repository.save(account);
    }

    private Account require(String accountNumber)
            throws AccountNotFoundException {
        Account account = repository.findById(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException(
                    "Account not found: " + accountNumber
            );
        }

        return account;
    }

    private synchronized void record(
            String type,
            String accountNumber,
            long amount) throws IOException {
        String line = type + " " + accountNumber + " " + amount;
        TransactionLog.append(
                folder.resolve("logs").resolve("daily.log"),
                line
        );
        history.add(line);
    }

    public synchronized Account openAccount(
            String type,
            String name,
            String email,
            String mobile,
            String pan,
            String ifsc,
            long openingBalance) throws BankException {

        if (name == null || name.isBlank() || name.length() > 20
                || !Validator.isValidEmail(email)
                || !Validator.isValidMobile(mobile)
                || !Validator.isValidPan(pan)
                || !Validator.isValidIfsc(ifsc)) {
            throw new BankException("Invalid customer details.");
        }

        if (openingBalance <= 0) {
            throw new InvalidAmountException(
                    "Opening balance must be greater than zero."
            );
        }

        Account account;

        switch (type.trim().toUpperCase()) {
            case "SAVINGS" ->
                account = new SavingsAccount(name, openingBalance, 0);
            case "CURRENT" ->
                account = new CurrentAccount(name, openingBalance, 1000);
            case "FD" ->
                account = new FixedDepositAccount(name, openingBalance);
            default ->
                throw new BankException(
                        "Account type must be SAVINGS, CURRENT, or FD."
                );
        }

        String[] errors = AnnotationValidator.validate(account);
        if (errors.length > 0) {
            throw new BankException(String.join(", ", errors));
        }

        // Generate another number if a loaded account already has this ID.
        while (repository.findById(account.getAccountNumber()) != null) {
            switch (type.trim().toUpperCase()) {
                case "SAVINGS" ->
                    account = new SavingsAccount(name, openingBalance, 0);
                case "CURRENT" ->
                    account = new CurrentAccount(name, openingBalance, 1000);
                case "FD" ->
                    account = new FixedDepositAccount(name, openingBalance);
                default -> throw new BankException("Invalid account type.");
            }
        }

        repository.save(account);
        return account;
    }

    public synchronized void deposit(String id, long amount)
            throws BankException, IOException {
        require(id).deposit(amount);
        record("DEPOSIT", id, amount);
    }

    public synchronized void withdraw(String id, long amount)
            throws BankException, IOException {
        require(id).withdraw(amount);
        record("WITHDRAW", id, amount);
    }

    public synchronized void transfer(String from, String to, long amount)
            throws BankException, IOException {
        Account source = require(from);
        Account target = require(to);

        source.transfer(target, amount);
        record("WITHDRAW", from, amount);
        record("DEPOSIT", to, amount);
    }

    public synchronized String statement(String id) throws BankException {
        StringBuilder result =
                new StringBuilder(StatementFormatter.buildStatement(require(id)));

        result.append("\nTransactions\n");
        for (String line : history) {
            String[] parts = line.split("\\s+");
            if (parts.length == 3
                    && (parts[1].equals(id) || parts[2].equals(id))) {
                result.append(line).append("\n");
            }
        }

        return result.toString();
    }

    public synchronized ArrayList<Account> listAccounts() {
        ArrayList<Account> accounts = repository.findAll();
        Collections.sort(accounts);
        return accounts;
    }

    public synchronized long balance(String id) throws BankException {
        return require(id).getBalance();
    }

    public synchronized void save() throws IOException {
        StatePersister.save(
                repository.findAll().toArray(new Account[0]),
                folder.resolve("accounts.dat")
        );
    }

    public synchronized String report() throws IOException {
        return ReportGenerator.generate(
                folder.resolve("logs"),
                folder.resolve("report.txt")
        );
    }

    public void batchDeposit(String id, int tasks, long amount)
            throws Exception {
        if (tasks <= 0 || amount <= 0) {
            throw new InvalidAmountException(
                    "Task count and deposit amount must be positive."
            );
        }

        require(id);

        TransactionProcessor processor = new TransactionProcessor();

        for (Account account : listAccounts()) {
            processor.addAccount(account);
        }

        if (processor.getAccount(id) == null) {
            processor.stop();
            throw new AccountNotFoundException("Account not found: " + id);
        }

        ConcurrentLinkedQueue<Exception> errors =
                new ConcurrentLinkedQueue<>();

        for (int i = 0; i < tasks; i++) {
            processor.submit(() -> {
                try {
                    Account account = processor.getAccount(id);
                    account.deposit(amount);
                    record("DEPOSIT", id, amount);
                } catch (InvalidAmountException | IOException e) {
                    errors.add(e);
                }
            });
        }

        processor.stop();

        if (!errors.isEmpty()) {
            throw errors.peek();
        }
    }
}
