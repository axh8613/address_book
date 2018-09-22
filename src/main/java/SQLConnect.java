import java.sql.*;
import java.util.Properties;
import java.util.*;

/**
 * The SQLConnect class is performing the supported operations based on the parameters provided
 *
 * @author  Ante Hakstok
 * @version 1.0
 * @since   2018-09-22
 */
public class SQLConnect
{
    /* The parameters needed to connect to the database */
    private String mysql = "jdbc:mysql://localhost:3306/ante?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private String un = "root";
    private String pw = "password";
    private Connection con;
    ArrayList<ArrayList<String>> exceptions = new ArrayList<>();
    private Statement state;
    private ResultSet res;
    private AddressBook ab = new AddressBook();

    public SQLConnect()
    {

    }

    /**
     * This is the method that connects to the database
     *
     * @return boolean that determines the success of connection
     * @throws DLException
     */
    public boolean connect() throws DLException
    {
        /* Here we are catching the SQLException */
        try
        {
            /* We use the username and password to get the connection to database */
            Properties connectionProps = new Properties();
            connectionProps.put("user", this.un);
            connectionProps.put("password", this.pw);

            con = DriverManager.getConnection(mysql,connectionProps);
            return true;
        }
        catch(SQLException s)
        {
            s.printStackTrace();
            throw new DLException(s, exceptions);
        }
    }

    /**
     * This is the method that closes the connection to the database
     *
     * @return boolean that determines the success of disconnect operation
     * @throws DLException
     */
    public boolean close() throws DLException
    {
        try
        {
            con.close();
            return true;
        }
        //catch an sql exception
        catch(SQLException s)
        {
            throw new DLException(s);
        }
    }

    /**
     * This is the method that performs the list operation
     *
     * @throws DLException
     */
   public void list() throws DLException
   {
       try
       {
           /* The query is prepared here with createStatement */
           state = con.createStatement();
           res = state.executeQuery("SELECT * FROM addr");
           /* These two cases will check if the results are empty or not */
           if(!res.next())
           {
               SQLConstants.returnOne(SQLConstants.NO_RECORDS);
           }
           else
           {
               do
               {
                   /* The results are printed with the queryResults method */
                   queryResults(res);
               }
               while(res.next());
           }
       }
       catch(SQLException s)
       {
           throw new DLException(s);
       }
   }

    /**
     * This is the method that performs the add operation
     *
     * @param name the name parameter
     * @param mail the email parameter
     * @param phone the phone parameter
     * @throws DLException
     */
   public void add(String name, String mail, String phone) throws DLException
   {
       /* PreparedStatement is used to avoid SQL injections */
       PreparedStatement p;
       PreparedStatement upd;
       try
       {
           /* First thing to do is search the provided parameters in order to see if they already exist in the
           database */
           state = con.createStatement();
           p = con.prepareStatement("SELECT * FROM addr WHERE name = ? OR email = ? OR phone = ?");
           /* After which we set the parameters with obtained values from the database */
           p.setString(1, name);
           p.setString(2, mail);
           p.setString(3, phone);
           res = p.executeQuery();
           /* If there are no results then the values are inserted */
           if(!res.next())
           {
               p = con.prepareStatement("INSERT INTO addr (name, email, phone) VALUES (?, ?, ?)");
               p.setString(1, name);
               p.setString(2, mail);
               p.setString(3, phone);
               p.executeUpdate();
               SQLConstants.returnOne(SQLConstants.RECORD_ADD);
           }
           /* If there are already existing values in the database they are instead updated */
           else
           {
               /* First we get the existing values in the database */
                String nameExists = res.getString("Name");
                String mailExists = res.getString("Email");
                String phoneExists = res.getString("Phone");
                int idInt = res.getInt("Id");
                upd = con.prepareStatement("UPDATE addr SET name = ?, email = ?, phone = ? WHERE id = ?");
               /* Here we check whenever the values obtained from parameters are empty; in that case the value
                * in the database won't be updated, and instead will remain the same as the existing one */
                if(name.equals(" "))
                {
                    upd.setString(1, nameExists);
                }
                else
                {
                    upd.setString(1, name);
                }
                if(mail.equals(" "))
                {
                    upd.setString(2, mailExists);
                }
                else
                {
                    upd.setString(2, mail);
                }
                if(phone.equals(" "))
                {
                    upd.setString(3, phoneExists);
                }
                else
                {
                    upd.setString(3, phone);
                }

                upd.setInt(4, idInt);
                upd.executeUpdate();
                SQLConstants.returnTwo(SQLConstants.UPDATE, name);
           }
       }
       catch(SQLException s)
       {
           throw new DLException(s);
       }
   }

    /**
     * This is the method that performs the add operation
     *
     * @param term the search term
     * @param caseNum the number that determines which field is going to be searched
     * @throws DLException
     */
   public void search(String term, int caseNum) throws DLException
   {
       PreparedStatement p;
       try
       {
           state = con.createStatement();
           /* Here the method cycles through possible cases to determine if it will check from name, email or phone
            * number */
           switch(caseNum)
           {
               case 1:
                   /* The prepared statement for select */
                   p = con.prepareStatement("SELECT * FROM addr WHERE name = ?");
                   p.setString(1, term);
                   res = p.executeQuery();
                   /* Here we check wherever there are any results or not*/
                   if(!res.next())
                   {
                       SQLConstants.returnTwo(SQLConstants.NO_NAME, term);
                   }
                   else
                   {
                       SQLConstants.returnTwo(SQLConstants.YES_NAME, term);
                       queryResults(res);
                   }
                   break;
               case 2:
                   p = con.prepareStatement("SELECT * FROM addr WHERE email = ?");
                   p.setString(1, term);
                   res = p.executeQuery();
                   if(!res.next())
                   {
                       SQLConstants.returnTwo(SQLConstants.NO_MAIL, term);
                   }
                   else
                   {
                       SQLConstants.returnTwo(SQLConstants.YES_MAIL, term);
                       queryResults(res);
                   }
                   break;
               case 3:
                   p = con.prepareStatement("SELECT * FROM addr WHERE phone = ?");
                   p.setString(1, term);
                   res = p.executeQuery();
                   if(!res.next())
                   {
                       SQLConstants.returnTwo(SQLConstants.NO_PHONE, term);
                   }
                   else
                   {
                       SQLConstants.returnTwo(SQLConstants.YES_PHONE, term);
                       queryResults(res);
                   }
                   break;
               default:
                   SQLConstants.returnOne(SQLConstants.NO_RECORDS);
                   break;
           }
       }
       catch(SQLException s)
       {
           throw new DLException(s);
       }
   }

    /**
     * This is the method that prints out the results
     *
     * @param res the ResultSet that contains the results
     * @throws DLException
     */
   public void queryResults(ResultSet res) throws DLException
   {
       try
       {
           /* The ID, name, email and phone values are changed with the AddressBook class and printed out*/
           ab.changeId(String.valueOf(res.getInt("Id")));
           ab.changeName(res.getString("Name"));
           ab.changeMail(res.getString("Email"));
           ab.changePhone(res.getString("Phone"));
           System.out.println(ab.printEq());
       }
       catch(SQLException s)
       {
           throw new DLException(s);
       }
   }
}
