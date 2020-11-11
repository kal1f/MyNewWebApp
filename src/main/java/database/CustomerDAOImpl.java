package database;

import database.connection.ConnectionProvider;
import database.connection.ConnectionProviderImpl;
import database.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {


     private ConnectionProvider connectionProvider;

    public CustomerDAOImpl(){
        this.connectionProvider = new ConnectionProviderImpl();
    }

    public CustomerDAOImpl(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }


    @Override
    public int insertCustomer(Customer c) {
        int status = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = connectionProvider.getCon();
            ps = con.prepareStatement("insert into customer (customer, pass_, name_)" + "values(?,?,?)");
//            Statement statement = con.createStatement();
//            statement.execute("insert into customer (customer, pass_, name_) values(" + c.getLogin() + ",?,?)");
            ps.setString(1, c.getLogin());
            ps.setString(2, c.getPassword());
            ps.setString(3, c.getName());
            status = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        finally {
            connectionProvider.closeStatement(ps);
            connectionProvider.closeCon(con);
        }
        return status;
    }

    @Override
    public Customer getCustomer(String login, String pass) {

        Customer c = new Customer();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            con = connectionProvider.getCon();
            ps = con.prepareStatement("select * from customer where customer=? and pass_=?");
            ps.setString(1,login);
            ps.setString(2,pass);

            rs=ps.executeQuery();
            while(rs.next()){
                c.setLogin(rs.getString(1));
                c.setPassword(rs.getString(2));
                c.setName(rs.getString(3));
                c.setId(rs.getString(4));
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
        finally {
            connectionProvider.closeRS(rs);
            connectionProvider.closeStatement(ps);
            connectionProvider.closeCon(con);

        }
        return c;
    }

    @Override
    public ArrayList<Customer> getCustomerByIdOrLogin(String login, String id) {
        ArrayList<Customer> c = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            con = connectionProvider.getCon();
            ps = con.prepareStatement("select * from customer where id=? or customer=? ");
            ps.setString(1,id);
            ps.setString(2,login);


            rs=ps.executeQuery();
            while(rs.next()){
                Customer customer = new Customer(rs.getString("customer"),
                        rs.getString("pass_"), rs.getString("name_"),
                        rs.getString("id"));
                c.add(customer);
            }

            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        finally {
            connectionProvider.closeRS(rs);
            connectionProvider.closeStatement(ps);
            connectionProvider.closeCon(con);
        }
        return c;
    }

    @Override
    public ArrayList<Customer> getCustomers() {
        ArrayList <Customer> c = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            con = connectionProvider.getCon();
            ps = con.prepareStatement("select * from customer");

            rs = ps.executeQuery();

            while(rs.next()){
                Customer customer = new Customer(rs.getString("customer"),
                        rs.getString("pass_"), rs.getString("name_"),
                        rs.getString("id"));
                c.add(customer);
            }
            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        finally {
            connectionProvider.closeRS(rs);
            connectionProvider.closeStatement(ps);
            connectionProvider.closeCon(con);
        }
        return c;
    }

    @Override
    public boolean isCustomerExist(String login, String password) {
        try{
            Connection con = connectionProvider.getCon();
            PreparedStatement ps = con.prepareStatement("select * from customer where customer=? and pass_=?");
            ps.setString(1,login);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if(!rs.next()) return false;

            while(rs.next()){
                Customer customer = new Customer(rs.getString("customer"),
                        rs.getString("pass_"), rs.getString("name_"),
                        rs.getString("id"));
                return customer.getPassword().equals(password) && customer.getLogin().equals(login);
            }

            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        return true;
        //return getCustomer(login, password) != null;
    }
}
