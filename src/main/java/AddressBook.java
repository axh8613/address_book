import java.util.ArrayList;

/**
 * AddressBook is the class that deals with the parameter values.
 *
 * @author  Ante Hakstok
 * @version 1.0
 * @since   2018-09-22
 */

public class AddressBook
{
    private String aID;
    private String aName;
    private String aMail;
    private String aPhone;

    ArrayList<ArrayList<String>> exceptions = new ArrayList<>();

    public AddressBook()
    {

    }

    /**
     * This method is used to change the ID of the entry
     *
     * @param aID the id of the entry
     */
    public void changeId(String aID)
    {
        this.aID = aID;
    }

    /**
     * This method is used to change the name of the entry
     *
     * @param aName the name of the entry
     */
    public void changeName(String aName)
    {
        this.aName = aName;
    }

    /**
     * This method is used to change the Email of the entry
     *
     * @param aMail the email of the entry
     */
    public void changeMail(String aMail)
    {
        this.aMail = aMail;
    }

    /**
     * This method is used to change the phone number of the entry
     *
     * @param aPhone the phone number of the entry
     */
    public void changePhone(String aPhone)
    {
        this.aPhone = aPhone;
    }

    /**
     * This method is used to get the ID of the entry
     *
     * @return the id of the entry
     */
    public String getId()
    {
        return aID;
    }

    /**
     * This method is used to get the name of the entry
     *
     * @return the name of the entry
     */
    public String getName()
    {
        return aName;
    }

    /**
     * This method is used to get the Email of the entry
     *
     * @return the email of the entry
     */
    public String getMail()
    {
        return aMail;
    }

    /**
     * This method is used to get the phone number of the entry
     *
     * @return the phone number of the entry
     */
    public String getPhone()
    {
        return aPhone;
    }

    /**
     * This method is used to print out the results from our query
     *
     * @return the parameters
     */
    public String printEq()
    {
        return  "Name: " + getName() + "\n" + "Email: " + getMail() + "\n" + "Phone: " + getPhone() + "\n" + "-----------";
    }
}
