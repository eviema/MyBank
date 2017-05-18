import java.util.*;
import java.io.*;
/**
 * The IO class is responsible for reading from the data file that stores information
 * of all customers.
 *
 * @author Jie Wang, Yifei Ma
 * @version 2017.05.15
 */
public class IO {

    private String dataFile;
    private List<Customer> customerList;
    private List<BankAccount> bankAccountList;

    
    public IO() {
        dataFile = "Data.txt";
        customerList = new ArrayList<>();
    }

    public List<Customer> readData() {

        try {

            FileReader reader = new FileReader(dataFile);
            Scanner input = new Scanner(reader);

            while(input.hasNext()) {
               
                String[] temp = input.nextLine().split(",");
                String name = temp[0];
                String email = temp[1];
                String homeAddress = temp[2];
                String lockedStatus = temp[3];
                String phone = temp[4];
                String id = temp[5];
                String password = temp[6];

                Customer customer = null;
                boolean customerAccountState = false;
                if(lockedStatus.equalsIgnoreCase("locked")) {
                    customerAccountState = true;
                }
                else if(lockedStatus.equalsIgnoreCase("unlocked")) {
                    customerAccountState = false;
                }

                String pin = "";

                // customer has not yet opened an account
                if (temp.length == 8) {
                    pin = temp[7];
                    customer = new Customer(
                            password,
                            name,
                            email,
                            homeAddress,
                            Integer.parseInt(phone),
                            Integer.parseInt(pin));
                }
                // customer has at least one account
                else {
                    String accountType = "";
                    String accountBalance = "";
                    String accountNumber = "";

                    List<BankAccount> listOfBankAccounts = new ArrayList<BankAccount>();
                    int accountStartAtIndex = 7;
                    Boolean continueReadingAccounts = true;
                    while (continueReadingAccounts) {
                        accountType = temp[accountStartAtIndex];
                        accountBalance = temp[accountStartAtIndex + 1];
                        accountNumber = temp[accountStartAtIndex + 2];

                        BankAccount bankAccount = new SavingsAccount(
                                accountType,
                                Integer.parseInt(accountNumber),
                                Double.parseDouble(accountBalance),
                                new ArrayList<Transaction>()
                        );

                        listOfBankAccounts.add(bankAccount);

                        if (accountStartAtIndex + 3 == temp.length - 1) {
                            continueReadingAccounts = false;
                            pin = temp[accountStartAtIndex + 3];
                        }
                        else {
                            accountStartAtIndex = accountStartAtIndex + 3;
                        }
                    }

                    customer = new Customer(
                            password,
                            name,
                            email,
                            homeAddress,
                            Integer.parseInt(phone),
                            Integer.parseInt(pin));
                    customer.setBankAccounts(listOfBankAccounts);
                }

                customer.setLockedStatus(customerAccountState);
                customer.setUserID(Integer.parseInt(id));
                customerList.add(customer);
            }

            reader.close();
        }

        catch(FileNotFoundException e) {
            System.err.println("File " +  dataFile + " not found");
        }
        catch(IOException e) {
            System.err.println("Unexpected I/O error occurred.");
        }
        catch (NumberFormatException e) {
            System.err.println("Unexpected error while reading customer data");
        }

        return customerList;  

    }
    
    public static void writeFile(List<Customer> customers) {
        File filename = new File("Data.txt");
        try {
            PrintWriter outputFile = new PrintWriter(filename);
            for (Customer customer: customers)
            {
               outputFile.println(Controller.displayCustomer(customer));
            }
            outputFile.close();
        }
        catch (IOException e) {
          System.out.println("Unexpected I/O exception Occured!");
       }
    }   

} // IO
