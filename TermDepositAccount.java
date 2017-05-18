import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The TermDepositAccount class acts as a template for all term deposit account objects.
 *
 * @author Yifei Ma
 * @version 2017.05.15
 */

public class TermDepositAccount extends BankAccount {

    private static final double LOWER_LIMIT_DEPOSIT = 1.0;
    private LocalDate agreedWithdrawalDate;
    private double interestRate;
    private int termPeriodInMonths;

    public TermDepositAccount() {
        accountType = MonBankConstants.TERM_DEPOSIT;
        accountNumber = ++nextAccountNumber;
        balance = 0.0;
        transactionList = new ArrayList<>();
        agreedWithdrawalDate = null;
        interestRate = 0.0;
        termPeriodInMonths = 0;
    }

    public TermDepositAccount(int numberOfMonths) {
        accountType = MonBankConstants.TERM_DEPOSIT;
        accountNumber = ++nextAccountNumber;
        balance = 0.0;
        transactionList = new ArrayList<>();
        termPeriodInMonths = numberOfMonths;
        agreedWithdrawalDate = LocalDate.now().plusMonths(numberOfMonths);
        switch (numberOfMonths) {
            case 12:
                interestRate = 0.05;
                break;
            case 6:
                interestRate = 0.04;
                break;
            case 3:
                interestRate = 0.03;
                break;
        }
    }

    public static double getLowerLimitOfDeposit() {
        return LOWER_LIMIT_DEPOSIT;
    }

    public LocalDate getAgreedWithdrawalDate() {
        return agreedWithdrawalDate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getTermPeriodInMonths() {
        return termPeriodInMonths;
    }

    public void setAgreedWithdrawalDate(LocalDate newDate) {
        agreedWithdrawalDate = newDate;
    }

    public void setInterestRate(double newRate) {
        interestRate = newRate;
    }

    public void setTermPeriodInMonths(int newNumberOfMonths) {
        termPeriodInMonths = newNumberOfMonths;
    }


}
