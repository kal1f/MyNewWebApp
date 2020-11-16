package database;

import database.connection.ConnectionProvider;
import database.connection.ConnectionProviderImpl;
import database.entity.Customer;

import java.sql.*;
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
        int id = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            con = connectionProvider.getCon();
            ps = con.prepareStatement("insert into customer (customer, pass_, name_)" + "values(?,?,?)", Statement.RETURN_GENERATED_KEYS);


            ps.setString(1, c.getLogin());
            ps.setString(2, c.getPassword());
            ps.setString(3, c.getName());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            while (rs.next())
            {
                id = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        finally {
            connectionProvider.closeRS(rs);
            connectionProvider.closeStatement(ps);
            //connectionProvider.closeStatement(getIdStatement);
            connectionProvider.closeCon(con);
        }
        return id;
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
                c.setId(rs.getInt(4));
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
                        rs.getInt("id"));
                c.add(customer);
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
                        rs.getInt("id"));
                c.add(customer);
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
    public boolean isCustomerExist(String login, String password) {

        Customer c = getCustomer(login, password);
        if(c.getLogin() != null && c.getPassword() != null ){
            return true;
        }
        else {
            return false;
        }

    }

}
