package database;

import database.connection.ConnectionProvider;
import database.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    static Connection con;
    static PreparedStatement ps;

    @Override
    public int insertCustomer(Customer c) {
        int status = 0;
        try {
            con = ConnectionProvider.getCon();
            ps = con.prepareStatement("insert into customer (customer, pass_, name_)" + "values(?,?,?)");
            ps.setString(1, c.getLogin());
            ps.setString(2, c.getPassword());
            ps.setString(3, c.getName());
            status = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    @Override
    public Customer getCustomer(String userid, String pass) {

        Customer c = null;
        try{
            con = ConnectionProvider.getCon();
            ps = con.prepareStatement("select * from customer where customer=? and pass_=?");
            ps.setString(1,userid);
            ps.setString(2,pass);

            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                c = new Customer();
                c.setLogin(rs.getString(1));
                c.setPassword(rs.getString(2));
                c.setName(rs.getString(3));
                c.setId(rs.getString(4));
            }

            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        return c;
    }

    @Override
    public ArrayList<Customer> getCustomerByIdOrLogin(String login, String id) {
        ArrayList<Customer> c = new ArrayList<>();
        try{
            con = ConnectionProvider.getCon();
            ps = con.prepareStatement("select * from customer where id=? or customer=? ");
            ps.setString(1,id);
            ps.setString(2,login);


            ResultSet rs=ps.executeQuery();
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
        return c;
    }

    @Override
    public ArrayList<Customer> getCustomers() {

        ArrayList <Customer> c = new ArrayList<>();
        try{
            con = ConnectionProvider.getCon();
            ps = con.prepareStatement("select * from customer");

            ResultSet rs=ps.executeQuery();

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
        return c;
    }

    @Override
    public boolean isCustomerExist(String login, String password) {

        try{
            con = ConnectionProvider.getCon();
            ps = con.prepareStatement("select * from customer where customer=? and pass_=?");
            ps.setString(1,login);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if(!rs.next()) return false;

            while(rs.next()){
                Customer customer = new Customer(rs.getString("customer"),
                        rs.getString("pass_"), rs.getString("name_"),
                        rs.getString("id"));
                return customer.getPassword().equals(password);
            }

            con.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        return true;
    }
}
