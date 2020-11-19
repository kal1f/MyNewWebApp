package database.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProviderTestImpl implements ConnectionProvider {

    @Override
    public Connection getCon() {
        Connection conn = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(ProviderConstantsTest.connection);
        } catch (Exception e) {
            System.out.println(e);
        }

        return conn;
    }

}
