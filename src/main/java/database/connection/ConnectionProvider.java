package database.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface ConnectionProvider {
     Connection getCon();
     boolean closeStatement(Statement statement);
     boolean closeRS(ResultSet rs);
     boolean closeCon(Connection con);
}
