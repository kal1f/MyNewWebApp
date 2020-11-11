package database.connection;

import com.fasterxml.jackson.databind.deser.std.DateDeserializers;

import java.sql.*;

public class ConnectionProviderImpl implements ConnectionProvider {

    static Connection conn;

    public  Connection getCon(){
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

    @Override
    public boolean closeStatement(Statement statement) {
        if(statement != null){
            try {
                statement.close();
                statement = null;
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean closeRS(ResultSet rs) {
        if(rs != null)
        {
            try{
                rs.close();
                rs = null;
                return true;
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean closeCon(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                if (!con.getAutoCommit()) {
                    con.commit();
                    con.setAutoCommit(true);
                }
                con.close();
                con = null;
                return true;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
