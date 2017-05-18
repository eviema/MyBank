import java.util.ArrayList;
import java.util.List;

/**
 * The SavingsAccount contains the amount limits of different types of transactions that
 * can be made with a savings account.
 *
 * @author Yifei Ma
 * @version 2017.05.15
 */

public class SavingsAccount extends BankAccount {

    private static final double LOWER_LIMIT_BALANCE = 0.0;
    private static final double LOWER_LIMIT_DEPOSIT = 1.0;
    private static final double LOWER_LIMIT_TRANSFER = 1.0;
    private static final double LOWER_LIMIT_WITHDRAW = 1.0;
    private static final double UPPER_LIMIT_TRANSFER = 2000.0;

    public SavingsAccount() {
        accountType = MonBankConstants.SAVINGS;
        accountNumber = ++nextAccountNumber;
        balance = 0.0;
        transactionList = new ArrayList<>();
    }

    public SavingsAccount(String newAccType,
                          int newAccNumber,
                          double newBalance,
                          List<Transaction> newTransactionList) {
        newAccType = MonBankConstants.SAVINGS;
        accountType = newAccType;
        accountNumber = newAccNumber;
        balance = newBalance;
        transactionList = newTransactionList;
    }

    public static double getLowerLimitBalance() { return LOWER_LIMIT_BALANCE; }

    public static double getLowerLimitOfDeposit() {
        return LOWER_LIMIT_DEPOSIT;
    }

    public static double getLowerLimitOfTransfer() {
        return LOWER_LIMIT_TRANSFER;
    }

    public static double getLowerLimitOfWithdrawal() {
        return LOWER_LIMIT_WITHDRAW;
    }

    public static double getUpperLimitOfTransfer() { return UPPER_LIMIT_TRANSFER; }

}
