package database.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface ConnectionProvider {

     Connection getCon();
     default void closeStatement(Statement statement){

          if(statement != null){
               try {
                    statement.close();
                    statement = null;
               } catch (SQLException e) {
                    System.out.println(e.getMessage());
               }
          }
     }

     default void closeRS(ResultSet rs){
          if(rs != null)
          {
               try{
                    rs.close();
                    rs = null;
               } catch (SQLException e){
                    System.out.println(e.getMessage());
               }
          }
     };
     default void closeCon(Connection con){
          try {
               if (con != null && !con.isClosed()) {
                    if (!con.getAutoCommit()) {
                         con.commit();
                         con.setAutoCommit(true);
                    }
                    con.close();
                    con = null;
               }
          }catch (SQLException e){
               System.out.println(e.getMessage());
          }
     };
}
