/**
 * The User class can be used to retrieve and set basic information
 * (i.e. id, password, and user type) of a user.
 *
 * @author Yifei Ma
 * @version 2017.05.15
 */

public class User {

    protected int userID;
    protected String password;
    protected String userType;

    public int getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserID(int newId) {
        userID = newId;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public void setUserType(String newType) {
        userType = newType;
    }
}
