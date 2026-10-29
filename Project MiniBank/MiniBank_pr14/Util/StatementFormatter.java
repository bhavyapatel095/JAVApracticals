package util;

import model.Account;

public class StatementFormatter {
    private StatementFormatter() {
    }

    public static String buildStatement(Account account) {
        return "------ MiniBank Statement ------\n"
                + "Account number: " + account.getAccountNumber() + "\n"
                + "Owner: " + account.getOwnerName() + "\n"
                + "Balance: Rs. " + account.getBalance() + "\n"
                + "Active: " + account.isActive() + "\n"
                + "--------------------------------";
    }
}