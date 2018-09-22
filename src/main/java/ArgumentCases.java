/**
 * The ArgumentCases class is checking wherever the arguments presented are valid or not
 *
 * @author  Ante Hakstok
 * @version 1.0
 * @since   2018-09-22
 */
public class ArgumentCases
{
    /* These are the parameters for connection and arguments */
    private String[] args;
    private SQLConnect sc;
    /* These are the parameters for field values, by default they are empty*/
    private String name = " ";
    private String mail = " ";
    private String phone = " ";
    /* This parameter is used to determine whenever the search, list or add function is going to be used*/
    private int caseNum;
    /* This parameter is used in order to determine which status message should be returned */
    private int status;

    /**
     * This is the constructor for the ArgumentCases class
     *
     * @param args the arguments
     * @param sc the SQLConnect instance
     */
    public ArgumentCases(String[] args, SQLConnect sc)
    {
        this.args = args;
        this.sc = sc;
    }

    /**
     * This is the method for checking the arguments
     */
    public void argCheck()
    {
        /* The first row of the arguments is taken as the main parameter. These are: search, add and list */
        String first = args[0];
        /* It is processed through a switch */
        switch(first)
        {
            /* For list, it should just take one parameter that should print out every entry in the database */
            case "list":
                /* Status is 1 in case of the list method being successful */
                status = 0;
                System.out.println(returnStatus(status));
                /* catching the DLException */
                try
                {
                    /* this is the method call for the "list" command */
                    sc.list();
                }
                catch (DLException e)
                {
                    e.printStackTrace();
                }
                break;
            /* For add, it should take up to 4 parameters, with the first one being the "add" keyword, and at least one
             * more which would be the value added to the table */
            case "add":
                if(args.length <= 4 && args.length != 1)
                {
                    status = 1;
                    System.out.println(returnStatus(status));
                    checkArgsAdd(args);
                }
                else
                {
                    /* Status is 0 in case of the failed method */
                    status = 2;
                    System.out.println(returnStatus(status));
                }
                break;
            /* For add, it should take up to 2 parameters, with the first one being the "search" keyword, and one more
             * parameter which will be the value searched */
            case "search":
                if(args.length == 2 && args.length != 1)
                {
                    status = 3;
                    System.out.println(returnStatus(status));
                    checkArgsSearch(args);
                }
                else
                {
                    status = 4;
                    System.out.println(returnStatus(status));
                }
                break;
            /* This is the case in which there were no correct parameters used */
            default:
                status = 5;
                System.out.println(returnStatus(status));
                break;
        }
    }

    /**
     * This is the method for further checks of the add arguments
     *
     * @param args the arguments
     */
    public void checkArgsAdd(String[] args)
    {
        /* Values after the first one in the array are checked */
        for(int i=1; i<args.length; i++)
        {
            /* In case the value contains "name=" */
            if(args[i].contains("name="))
            {
                /* The value is split where the = sign is and stored as the "name" parameter */
                String[] namePart = args[i].split("=");
                name = namePart[1];
            }
            /* In case the value contains "email=" */
            else if(args[i].contains("email="))
            {
                String[] mailPart = args[i].split("=");
                /* Further checks are conducted to determine if the email address is in the correct format */
                if(mailPart[1].contains("@") && mailPart[1].contains(".com"))
                {
                    mail = mailPart[1];
                }
                else
                {
                    System.out.println("Illegal email address " + mailPart[1] + ", record NOT stored.");
                    System.exit(1);
                }
            }
            /* In case the value contains "phone=" */
            else if(args[i].contains("phone="))
            {
                String[] phonePart = args[i].split("=");
                /* Further checks are conducted to determine if the phone number is in a correct format */
                if(phonePart[1].length() == 12 && phonePart[1].matches("\\d*\\d+"))
                {
                    phone = "+" + phonePart[1];
                }
                else
                {
                    System.out.println("Invalid phone number " + phonePart[1] + ", record NOT stored.");
                    System.exit(1);
                }
            }
        }
        /* After the check, the values are further used in the SQLConnect class */
        try
        {
            sc.add(name, mail, phone);
        }
        catch (DLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This is the method for further checks of the search arguments
     *
     * @param args the arguments
     */
    public void checkArgsSearch(String[] args)
    {
        /* Values after the first one in the array are checked */
        for(int i=1; i<args.length; i++)
        {
            if(args[i].contains("name="))
            {
                String[] namePart = args[i].split("=");
                name = namePart[1];
                /* The case number is used to determine the field where the value will be searched */
                caseNum = 1;
                try
                {
                    /* The search value and the case number are further used in the SQLConnect class */
                    sc.search(name, caseNum);
                }
                catch (DLException e)
                {
                    e.printStackTrace();
                }
            }
            else if(args[i].contains("email="))
            {
                String[] mailPart = args[i].split("=");
                mail = mailPart[1];
                caseNum = 2;
                try
                {
                    sc.search(mail, caseNum);
                }
                catch (DLException e)
                {
                    e.printStackTrace();
                }
            }
            else if(args[i].contains("phone="))
            {
                String[] phonePart = args[i].split("=");
                phone = phonePart[1];
                caseNum = 3;
                try
                {
                    sc.search(phone, caseNum);
                }
                catch (DLException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                status = 6;
                System.out.println(returnStatus(status));
            }
        }
    }

    /**
     * This is the method which returns the status number
     *
     * @return the status number
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * This is the method which returns the status message based on the status number
     *
     * @param status status number
     * @return the status message
     */
    public String returnStatus(int status)
    {
        String statusReturn = "";
        /* Cases for each number */
        switch(status)
        {
            case 0:
                statusReturn = "List argument successfully initialized!";
                break;
            case 1:
                statusReturn = "Add argument successfully initialized!";
                break;
            case 2:
                statusReturn = "Invalid number of arguments for the add method!";
                break;
            case 3:
                statusReturn = "Search argument successfully initialized!";
                break;
            case 4:
                statusReturn = "Invalid number of arguments for the search method!";
                break;
            case 5:
                statusReturn = "Please specify a command.\n" +
                        "Available commands: list, add, search";
                break;
            case 6:
                statusReturn = "Invalid column name!";
                break;
            default:
                break;
        }
        /* String is returned */
        return statusReturn;
    }
}
