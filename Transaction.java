import java.time.LocalDate;

/**
 * The Transaction class acts as a template for a bank transaction in MonBank.
 * A method of displaying the details of a transaction is also included.
 *
 * @author Yifei Ma
 * @version 2017.05.15
 */

public class Transaction {

    private BankAccount accountTransactionFrom;
    private BankAccount accountTransactionTo;
    private double amountOfTransaction;
    private LocalDate dateOfTransaction;
    private static int nextTransactionNumber = 10000000;
    private int transactionNumber;
    private String transactionType;

    /**
     * Default constructor to create a new transaction object.
     */
    public Transaction() {
        accountTransactionFrom = null;
        accountTransactionTo = null;
        amountOfTransaction = 0.0;
        dateOfTransaction = LocalDate.now();
        transactionNumber = ++nextTransactionNumber;
        transactionType = null;
    }


    /**
     * Constructor to create a new transaction object, for transactions where only one
     * bank account is involved.
     *
     * @param transactionFrom
     * @param transactionAmount
     * @param typeOfTransaction
     */
    public Transaction(BankAccount transactionFrom,
                       double transactionAmount,
                       String typeOfTransaction) {

        accountTransactionFrom = transactionFrom;
        accountTransactionTo = null;
        amountOfTransaction = transactionAmount;
        dateOfTransaction = LocalDate.now();
        transactionNumber = ++nextTransactionNumber;
        transactionType = typeOfTransaction;
    }

    /**
     * Constructor to create a new transaction object, for transactions where two
     * bank accounts are involved.
     *
     * @param transactionFrom
     * @param transactionTo
     * @param transactionAmount
     * @param typeOfTransaction
     */
    public Transaction(BankAccount transactionFrom,
                       BankAccount transactionTo,
                       double transactionAmount,
                       String typeOfTransaction) {

        accountTransactionFrom = transactionFrom;
        accountTransactionTo = transactionTo;
        amountOfTransaction = transactionAmount;
        dateOfTransaction = LocalDate.now();
        transactionNumber = ++nextTransactionNumber;
        transactionType = typeOfTransaction;
    }

    public void displayTransactionDetails() {
        System.out.println("Transaction Number: " + transactionNumber);
        System.out.println("Transaction Date: " + dateOfTransaction.toString());
        System.out.println("Transaction Type: " + transactionType);
        System.out.println("From Account number: " + accountTransactionFrom.getAccountNumber());
        if (accountTransactionTo == null) {
            System.out.println("To Account Number: N/A");
        }
        else {
            System.out.println("To Account Number: " + accountTransactionTo.getAccountNumber());
        }
        System.out.println("Transaction Amount: " + amountOfTransaction);
    }

    public BankAccount getAccountTransactionFrom() {
        return accountTransactionFrom;
    }

    public BankAccount getAccountTransactionTo() {
        return accountTransactionTo;
    }

    public double getAmountOfTransaction() {
        return amountOfTransaction;
    }

    public LocalDate getDateOfTransaction() {
        return dateOfTransaction;
    }

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setAccountTransactionFrom(BankAccount newAccFrom) {
        accountTransactionFrom = newAccFrom;
    }

    public void setAccountTransactionTo(BankAccount newAccTo) {
        accountTransactionTo = newAccTo;
    }

    public void setAmountOfTransaction(double newAmount) {
        amountOfTransaction = newAmount;
    }

    public void setDateOfTransaction(LocalDate newDate) {
        dateOfTransaction = newDate;
    }

    public void setTransactionID(int newNumber) {
        transactionNumber = newNumber;
    }

    public void setTransactionType(String newType) {
        transactionType = newType;
    }
}
