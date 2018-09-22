import java.util.*;
import java.io.*;
import java.sql.*;
/**
 * The DLException class deals with logging all the SQL-related exceptions
 *
 * @author  Ante Hakstok
 * @version 1.0
 * @since   2018-09-22
 */
public class DLException extends Exception
{
    private static final long serialVersionUID = 1L;
    ArrayList<ArrayList<String>> exceptions = new ArrayList<>();
    Exception exception;
    /**
     * Default constructor 1
     * @param ex the exception
     */
    public DLException(Exception ex)
    {
        super("Unable to complete operation");
        exception = ex;
    }

    /**
     * Default constructor 2
     * @param ex the exception
     * @param list the exceptions list
     */
    public DLException(Exception ex, ArrayList<ArrayList<String>> list)
    {
        super("Unable to complete operation");
        exception = ex;
        exceptions = list;
        ArrayList<String> exStr = new ArrayList<>();

        /* In case of SQL Exception we store the information */
        if (exception instanceof SQLException)
        {
            exStr.add("Error msg: " + exception.getMessage());
            exStr.add("SQLState: " + (((SQLException)exception).getSQLState()));
            exStr.add("Error code:" + String.valueOf(((SQLException)exception).getErrorCode()));
            exStr.add("Cause: " + (exception.getCause()).toString());
            if(exception.getStackTrace() != null)
            {
                StackTraceElement[] stElement = exception.getStackTrace();

                for (StackTraceElement el: stElement)
                {
                    exStr.add(el.toString());
                }
                exceptions.add(exStr);
            }
        }
        /* These are the operations in case of any other errors */
        else
        {
            exStr.add(exception.getMessage());
            if(exception.getStackTrace() != null)
            {
                StackTraceElement[] stElement = exception.getStackTrace();
                for (StackTraceElement el: stElement)
                {
                    exStr.add(el.toString());
                }
                exceptions.add(exStr);
            }
        }
    }
    /**
     * This method is used to store the exception logs into a file
     */
    public void log()
    {
        try
        {
            /* The file is created */
            File log = new File("log.txt");
            FileWriter wrt = new FileWriter(log);

            /* Whenever an exception happens this method starts storing */
            if(exceptions.size() > 0)
            {
                System.out.println("Logging...");
                for (int i = 0; i <= exceptions.size() - 1; i++)
                {
                    for(int j = 0; j <= exceptions.get(i).size() - 1; j++)
                    {
                        java.util.Date date = new java.util.Date();
                        wrt.write(new Timestamp(date.getTime()).toString() + " ");
                        wrt.write(exceptions.get(i).get(j) + System.getProperty("line.separator"));
                        wrt.flush();
                    }
                }
            }

            else
            {
                java.util.Date date = new java.util.Date();
                wrt.write(new Timestamp(date.getTime()).toString() + " ");
                wrt.write(exception.getMessage() + System.getProperty("line.separator"));

                wrt.flush();
                wrt.close();
            }
        }
        catch(IOException e)
        {

        }
    }
}