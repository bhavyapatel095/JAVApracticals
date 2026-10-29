package service;

import java.nio.file.Files;
import java.nio.file.Path;
import model.Account;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.SavingsAccount;
import util.ReportGenerator;
import util.StatePersister;
import util.TransactionLog;

public class MiniBank {
    public static void main(String[] args) throws Exception {
        Account[] accounts = {
            new SavingsAccount("Bhavya", 5000, 1000),
            new CurrentAccount("Japan", 10000, 2000),
            new FixedDepositAccount("Shivam", 20000)
        };

        Path stateFile = Path.of("data", "accounts.dat");
        StatePersister.save(accounts, stateFile);

        Account[] loadedAccounts = StatePersister.load(stateFile);

        System.out.println("Reloaded accounts:");
        for (Account account : loadedAccounts) {
            System.out.println(account);
        }

        Path logsFolder = Path.of("logs");
        Files.createDirectories(logsFolder);

        Path transactionFile = logsFolder.resolve("transactions.log");
        TransactionLog.append(transactionFile, "DEPOSIT 500");
        TransactionLog.append(transactionFile, "WITHDRAW 200");
        TransactionLog.append(transactionFile, "DEPOSIT 1000");

        Path reportFile = Path.of("reports", "eod-summary.txt");
        String report = ReportGenerator.generate(logsFolder, reportFile);

        System.out.println("\n" + report);
        System.out.println("Report saved to: " + reportFile.toAbsolutePath());
    }
}