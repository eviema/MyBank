import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

/**
 * The Controller class is responsible for performing all banking-related activities and
 * validation of user inputs.
 *
 * @author Yifei Ma, Jie Wang
 * @version 2017.05.15
 */

public class Controller {

    private Customer currentCustomer;
    private BankAccount currentBankAccount;


    /**
     * Default constructor to create a new Controller object with null values.
     *
     */
    public Controller() {
        currentCustomer = null;
        currentBankAccount = null;
    }
    /**
     * Constructor to create a new Controller object.
     *
     * @param customer The current customer object
     * @param bankAccount The current bank account of the current customer
     */
    public Controller(Customer customer, BankAccount bankAccount) {
        currentCustomer = customer;
        currentBankAccount = bankAccount;
    }
    /**
     *Create a new customer.
     *
     * @return newCustomer   A new customer object just created
     */
    public Customer createNewCustomer(String password,
                                      String fullName,
                                      String emailAdd,
                                      String homeAdd,
                                      String phoneNum,
                                      int PIN) {

        int phone = Integer.parseInt(phoneNum);
        return new Customer(
                password,
                fullName,
                emailAdd,
                homeAdd,
                phone,
                PIN);
    }

    public static String depositToCreditCard() {
        String result = "";

        return result;
    }

    /**
     * Deposit to the current savings account.
     *
     * @param amount     An amount entered by the user to deposit
     * @return result    A string indicating whether the deposit was successful
     */
    public static String depositToSavings(double amount, Customer customer) {
        String result = "";

        SavingsAccount currentSavingsAccount = null;

        currentSavingsAccount = (SavingsAccount) findAccountOfCustomerByType(
                MonBankConstants.SAVINGS, customer);

        if (currentSavingsAccount == null) {
            result = MonBankConstants.FAILED_ACCOUNT_DOES_NOT_EXIST;
        }
        else {
            // make a deposit
            double newBalance = currentSavingsAccount.getBalance() + amount;

            // update balance
            currentSavingsAccount.setBalance(newBalance);

            // create a new transaction record
            Transaction newTransaction = new Transaction(currentSavingsAccount,
                    amount,
                    MonBankConstants.DEPOSIT);

            // update the transaction list of the current account
            List<Transaction> transactionListOfCurrentAccount = currentSavingsAccount.getTransactionList();
            transactionListOfCurrentAccount.add(newTransaction);
            currentSavingsAccount.setTransactionList(transactionListOfCurrentAccount);

            result = MonBankConstants.SUCCEEDED_DEPOSIT_SAVINGS;
        }
        return result;
    }

    /**
     * Deposit to the current term deposit account.
     *
     * @param amount   An amount entered by the user to deposit
     * @param periodInMonths  An integer number of the months of deposit
     * @return result  A string indicating whether the deposit was successful
     */
    public String depositToTermDeposit(double amount, int periodInMonths) {
        String result = "";

        // cast currentBankAccount to its actual subclass, in order to use subclass methods on it
        TermDepositAccount currentTermDepositAccount = (TermDepositAccount) currentBankAccount;

        // make a term deposit
        double newBalance = currentBankAccount.getBalance() + amount;
        currentTermDepositAccount.setBalance(newBalance);
        currentTermDepositAccount.setTermPeriodInMonths(periodInMonths);
        LocalDate newAgreedWithdrawalDate = LocalDate.now().plusMonths(periodInMonths);
        currentTermDepositAccount.setAgreedWithdrawalDate(newAgreedWithdrawalDate);
        switch (periodInMonths) {
            case 12:
                currentTermDepositAccount.setInterestRate(0.05);
                break;
            case 6:
                currentTermDepositAccount.setInterestRate(0.04);
                break;
            case 3:
                currentTermDepositAccount.setInterestRate(0.03);
                break;
        }

        // redirecting object reference back to the instance variable
        currentBankAccount = currentTermDepositAccount;

        // create a new transaction record
        Transaction newTransaction = new Transaction(currentBankAccount,
                amount,
                MonBankConstants.TERM_DEPOSIT);

        // update the transaction list of the current account
        List<Transaction> transactionListOfCurrentAccount = currentBankAccount.getTransactionList();
        transactionListOfCurrentAccount.add(newTransaction);
        currentBankAccount.setTransactionList(transactionListOfCurrentAccount);

        result = MonBankConstants.SUCCEEDED;
        return result;
    }

    /**
     * Fetch the list of bank accounts of the current customer, and return a string of basic info
     * of all the bank accounts prepared to display on the login page.
     *
     * @param customer The customer object for which bank accounts info is required
     * @return bankAccountsInfoToDisplay  A string containing all bank accounts info
     *                                    of the given customer
     */
    public static String fetchCustomerBankAccounts(Customer customer) {
        List<BankAccount> listOfBankAccountsToDisplay = new ArrayList<BankAccount>();
        listOfBankAccountsToDisplay = customer.getBankAccounts();

        String bankAccountsInfoToDisplay = "";
        StringBuilder strBuilder = new StringBuilder();

        for (BankAccount account : listOfBankAccountsToDisplay) {
            strBuilder.append("Account Number: ").append(account.getAccountNumber()).append("\n");
            strBuilder.append("Account Type: ").append(account.getAccountType()).append("\n");
            strBuilder.append("Account Balance: ").append(account.getBalance()).append("\n\n");

        }

        bankAccountsInfoToDisplay = strBuilder.toString();
        return bankAccountsInfoToDisplay; // new line separated
    }

    /**
     * Fetch from the customer database the personal information of the current customer
     * to display on the user home page.
     *
     * @param customer The customer object for which personal info is required
     * @return personalInfoOfCurrentCustomer  A string containing all personal info
     *                                        of the given customer
     */
    public static String fetchCustomerPersonalInfo(Customer customer) {
        String personalInfoOfCurrentCustomer = "";

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Customer Full Name: ").append(customer.getFullName()).append("\n");
        strBuilder.append("Customer Email Address: ").append(customer.getEmailAdd()).append("\n");
        strBuilder.append("Customer Home Address: ").append(customer.getHomeAdd()).append("\n");
        strBuilder.append("Customer Phone Number: ").append(customer.getPhoneNumber()).append("\n");
        strBuilder.append("Customer PIN Number: ").append(customer.getPIN()).append("\n");

        personalInfoOfCurrentCustomer = strBuilder.toString();
        return personalInfoOfCurrentCustomer; // new line separated
    }
     public static String displayCustomer(Customer customer) {
         List<BankAccount> listOfBankAccounts = new ArrayList<BankAccount>();
         listOfBankAccounts = customer.getBankAccounts();

         StringBuffer strBuffer = new StringBuffer();
         strBuffer.append(customer.getFullName()).append(",");
         strBuffer.append(customer.getEmailAdd()).append(",");
         strBuffer.append(customer.getHomeAdd()).append(",");
         strBuffer.append(customer.getLockedStatus()).append(",");
         strBuffer.append(customer.getPhoneNumber()).append(",");
         strBuffer.append(customer.getUserID()).append(",");
         strBuffer.append(customer.getPassword()).append(",");

         BankAccount account = null;
         for(int i = 0; i < listOfBankAccounts.size(); i++){
             account = listOfBankAccounts.get(i);
             if (account != null) {
                 strBuffer.append(account.getAccountType()).append(",");
                 strBuffer.append(account.getBalance()).append(",");
                 strBuffer.append(account.getAccountNumber()).append(",");
             }
         }

         strBuffer.append(customer.getPIN());
         return strBuffer.toString();

    }

    public static BankAccount findAccountOfCustomerByType(String accType, Customer customer) {
        List<BankAccount> bankAccountsOfCustomer = customer.getBankAccounts();
        BankAccount targetAccount = null;
        for (BankAccount account : bankAccountsOfCustomer) {
            String accountType = account.getAccountType();
            if (accountType.equals(accType)) {
                targetAccount = account;
            }
        }
        return targetAccount;
    }

    /**
     * Make a repayment to the account with the input account number.
     *
     * @param accNumber
     * @param amount
     * @return
     */
    public String makeRepayment(int accNumber, double amount) {
        String result = "";

        return result;
    }

    /**
     * Take a loan from a bank account, either credit card loan or home loan.
     *
     * @param accNumber
     * @param amount
     * @return
     */
    public String takeLoan(int accNumber, double amount) {
        String result = "";

        return result;
    }

    /**
     * Transfer money from one account to another of the current customer.
     *
     * @param accountTransferTo  The bank account to which money is transferred
     * @param amount  The amount of money transferred
     * @return result    A string indicating whether the deposit was successful
     */
    public String transfer(BankAccount accountTransferTo, double amount) {
        String result = "";

        // make a transfer
        double newBalanceOfAccountFrom = currentBankAccount.getBalance() - amount;
        double newBalanceOfAccountTo = accountTransferTo.getBalance() + amount;

        // update balance
        currentBankAccount.setBalance(newBalanceOfAccountFrom);
        accountTransferTo.setBalance(newBalanceOfAccountTo);

        // create a new transaction record
        Transaction newTransaction = new Transaction(
                currentBankAccount,
                accountTransferTo,
                amount,
                MonBankConstants.TRANSFER);

        // update the transaction list of each account
        List<Transaction> transactionListOfCurrentAccount = currentBankAccount.getTransactionList();
        transactionListOfCurrentAccount.add(newTransaction);
        currentBankAccount.setTransactionList(transactionListOfCurrentAccount);

        List<Transaction> transactionListOfAccountTransferTo = accountTransferTo.getTransactionList();
        transactionListOfAccountTransferTo.add(newTransaction);
        accountTransferTo.setTransactionList(transactionListOfAccountTransferTo);

        result = MonBankConstants.SUCCEEDED;
        return result;
    }

    /**
     * Validate the customer accounts status, either locked / cannot be used or not locked / can
     * be used.
     *
     * @param customer  An input customer object for which the locked status is checked
     * @return notLockedCanBeUsed  A boolean result of whether the customer account is locked
     */
    public static Boolean validateAccountStatus(Customer customer) {
        Boolean isLocked;

        Boolean isCustomerLocked = customer.getLockedStatus();

        if (isCustomerLocked) {
            isLocked = true;
        }
        else {
            isLocked = false;
        }
        return isLocked;
    }

    /**
     * Validate the amount entered by the customer against the limits of
     * different account types.
     *
     * @param transactionType
     * @param amount
     * @return
     */
    public static Boolean validateAmount(BankAccount account, String transactionType, double amount) {
        Boolean isAmountValid = null;

        double lowerLimit = 0.0;
        double upperLimit = 0.0;

        String accType = account.getAccountType();

        // determine the lower and upper limits of the input amount
        // based on account type and transaction type
        switch (accType) {

            case MonBankConstants.SAVINGS:
                switch (transactionType) {
                    case MonBankConstants.DEPOSIT:
                        lowerLimit = SavingsAccount.getLowerLimitOfDeposit();
                        upperLimit = Double.POSITIVE_INFINITY;
                        break;
                    case MonBankConstants.WITHDRAW:
                        lowerLimit = SavingsAccount.getLowerLimitOfWithdrawal();
                        upperLimit = account.getBalance();
                        break;
                    case MonBankConstants.TRANSFER:
                        // currentBankAccount is the account FROM which a transfer is made.
                        lowerLimit = SavingsAccount.getLowerLimitOfTransfer();
                        upperLimit = min(SavingsAccount.getUpperLimitOfTransfer(),
                                account.getBalance());
                        break;
                }
                break;

            case MonBankConstants.TERM_DEPOSIT:
                switch (transactionType) {
                    case MonBankConstants.DEPOSIT:
                        lowerLimit = TermDepositAccount.getLowerLimitOfDeposit();
                        upperLimit = Double.POSITIVE_INFINITY;
                        break;
                    case MonBankConstants.WITHDRAW:
                        lowerLimit = account.getBalance();
                        upperLimit = account.getBalance();
                        break;
                }
                break;

            // TODO : validate amount for credit card account related banking activities.
            case MonBankConstants.CREDIT_CARD:
                switch (transactionType) {
                    case MonBankConstants.DEPOSIT:
                        break;
                    case MonBankConstants.WITHDRAW:
                        break;
                    case MonBankConstants.TRANSFER:
                        break;
                    case MonBankConstants.TAKE_LOAN:
                        break;
                    case MonBankConstants.MODIFY_DAILY_LIMIT:
                        break;
                }

                break;

            // TODO : validate amount for home loan account related banking activities.
            case MonBankConstants.HOME_LOAN:
                switch (transactionType) {
                    case MonBankConstants.TAKE_LOAN:
                        break;
                    case MonBankConstants.MAKE_REPAYMENT:
                        break;
                }
                break;
        }

        // compare the input amount with its lower and upper limits
        if (amount >= lowerLimit &&
                amount <= upperLimit) {
            isAmountValid = true;
        }
        else {
            isAmountValid = false;
        }

        return isAmountValid;
    }

    /**
     * Validate the email address entered by the user.
     *
     * @param email  The email address string entered of the customer
     * @return isEmailValid  A boolean result indicating whether the input email address is valid
     */
    public Boolean validateEmail(String email) {
        Boolean isEmailValid = null;

        if (email == null ||
                email.trim().equals("") ||
                !email.matches("^(.+)@(.+)$")) {
            isEmailValid = false;
        }
        else {
            isEmailValid = true;

        }
        return isEmailValid;
    }

    /**
     * Validate the password entered by the current customer. Only returns true if the input password is
     * valid (true to password rules) and correct (matching the actual password of the customer).
     *
     * @param password  The password string input by the customer
     * @return isPasswordValid  A boolean result of whether the input password is valid & correct
     */
    public static Boolean validatePassword(String password, Customer customer) {
        Boolean isPasswordValid = null;

        // the regular expression below makes sure that string contains at least:
        // - one lower case letter
        // - one upper case letter
        // - one digit
        // - one non-word character / special character
        if (password.length() != 8 ||
                !password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*(_|[^\\w])).+$")) {
            return false;
        }

        String actualPasswordOfCustomer = customer.getPassword();
        if (password.equals(actualPasswordOfCustomer)) {
            isPasswordValid = true;
        }
        else {
            isPasswordValid = false;
        }
        return isPasswordValid;
    }

    /**
     * Validate the phone number entered by the user. Only returns true if it contains only
     * numbers and is 10-digit long.
     *
     * @param phone  A phone number string input by the user
     * @return isPhoneValid  A boolean result indicating whether the input phone number is valid
     */
    public Boolean validatePhone(String phone) {
        Boolean isPhoneValid = null;

        if (phone.matches("[0-9+]") &&
                phone.length() == 10) {
            isPhoneValid = true;
        }
        else {
            isPhoneValid = false;
        }
        return isPhoneValid;
    }

    /**
     * Validate the PIN entered by the current customer. Only returns true if the input PIN is
     * valid (true to PIN rules) and correct (matching the actual PIN of the customer).
     *
     * @param PIN   A PIN string input by the user
     * @return isPINValid   A boolean result of whether the input PIN is valid & correct
     */
    public static Boolean validatePIN(String PIN, Customer customer) {
        Boolean isPINValid = null;

       if (!PIN.matches("[0-9]+") ||  //minor bug was [0-9+]
                PIN.length() != 6) {
            return false;
        }
      

        int actualPinOfCustomer = customer.getPIN();
        String actualPinString = Integer.toString(actualPinOfCustomer);
        if (PIN.equals(actualPinString)) {
            isPINValid = true;
        }
        else {
            isPINValid = false;
        }
    
        return isPINValid;
    }

    /**
     * Validate the existence of a customer by the input user ID.
     *
     * @param userID  An integer input id used to find the customer
     * @return doesUserExist  A boolean result of whether the customer exists in the collection
     */
    public static Boolean validateCustomerExists(int userID) {
        Boolean doesCustomerExist = false;

        if (CustomerCollection.findCustomerById(userID) == null) {
            doesCustomerExist = false;
        }
        else {
            doesCustomerExist = true;
        }
        return doesCustomerExist;
    }

    /**
     * Withdraw money from an account.
     *
     * @param amount  An amount of money to withdraw
     * @return result A boolean result indicating whether the withdrawal is successful
     */
    public static String withdraw(String accType, double amount, Customer customer) {
        String result = "";

        BankAccount currentAccount = null;
        switch (accType) {
            case MonBankConstants.SAVINGS:
                currentAccount = (SavingsAccount)
                    findAccountOfCustomerByType(accType, customer);
                break;
        }

        if (currentAccount == null) {
            result = MonBankConstants.FAILED_ACCOUNT_DOES_NOT_EXIST;
        }
        else {
            // make a withdrawal
            double newBalance = currentAccount.getBalance() - amount;

            // update balance
            currentAccount.setBalance(newBalance);

            // create a new transaction record
            Transaction newTransaction = new Transaction(currentAccount,
                    amount,
                    MonBankConstants.WITHDRAW);

            // update the transaction list of the current account
            List<Transaction> transactionListOfCurrentAccount = currentAccount.getTransactionList();
            transactionListOfCurrentAccount.add(newTransaction);
            currentAccount.setTransactionList(transactionListOfCurrentAccount);

            result = MonBankConstants.SUCCEEDED_WITHDRAW_SAVINGS;

        }
        return result;
    }
}
    

