package database.connection;

import org.apache.log4j.Logger;

import java.sql.*;

public class ConnectionProviderImpl implements ConnectionProvider {

    static final Logger LOGGER = Logger.getLogger(ConnectionProviderImpl.class);

    public Connection getCon(){
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(ProviderConstants.connection,
                    ProviderConstants.username, ProviderConstants.pwd);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage(), e);
        }

        return conn;
    }

}
