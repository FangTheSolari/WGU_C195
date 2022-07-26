package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Java class that provides the logic to connect to the database
 * @author Adam Rutland-Ruiz
 */
public abstract class JDBC {
    private static final String proto = "jdbc";
    private static final String vend = ":mysql:";
    private static final String local="//localhost:3306/";
    private static final String databaseName="client_schedule";
    private static final String DB_URL=proto+vend+local+databaseName+"?connectionTimeZone=SERVER";
    private static final String username="sqlUser";
    private static final String password="Passw0rd!";
    private static Connection connect;

    /**
     * Opens the connection to the database
     */
    public static void openConnection() {
        try{
            connect= DriverManager.getConnection(DB_URL,username,password);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * closes the connection to the database
     */
    public static void closeConnection() {
        try {
            connect.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * A getter for the database connection
     * @return
     */
    public static Connection getConnection(){
        return connect;
    }
}
