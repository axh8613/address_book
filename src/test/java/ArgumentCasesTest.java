import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains tests relating to the ArgumentCases class
 * @author  Ante Hakstok
 * @version 1.0
 * @since   2018-09-11
 */
public class ArgumentCasesTest
{
    public static SQLConnect sc = new SQLConnect();
    int expectedStat;
    int retrievedStat;
    String[] testError;

    /**
     * This test method will test if the program detects abnormal parameters
     */
    @Test
    void argCheckParameters()
    {
        testError = new String[3];
        testError[0] = "a";
        testError[1] = "b";
        testError[2] = "c";
        expectedStat = 0;

        mockMain(testError);

        assertEquals(expectedStat, retrievedStat);
    }

    /**
     * This test method will test if the list parameter works correctly
     */
    @Test
    void argCheckList()
    {
        testError = new String[1];
        testError[0] = "list";
        expectedStat = 1;

        mockMain(testError);

        assertEquals(expectedStat, retrievedStat);
    }

    /**
     * This test method will test if the search parameter can detect if the inserted field where we search the value
     * doesn't exist
     */
    @Test
    void argCheckSearchError()
    {
        testError = new String[2];
        testError[0] = "search";
        testError[1] = "nam=Someone";
        expectedStat = 0;

        mockMain(testError);

        assertEquals(expectedStat, retrievedStat);
    }

    /**
     * This test method will test if the search method can detect if we insert more than one search parameter
     */
    @Test
    void argCheckArgNumber()
    {
        testError = new String[3];
        testError[0] = "search";
        testError[1] = "name=Someone";
        testError[2] = "email=a@a.com";
        expectedStat = 0;

        mockMain(testError);

        assertEquals(expectedStat, retrievedStat);
    }

    /**
     * This test method will test if the search method functions
     */
    @Test
    void argCheckSearchResult()
    {
        testError = new String[2];
        testError[0] = "search";
        testError[1] = "name=Someone";
        expectedStat = 1;

        mockMain(testError);

        assertEquals(expectedStat, retrievedStat);
    }

    /**
     * This test method will test if the add method works correctly
     */
    @Test
    void argCheckAdd()
    {
        testError = new String[4];
        testError[0] = "add";
        testError[1] = "name=Ante";
        testError[2] = "phone=385981667224";
        testError[3] = "email=ante@mail.com";
        expectedStat = 1;

        mockMain(testError);

        assertEquals(expectedStat, retrievedStat);
    }

    /**
     * This method is basically a mock "main" method that connects to a database and checks our test parameters
     * @param testError the array containing our parameters for testing
     */
    void mockMain(String[] testError)
    {
        try
        {
            sc.connect();
            ArgumentCases ac = new ArgumentCases(testError, sc);
            ac.argCheck();
            retrievedStat = ac.getStatus();
            sc.close();
        }
        catch (DLException e)
        {
            e.log();
        }
    }
}
