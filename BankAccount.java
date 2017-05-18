import java.util.ArrayList;
import java.util.List;

/**
 * The BankAccount class contains common attributes and methods
 * for different types of bank accounts.
 *
 * @author Yifei Ma
 * @version 2017.05.14
 */

public class BankAccount {

    protected static int nextAccountNumber = 50000000;
    protected int accountNumber;
    protected String accountType;
    protected double balance;
    protected List<Transaction> transactionList;

    public BankAccount() {
        accountNumber = ++nextAccountNumber;
        accountType = "";
        balance = 0;
        transactionList = new ArrayList<>();
    }

    public BankAccount(String accType, double accBalance, int accNumber) {
        accountNumber = ++nextAccountNumber;
        accountType = accType;
        balance = accBalance;
        transactionList = new ArrayList<>();
    }

    public int getAccountNumber() { return accountNumber; }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setAccountNumber(int newAccNumber) { accountNumber = newAccNumber; }

    public void setAccountType(String newAccType) { accountType = newAccType; }

    public void setBalance(double newBalance) {
        balance = newBalance;
    }

    public void setTransactionList(List<Transaction> newTransactionList) {
        transactionList = newTransactionList;
    }
}
