package service;

import model.Account;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.Premium;
import model.SavingsAccount;
import model.WithdrawRule;
import util.AnnotationValidator;

import static util.Validator.isValidMobile;

public class MiniBank {
    public static void main(String[] args) {
        Account[] accounts = {
            new SavingsAccount("Bhavya", 5000, 1000),
            new CurrentAccount("Japan", 10000, 2000),
            new FixedDepositAccount("Shivam", 20000)
        };

        System.out.println("MiniBank Accounts");
        System.out.println("=================");

        for (Account account : accounts) {
            System.out.println(account);
            System.out.println("Interest rate: " + account.interestRate() + "%");
            System.out.println("Yearly interest: Rs. "
                    + account.yearlyInterest());

            if (account instanceof SavingsAccount savings) {
                System.out.println("Minimum balance: Rs. "
                        + savings.getMinBalance());
            }

            System.out.println();
        }

        WithdrawRule anonymousRule = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return account.canWithdraw(amount);
            }
        };

        WithdrawRule lambdaRule =
                (account, amount) -> account.canWithdraw(amount);

        System.out.println("Anonymous rule allows withdrawal: "
                + anonymousRule.allow(accounts[0], 1000));
        System.out.println("Lambda rule allows withdrawal: "
                + lambdaRule.allow(accounts[0], 1000));

        System.out.println("Bhavya has Premium status: "
                + (accounts[0] instanceof Premium));

        System.out.println("Mobile number valid: "
                + isValidMobile("9876543210"));

        Account invalidAccount =
                new FixedDepositAccount("Test Customer", -500);

        System.out.println("\nValidation errors:");
        String[] errors = AnnotationValidator.validate(invalidAccount);

        if (errors.length == 0) {
            System.out.println("No errors found.");
        } else {
            for (String error : errors) {
                System.out.println(error);
            }
        }
    }
}
