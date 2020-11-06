package database.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider
{
    static Connection conn = null;
    public static Connection getCon(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection(ProviderConstants.connection,
                    ProviderConstants.username,ProviderConstants.pwd);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }
}
