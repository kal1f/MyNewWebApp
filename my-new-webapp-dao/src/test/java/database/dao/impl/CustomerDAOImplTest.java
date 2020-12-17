package database.dao.impl;

import database.connection.ConnectionProvider;

import database.connection.ConnectionProviderTestImpl;

import database.dao.CustomerDAO;
import database.entity.Customer;
import database.entity.Role;
import org.apache.log4j.Logger;
import org.junit.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CustomerDAOImplTest {

    static ConnectionProvider connectionProvider;
    static CustomerDAO customerDAO;

    static final Logger LOGGER = Logger.getLogger(CustomerDAOImplTest.class);

    @BeforeClass
    public static void  databaseUp() {
        connectionProvider = new ConnectionProviderTestImpl();
        customerDAO = new CustomerDAOImpl(connectionProvider);
    }
    @AfterClass
    public static void databaseDown(){
        connectionProvider.closeCon(connectionProvider.getCon());
    }
    @Before
    public void beforeMethod() throws SQLException {
        Statement createTable = connectionProvider.getCon().createStatement();
        createTable.execute("CREATE TABLE ROLE(id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(15) NOT NULL unique );" +
                "CREATE TABLE CUSTOMER(id INT PRIMARY KEY AUTO_INCREMENT," +
                " login VARCHAR(50) NOT NULL UNIQUE," +
                " password VARCHAR(50) NOT NULL," +
                " name VARCHAR(50) DEFAULT NULL," +
                " role_id INT DEFAULT 1, " +
                "CONSTRAINT fk FOREIGN KEY (role_id) REFERENCES ROLE(ID));");

        fillInTable();
        connectionProvider.closeStatement(createTable);
    }
    @After
    public void afterMethod() throws SQLException {
        connectionProvider.getCon().createStatement().execute("DROP TABLE role; DROP TABLE customer; ");

    }

    @Test
    public void insertCustomerReturnIndex() {
        Customer customer = new Customer();
        customer.setLogin("alex");
        customer.setPassword("123");
        customer.setName("alex");
        customer.setRole(Role.ROLE_BUYER);
        assertEquals(6, customerDAO.insertCustomer(customer));

    }

    @Test
    public void insertExistingCustomerReturn0() {
        Customer customer = new Customer();
        customer.setLogin("jarty12");
        customer.setPassword("123");
        customer.setName("alex");
        customer.setRole(Role.ROLE_BUYER);
        assertEquals(0, customerDAO.insertCustomer(customer));

    }

    @Test
    public void getCustomerWithExistedLoginAndPassReturnCustomer() {
        Customer c = customerDAO.processSignIn("jarty12", "esh141s.");
        assertEquals("jarty12", c.getLogin());
        assertEquals("esh141s.", c.getPassword());
        assertEquals("Ramesh", c.getName());
        assertEquals(1, c.getId());

    }

    @Test
    public void getCustomerWithNoExistedPassReturnNull(){
        Customer c = customerDAO.processSignIn("alex", "23423");

        assertNull(c);
    }

    @Test
    public void getCustomerWithNoExistedLoginReturnNull(){
        Customer c = customerDAO.processSignIn("ahmed", "esh141s.");
        assertNull(c);
    }

    @Test
    public void getCustomerByNotExistingIdAndLoginReturnEmptyArray() {

        ArrayList<Customer> customers = customerDAO.getCustomerByIdOrLogin("fs", 31213);
        assertEquals(0, customers.size());
    }

    @Test
    public void getCustomerByExistingIdReturnArray() {

        ArrayList<Customer> customers = customerDAO.getCustomerByIdOrLogin("fs", 1);
        assertEquals(1, customers.size());

    }

    @Test
    public void getCustomerByExistingLoginReturnArray() {

        ArrayList<Customer> customers = customerDAO.getCustomerByIdOrLogin("das21", 54);
        assertEquals(1, customers.size());

    }

    @Test
    public void getCustomersReturnArrayWithAllCustomers() {
        ArrayList<Customer> customers = customerDAO.getCustomers();
        assertEquals(5, customers.size());
    }

    @Test
    public void updateProductByExistingIdReturn1() {
        Customer customer  = new Customer();
        customer.setLogin("l1inS2");
        customer.setName("muxandr");
        customer.setPassword("ssad121!3");

        int updated = customerDAO.updateCustomer(customer, 1);
        assertEquals(1, updated);
    }

    private void fillInTable() {
        Statement statement = null;
        try {
            statement = connectionProvider.getCon().createStatement();

            String sql = "INSERT INTO `role` " +
                    "VALUES (1,'buyer')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO role " +
                    "VALUES (2,'admin')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO CUSTOMER " +
                    "VALUES (1,'jarty12', 'esh141s.', 'Ramesh', 1)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO CUSTOMER " +
                    "VALUES (2,'milka', 'mksd214', 'Mike', 1)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO CUSTOMER " +
                    "VALUES (3,'miketins', 'da2.w12', 'Maks', 1)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO CUSTOMER " +
                    "VALUES (4,'das21', 'd2.cw', 'Alexander', 1)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO CUSTOMER " +
                    "VALUES (5,'thazfr12', 'wqr12.ad', 'Miron', 1)";
            statement.executeUpdate(sql);
        }catch (Exception e){
            LOGGER.debug(e.getMessage(), e);
        }
        finally {
            connectionProvider.closeStatement(statement);
            connectionProvider.closeCon(connectionProvider.getCon());
        }
    }

}