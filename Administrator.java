
/**
 * The Administrator class is responsible for manipulating all admin-related information.
 *
 * @author Jie Wang
 * @version 2017.05.15
 */
public class Administrator extends User{

    private String adminName;
    private String emailAddress;

    public Administrator()
    {
        adminName = "";
        emailAddress = "";
    }

    public String getAdminName()
    {
        return adminName;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setAdministratorName(String name)
    {
        adminName = name;
    }

    public void setEmailAdress(String email)
    {
        emailAddress = email;
    }
}
