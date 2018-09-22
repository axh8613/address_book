/**
 * The SQLConstants class is returning messages based on whenever an operation was a success or not
 *
 * @author  Ante Hakstok
 * @version 1.0
 * @since   2018-09-22
 */
public class SQLConstants
{
    /* These are the constants that contain the messages */
    public static final String NO_RECORDS = "No records found.";
    public static final String RECORD_ADD = "Record added";
    public static final String UPDATE =  "Record updated for: ";
    public static final String NO_NAME = "No records found matching name = ";
    public static final String NO_MAIL = "No records found matching email = ";
    public static final String NO_PHONE = "No records found matching phone = ";
    public static final String YES_NAME = "Records found for name = ";
    public static final String YES_MAIL = "Records found for email = ";
    public static final String YES_PHONE = "Records found forphone = ";

    /**
     * This is the method that returns a message with one parameter
     *
     * @param a first parameter
     */
    public static void returnOne(String a)
    {
        System.out.println(a);
    }

    /**
     * This is the method that returns a message with two parameters
     *
     * @param a first parameter
     * @param b second parameter
     */
    public static void returnTwo(String a, String b)
    {
        System.out.println(a + b);
    }
}
