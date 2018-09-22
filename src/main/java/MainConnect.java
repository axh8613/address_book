/**
 * The AddressBook program is an application which searches through a address book table in SQL. MainConnect is the main
 * method
 *
 * @author  Ante Hakstok
 * @version 1.0
 * @since   2018-09-22
 */
public class MainConnect
{
    /* This is where the SQLConnect class is initialized */
    public static SQLConnect sc = new SQLConnect();

    /**
     * This is the main method which will take the arguments and connect to the database.
     *
     * @param args main arguments
     */
    public static void main(String[] args)
    {
        /* In case of connection errors the error is written in a log */
        try
        {
            sc.connect();
            ArgumentCases ac = new ArgumentCases(args, sc);
            /* This is where the program processes the arguments */
            ac.argCheck();
            sc.close();
        }
        catch(DLException d)
        {
            d.log();
        }
        catch(ArrayIndexOutOfBoundsException aie)
        {
            System.out.println("Please specify a command.\n" +
                    "Available commands: list, add, search");
            System.exit(1);
        }
    }
}
