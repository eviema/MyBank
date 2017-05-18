import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The CustomerCollection class is responsible for manipulating the customer collection of MonBank.
 *
 * @author Jie Wang, Yifei Ma
 * @version 2017.05.15
 */

public class CustomerCollection {

    private IO io;
    private static List<Customer> listOfCustomers;

    public CustomerCollection() {
        listOfCustomers = new ArrayList<>();
        io = new IO();
        listOfCustomers = io.readData();
    }

    public void addNewCustomer(Customer customer) {
        listOfCustomers.add(customer);
    }

    public void clearDatabase() {
        io.writeFile(listOfCustomers);
        listOfCustomers.clear();
    }

    public static Customer findCustomerById(int id) {
        Customer targetCustomer = null;
        for(int i = 0; i < listOfCustomers.size(); i++) {
            Customer currentCustomer = listOfCustomers.get(i);
            if(currentCustomer.getUserID() == id) {
                targetCustomer = currentCustomer;
                i = listOfCustomers.size();
            }
        }
        return targetCustomer;
    }

    public Customer findCustomerByName(String name) {
        Customer targetCustomer = new Customer();
        for(int i = 0; i < listOfCustomers.size(); i++) {
            Customer currentCustomer = listOfCustomers.get(i);
            if(currentCustomer.getFullName().trim().equalsIgnoreCase(name)) {
                targetCustomer = currentCustomer;
                i = listOfCustomers.size();
            }
        }
        return targetCustomer;
    }

    public int getNumberOfCustomers() {
        return listOfCustomers.size();
    }

    public void removeCustomer(String id) {
        for(int i = 0; i < listOfCustomers.size(); i++) {
            if(listOfCustomers.get(i).getUserID() == (Integer.parseInt(id))) {
                listOfCustomers.remove(i);
                i = listOfCustomers.size();
            }
        }
    }

    public void printAllCustomers() {
        // format for each customer: id + full name
        for (Customer cust : listOfCustomers) {
            int id = cust.getUserID();
            String fullName = cust.getFullName();
            System.out.println(id + " " + fullName);
        }
    }
    
    public List<Customer> getAllCustomers() {
        // format for each customer: id + full name
        return listOfCustomers;
      }

} // CustomerCollection
