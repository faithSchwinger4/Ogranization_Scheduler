package utility;

import java.sql.Connection;
import java.sql.DriverManager;

/** This class holds methods used to connect and disconnect from the database. */
public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone=SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // reference to driver interface
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection connection;

    /** This function opens a connection to the database */
    public static void openConnection(){
        try{
            Class.forName(driver); //locates the driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection successful");
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    /** This function closes a connection to the database */
    //always close when done using
    public static void closeConnection(){
        try{
            connection.close();
            System.out.println("Connection Closed.");
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

}
