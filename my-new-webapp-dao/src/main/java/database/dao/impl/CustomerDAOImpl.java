package database.dao.impl;

import database.connection.ConnectionProvider;
import database.connection.ConnectionProviderImpl;
import database.dao.CustomerDAO;
import database.entity.Customer;
import database.entity.Role;
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
        Role r;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            con = connectionProvider.getCon();
            ps = con.prepareStatement("SELECT c.id, c.login, c.password, c.name, r.id, r.name " +
                    "FROM role as `r` " +
                    "INNER JOIN (SELECT * FROM customer where login=? and password=? ) as `c` " +
                    "ON r.id = c.role_id;");
//            ps = con.prepareStatement("SELECT `t2`.id, `t2`.login, `t2`.password, `t2`.name, `role`.name from \n" +
//                            "(SELECT * FROM customer where `login`=? and `password`=?) as t2, role\n" +
//                            "where `role`.id = `t2`.role_id\n");
            ps.setString(1,login);
            ps.setString(2,pass);

            rs=ps.executeQuery();


            while(rs.next()){
                c = new Customer();
                r = new Role();
                c.setId(rs.getInt(1));
                c.setLogin(rs.getString(2));
                c.setPassword(rs.getString(3));
                c.setName(rs.getString(4));
                r.setId(rs.getInt(5));
                r.setName(rs.getString(6));
                c.setRole(r);
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
            ps.setString(2, customer.getPassword());
            ps.setString(3, customer.getName());
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
            ps = con.prepareStatement("SELECT c.id, c.login, c.password, c.name, r.id, r.name " +
                    "FROM role AS `r` " +
                    "INNER JOIN (SELECT * FROM customer WHERE id=? OR login=? ) AS `c` " +
                    "ON r.id = c.role_id");
            ps.setInt(1,id);
            ps.setString(2,login);

            rs=ps.executeQuery();

            while(rs.next()){
                Customer customer = new Customer();
                Role r = new Role();
                customer.setId(rs.getInt(1));
                customer.setLogin(rs.getString(2));
                customer.setPassword(rs.getString(3));
                customer.setName(rs.getString(4));
                r.setId(rs.getInt(5));
                r.setName(rs.getString(6));
                customer.setRole(r);
//                Customer customer = new Customer(
//                        rs.getInt("id"),
//                        rs.getString("login"),
//                        rs.getString("password"),
//                        rs.getString("name"),
//                        rs.getString("role_id"));
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
            ps = con.prepareStatement("SELECT c.id, c.login, c.password, c.name, r.id, r.name " +
                    "FROM role AS `r`" +
                    "INNER JOIN (SELECT * FROM customer) AS `c` " +
                    "ON r.id = c.role_id;");

            rs = ps.executeQuery();

            while(rs.next()){
                Customer customer = new Customer();
                Role r = new Role();
                customer.setId(rs.getInt(1));
                customer.setLogin(rs.getString(2));
                customer.setPassword(rs.getString(3));
                customer.setName(rs.getString(4));
                r.setId(rs.getInt(5));
                r.setName(rs.getString(6));
                customer.setRole(r);
//                Customer customer = new Customer(rs.getInt("id"),
//                        rs.getString("login"),
//                        rs.getString("password"),
//                        rs.getString("name"),
//                        rs.getString("role_id"));
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