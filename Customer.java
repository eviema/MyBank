import java.util.ArrayList;
import java.util.List;

/**
 * The Customer class is responsible for manipulating all customer-related information.
 *
 * @author Yifei Ma
 * @version 2017.05.15
 */

public class Customer extends User{

    private static int nextCustomerID = 1000;
    private List<BankAccount> bankAccounts;
    private String fullName;
    private String emailAdd;
    private String homeAdd;
    private Boolean lockedStatus;
    private int phoneNumber;
    private int PIN;

    public Customer() {
        userID = ++nextCustomerID;
        password = null;
        bankAccounts = null;
        fullName = null;
        emailAdd = null;
        homeAdd = null;
        lockedStatus = null;
        phoneNumber = 0;
        PIN = 0;
    }

    public Customer(String newPassword,
                    String newFullName,
                    String newEmailAdd,
                    String newHomeAdd,
                    int newPhone,
                    int newPIN) {
        userID = ++nextCustomerID;
        password = newPassword;
        bankAccounts = new ArrayList<>();
        fullName = newFullName;
        emailAdd = newEmailAdd;
        homeAdd = newHomeAdd;
        lockedStatus = false;
        phoneNumber = newPhone;
        PIN = newPIN;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public String getHomeAdd() {
        return homeAdd;
    }

    public Boolean getLockedStatus() {
        return lockedStatus;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public int getPIN() {
        return PIN;
    }

    public void setBankAccounts(List<BankAccount> newBankAccounts) {
        bankAccounts = newBankAccounts;
    }

    public void setFullName(String newName) {
        fullName = newName;
    }

    public void setEmailAdd(String newEmail) {
        emailAdd = newEmail;
    }

    public void setHomeAdd(String newHomeAdd) {
        homeAdd = newHomeAdd;
    }

    public void setLockedStatus(Boolean newLockedStatus) {
        lockedStatus = newLockedStatus;
    }

    public void setPhoneNumber(int newPhoneNum) {
        phoneNumber = newPhoneNum;
    }

    public void setPIN(int newPIN) {
        PIN = newPIN;
    }
}
