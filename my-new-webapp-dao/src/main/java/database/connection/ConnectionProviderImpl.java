package database.connection;

import java.sql.*;

public class ConnectionProviderImpl implements ConnectionProvider {

    public Connection getCon(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(ProviderConstants.connection,
                    ProviderConstants.username, ProviderConstants.pwd);
        } catch (Exception e) {
            System.out.println(e);
        }

        return conn;
    }

}
