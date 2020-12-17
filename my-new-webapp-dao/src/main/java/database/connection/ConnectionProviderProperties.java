package database.connection;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionProviderProperties implements ConnectionProvider{

    private final Properties properties;

    static final Logger LOGGER = Logger.getLogger(ConnectionProviderProperties.class);

    public ConnectionProviderProperties(Properties properties) {
        this.properties = properties;
    }

    public Connection getCon(){
        Connection conn = null;
        try {
            Class.forName(properties.getProperty("db.driver"));
            conn = DriverManager.getConnection(properties.getProperty("db.url"),
                    properties.getProperty("db.user"), properties.getProperty("db.pwd"));
        } catch (Exception e) {
            LOGGER.debug(e.getMessage(), e);
        }

        return conn;
    }
}
