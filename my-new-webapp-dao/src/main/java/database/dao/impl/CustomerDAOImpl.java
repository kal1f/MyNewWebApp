package database.dao.impl;

import database.connection.ConnectionProvider;
import database.connection.ConnectionProviderImpl;
import database.dao.CustomerDAO;
import database.entity.Customer;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {


    private final ConnectionProvider connectionProvider;

    static final Logger LOGGER = Logger.getLogger(CustomerDAOImpl.class);

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
            ps = con.prepareStatement("insert into customer (login, password, name)" + " values(?,?,?)", Statement.RETURN_GENERATED_KEYS);


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
            LOGGER.debug(e.getMessage(), e);
        }
        finally {
            connectionProvider.closeRS(rs);
            connectionProvider.closeStatement(ps);
            connectionProvider.closeCon(con);
        }
        return id;
    }

    @Override
    public Customer getCustomer(String login, String pass) {
        Customer c = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            con = connectionProvider.getCon();
            ps = con.prepareStatement("select * from customer where login=? and password=?");
            ps.setString(1,login);
            ps.setString(2,pass);

            rs=ps.executeQuery();


            while(rs.next()){
                c = new Customer();
                c.setLogin(rs.getString(2));
                c.setPassword(rs.getString(3));
                c.setName(rs.getString(4));
                c.setRole(rs.getString(5));
                c.setId(rs.getInt(1));
            }
        }
        catch (Exception e){
            LOGGER.debug(e.getMessage(), e);
        }
        finally {
            connectionProvider.closeRS(rs);
            connectionProvider.closeStatement(ps);
            connectionProvider.closeCon(con);

        }
        return c;
    }

    @Override
    public int updateCustomer(Customer customer, Integer id) {
        int updated = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            con = connectionProvider.getCon();
            ps = con.prepareStatement("UPDATE customer SET login=?," +
                    "password=?," +
                    "name=? WHERE id = ? ", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, customer.getLogin());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getPassword());
            ps.setInt(4,id);

            updated = ps.executeUpdate();


        } catch (Exception e) {
            LOGGER.debug(e.getMessage(), e);
        }
        finally {
            connectionProvider.closeRS(rs);
            connectionProvider.closeStatement(ps);
            connectionProvider.closeCon(con);
        }
        return updated;
    }

    @Override
    public ArrayList<Customer> getCustomerByIdOrLogin(String login, Integer id) {
        ArrayList<Customer> c = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            con = connectionProvider.getCon();
            ps = con.prepareStatement("select * from customer where id=? or login=? ");
            ps.setInt(1,id);
            ps.setString(2,login);


            rs=ps.executeQuery();
            while(rs.next()){
                Customer customer = new Customer(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("role_id"));
                c.add(customer);
            }

        }
        catch (Exception e){
            LOGGER.debug(e.getMessage(), e);
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
                Customer customer = new Customer(rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("role_id"));
                c.add(customer);
            }
        }
        catch (Exception e){
            LOGGER.debug(e.getMessage(), e);
        }
        finally {
            connectionProvider.closeRS(rs);
            connectionProvider.closeStatement(ps);
            connectionProvider.closeCon(con);
        }
        return c;
    }



}
