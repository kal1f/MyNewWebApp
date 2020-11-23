package database;

import database.connection.ConnectionProvider;

import database.connection.ConnectionProviderTestImpl;

import database.entity.Customer;
import org.junit.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CustomerDAOImplTest {

    static ConnectionProvider connectionProvider;
    static CustomerDAO customerDAO;

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
        createTable.execute("CREATE TABLE customer(customer VARCHAR(50), pass_ VARCHAR(50), name_ VARCHAR(50), id INT PRIMARY KEY AUTO_INCREMENT )");
        fillInTable();
        connectionProvider.closeStatement(createTable);
    }
    @After
    public void afterMethod() throws SQLException {
        connectionProvider.getCon().createStatement().execute("DROP TABLE customer");
    }

    @Test
    public void insertCustomerReturnZero() {
        Customer customer = new Customer();
        customer.setLogin("alex");
        customer.setPassword("123");
        customer.setName("alex");
        assertEquals(6, customerDAO.insertCustomer(customer));
    }

    @Test
    public void getCustomerWithExistedLoginAndPassReturnCustomer() {
        Customer c = customerDAO.getCustomer("jarty12", "esh141s.");
        assertEquals("jarty12", c.getLogin());
        assertEquals("esh141s.", c.getPassword());
        assertEquals("Ramesh", c.getName());
        assertEquals(1, c.getId());

    }

    @Test
    public void getCustomerWithNoExistedPassReturnNull(){
        Customer c = customerDAO.getCustomer("alex", "23423");
        assertNull(c.getLogin());
        assertNull(c.getPassword());
        assertNull(c.getName());
        assertEquals(0 , c.getId());
    }

    @Test
    public void getCustomerWithNoExistedLoginReturnNull(){
        Customer c = customerDAO.getCustomer("ahmed", "esh141s.");
        assertNull(c.getLogin());
        assertNull(c.getPassword());
        assertNull(c.getName());
        assertEquals(0, c.getId());
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
    public void isCustomerExistReturnTrueWithExistingLoginAndPassword() {

        boolean existed = customerDAO.isCustomerExist("thazfr12", "wqr12.ad");
        assertTrue(existed);

    }

    @Test
    public void isCustomerExistReturnFalseWithNoExistingLogin() {

        boolean existed = customerDAO.isCustomerExist("ahmed", "123");
        assertFalse(existed);

    }

    @Test
    public void isCustomerExistReturnFalseWithNoExistingPassword() {

        boolean existed = customerDAO.isCustomerExist("alex", "2131254");
        assertFalse(existed);

    }

    @Test
    public void isCustomerExistReturnFalseWithNoExistingPasswordAndLogin() {

        boolean existed = customerDAO.isCustomerExist("ahmed", "2435436564");
        assertFalse(existed);

    }
    private void fillInTable() {
        Statement statement = null;
        try {
            statement = connectionProvider.getCon().createStatement();

            String sql = "INSERT INTO CUSTOMER " +
                    "VALUES ('jarty12', 'esh141s.', 'Ramesh', 1)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO CUSTOMER " +
                    "VALUES ('milka', 'mksd214', 'Mike', 2)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO CUSTOMER " +
                    "VALUES ('miketins', 'da2.w12', 'Maks', 3)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO CUSTOMER " +
                    "VALUES ('das21', 'd2.cw', 'Alexander', 4)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO CUSTOMER " +
                    "VALUES ('thazfr12', 'wqr12.ad', 'Miron', 5)";
            statement.executeUpdate(sql);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            connectionProvider.closeStatement(statement);
            connectionProvider.closeCon(connectionProvider.getCon());
        }
    }

}