import java.util.Scanner;

/**
 * Created by Evie on 15/05/2017.
 */

public class Boundary {
    
    private CustomerCollection customers;

    private void displayDepositMoneyPage(Customer customer) {
        // TO BE REMOVED IN THE END, to be replaced with a menu list of account types:
        System.out.println("MonBank currently only supports making deposits to a savings account.");


        // menu option of account type: savings
        SavingsAccount savingsOfCustomer = (SavingsAccount)
                Controller.findAccountOfCustomerByType(MonBankConstants.SAVINGS, customer);
        System.out.print("Please enter an amount to deposit: ");

        // make sure the input amount can be parsed to double
        double amountToDeposit = 0;
        Boolean isAmountDouble = false;
        while (!isAmountDouble) {
            String amountString = getUserInput();
            try {
                amountToDeposit = Double.parseDouble(amountString);
                isAmountDouble = true;
            }
            catch (NumberFormatException e) {
                System.out.print("Invalid amount. Please try again: ");
            }
        }

        // validate amount
        Boolean isAmountValid = Controller.validateAmount(
                savingsOfCustomer,
                MonBankConstants.DEPOSIT,
                amountToDeposit);
        if (isAmountValid) {
            String depositResult = Controller.depositToSavings(amountToDeposit, customer);

            if (depositResult.equals(MonBankConstants.SUCCEEDED_DEPOSIT_SAVINGS)) {
                displayTransactionMessage(MonBankConstants.SUCCEEDED_DEPOSIT_SAVINGS);
            }
            else if (depositResult.equals(MonBankConstants.FAILED_ACCOUNT_DOES_NOT_EXIST)){
                displayTransactionError(MonBankConstants.FAILED_ACCOUNT_DOES_NOT_EXIST);
            }
        }
        else {
            displayTransactionError(MonBankConstants.FAILED_AMOUNT_OUTSIDE_LIMIT_RANGE);
        }

    }

    public void displayLoginPage() {
        customers = new CustomerCollection();

        System.out.print("======= Welcome to MonBank! ========\n" +
                "Please enter your user ID: "
        );

        // ASSUMING the user id belongs to a customer, not an admin !!!

        // get input user id
        String userIdString = getUserInput();
        // check if user id is valid
        int userId = 0;
        Boolean isUserIdValid = false;
        try {
            userId = Integer.parseInt(userIdString);
            isUserIdValid = true;

        }
        catch (NumberFormatException e) {
            isUserIdValid = false;
        }
        // if user id is invalid, go to error msg
        if (!isUserIdValid) {
            displayWrongPasswordUserIDError();
            System.out.println("Could not parse input user id into integer."); // DEGUB
        }
        // else user id is valid, validate user id
        else {

            Boolean doesUserIdExist;
            doesUserIdExist = Controller.validateCustomerExists(userId);
            // if user id does not exist, go to error msg
            if (!doesUserIdExist) {
                displayWrongPasswordUserIDError();
                System.out.println("User id does not exist"); // DEBUG
            }
            // else user id exists, get password input
            else {
                // get input password
                System.out.print("Please enter your password: ");
                String password = getUserInput();
                Customer customer = CustomerCollection.findCustomerById(userId);
                Boolean isPasswordValid = Controller.validatePassword(password, customer);
                // if validate password gets false, go to error msg
                if (!isPasswordValid) {
                    displayWrongPasswordUserIDError();
                    System.out.println("password does not match"); // DEBUG
                }
                // else password correct, check account locked status
                else {
                    Boolean isAccountLocked = Controller.validateAccountStatus(customer);
                    // if locked, display error msg
                    if (isAccountLocked) {
                        System.out.println("Account is locked, please contact Administration");
                    }
                    // else not locked, proceed to home page
                    else {
                        displayCustomerHomePage(customer);
                    }
                }
            }
        }
    }

    private void displayLogoutMessage() {
        System.out.println("You have logged out. Thank you for visiting MonBank.");
    }

    private void displayTransactionError(String errorType) {
        switch (errorType) {
            case MonBankConstants.FAILED_ACCOUNT_DOES_NOT_EXIST:
                System.out.println("Sorry, it seems currently you don't have a savings account.");
                break;
            case MonBankConstants.FAILED_AMOUNT_OUTSIDE_LIMIT_RANGE:
                System.out.println("Sorry, the input amount is outside its limit range.");
                break;
        }
    }

    private void displayTransactionMessage(String successType) {
        switch (successType) {
            case MonBankConstants.SUCCEEDED_DEPOSIT_SAVINGS:
                System.out.println("Good news! The deposit has been made successfully.");
                break;
            case MonBankConstants.SUCCEEDED_WITHDRAW_SAVINGS:
                System.out.println("Good news! The withdrawal has been made successfully.");
                break;
        }
    }

    private void displayCustomerAccountInfo(Customer customer) {
        String personalInfoOfCurrentCustomer = Controller.fetchCustomerPersonalInfo(customer);
        System.out.println(personalInfoOfCurrentCustomer);

        String bankAccountsInfoToDisplay = Controller.fetchCustomerBankAccounts(customer);
        System.out.println(bankAccountsInfoToDisplay);
    }

    private void displayCustomerHomePage(Customer customer) {
        System.out.println("======== Customer Home Page =========");

        displayCustomerAccountInfo(customer);

        Boolean done = false;
        while (!done) {
        
            System.out.print("\n ============= Menu ===============\n" +
                    "(1) Make a deposit\n" +
                    "(2) Make a withdrawal\n" +
                    "(3) See customer account information\n" +
                    "(4) Log out\n" +
                    "Choose an menu option: ");

            int customerOption = 0;

            // make sure the user entered option can be parsed into an integer
            Boolean isOptionValid = false;
            while (!isOptionValid) {
                String customerOptionString = getUserInput();
                try {
                    customerOption = Integer.parseInt(customerOptionString);
                    isOptionValid = true;
                }
                catch (NumberFormatException e) {
                    System.out.print("Invalid option. Please try again: ");
                }

            }

            if (customerOption < 1 ||
                    customerOption > 4) {
                System.out.print("Invalid option. Please try again: ");
            }
            else {
                clearTerminal();

                switch (customerOption) {
                    case 1:
                        displayDepositMoneyPage(customer);
                        break;
                    case 2:
                        displayWithdrawMoneyPage(customer);
                        if (customer.getLockedStatus()) {
                            done = true;
                        }
                        break;
                    case 3:
                        displayCustomerAccountInfo(customer);
                        break;
                    case 4:
                        done = true;
                        displayLogoutMessage();
                        break;
                }
            }
             
        }

        customers.clearDatabase();

    }

    private void displayWithdrawMoneyPage(Customer customer) {
        // TO BE REMOVED IN THE END, to be replaced with a menu list of account types:
        System.out.println("MonBank currently only supports making withdrawals from a savings account.");

        int pinAttempts = 3;
        while (pinAttempts > 0) {

            // validate PIN
            System.out.print("Please enter your PIN: ");
            String pinString = getUserInput();
            if (!Controller.validatePIN(pinString, customer)) {
                System.out.println("Invalid or incorrect PIN.");
                pinAttempts -=1 ;
            }
            else {
                pinAttempts = -1;
                // menu option of account type: savings
                SavingsAccount savingsOfCustomer = (SavingsAccount)
                        Controller.findAccountOfCustomerByType(MonBankConstants.SAVINGS, customer);
                System.out.print("Please enter an amount to withdraw: ");

                // make sure the input amount can be parsed to double
                double amountToWithdraw = 0;
                Boolean isAmountDouble = false;
                while (!isAmountDouble) {
                    String amountString = getUserInput();
                    try {
                        amountToWithdraw = Double.parseDouble(amountString);
                        isAmountDouble = true;
                    }
                    catch (NumberFormatException e) {
                        System.out.print("Invalid amount. Please try again: ");
                    }
                }


                // validate amount
                Boolean isAmountValid = Controller.validateAmount(
                        savingsOfCustomer,
                        MonBankConstants.WITHDRAW,
                        amountToWithdraw);
                if (isAmountValid) {
                    String withdrawResult = Controller.withdraw(MonBankConstants.SAVINGS, amountToWithdraw, customer);

                    if (withdrawResult.equals(MonBankConstants.SUCCEEDED_WITHDRAW_SAVINGS)) {
                        displayTransactionMessage(MonBankConstants.SUCCEEDED_WITHDRAW_SAVINGS);
                    } else if (withdrawResult.equals(MonBankConstants.FAILED_ACCOUNT_DOES_NOT_EXIST)) {
                        displayTransactionError(MonBankConstants.FAILED_ACCOUNT_DOES_NOT_EXIST);
                    }
                } else {
                    displayTransactionError(MonBankConstants.FAILED_AMOUNT_OUTSIDE_LIMIT_RANGE);
                }
            }

            if (pinAttempts == 0) {
                System.out.println("Sorry, you have entered the PIN incorrectly for three times." +
                        "Your account is locked now. ");
                customer.setLockedStatus(true);
            }
        }
    }

    private void displayWrongPasswordUserIDError() {
        System.out.print("Sorry, the user ID or the password is invalid or incorrect. Try again? (y/n)");
        Boolean done = false;
        while (!done) {
            String userChoice = getUserInput();
            if (userChoice.equalsIgnoreCase("y")) {
                done = true;
                clearTerminal();
                displayLoginPage();
            }
            else if (userChoice.equalsIgnoreCase("n")) {
                done = true;
                System.out.println("Thank you for visiting MonBank.");
                CustomerCollection customers = new CustomerCollection();
                customers.clearDatabase();
            }
            else {
                System.out.println("Invalid input. Try again? (y/n)");
            }
        }
   
    }

    private String getUserInput() {
        Scanner reader = new Scanner(System.in);
        return reader.nextLine();
    }
    
    private void clearTerminal() {
        System.out.println("\f");
    }
        

} // Boundary / UI
