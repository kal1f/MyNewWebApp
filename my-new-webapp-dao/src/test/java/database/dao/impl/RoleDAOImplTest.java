package database.dao.impl;

import database.connection.ConnectionProvider;
import database.connection.ConnectionProviderTestImpl;
import database.dao.ProductDAO;
import database.dao.RoleDAO;
import database.entity.Product;
import database.entity.Role;
import org.apache.log4j.Logger;
import org.junit.*;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class RoleDAOImplTest {

    static ConnectionProvider connectionProvider;
    static RoleDAO roleDAO;

    static final Logger LOGGER = Logger.getLogger(RoleDAOImplTest.class);

    @BeforeClass
    public static void databaseUp(){
        connectionProvider = new ConnectionProviderTestImpl();
        roleDAO = new RoleDAOImpl(connectionProvider);
    }

    @AfterClass
    public static void databaseDown(){
        connectionProvider.closeCon(connectionProvider.getCon());
    }

    @Before
    public void beforeMethod() throws SQLException {
        Statement createTable = connectionProvider.getCon().createStatement();
        createTable.execute("CREATE TABLE role(ID INT PRIMARY KEY AUTO_INCREMENT," +
                " name VARCHAR(15) NOT NULL unique )");
        fillInTable();
        connectionProvider.closeStatement(createTable);
    }

    @After
    public void afterMethod() throws SQLException {
        connectionProvider.getCon().createStatement().execute("DROP TABLE role");
    }

    @Test
    public void getRoleByExistingIdReturnRole() {
        Role role = roleDAO.getRoleById(1);

        assertNotNull(role);
    }

    @Test
    public void getRoleByExistingIdReturnNull() {
        Role role = roleDAO.getRoleById(10);

        assertNull(role);
    }

    @Test
    public void getRolesReturnRolesArray() {
        List<Role> roles = roleDAO.getRoles();
        assertEquals(2, roles.size());
    }

    @Test
    public void insertRoleReturnIndex() {
        assertEquals(3, roleDAO.insertRole("employer"));
    }

    @Test
    public void insertExistingRoleReturn0() {
        assertEquals(0, roleDAO.insertRole("admin"));
    }

    @Test
    public void updateRoleByExistingStatusReturn0() {
        int updated = roleDAO.updateRoleById(new Role(1, "admin"));
        assertEquals(0,updated);
    }

    @Test
    public void updateRoleByNewStatusReturn1() {
        int updated = roleDAO.updateRoleById(new Role(1, "dasdd"));
        assertEquals(1,updated);
    }

    @Test
    public void updateTransactionStatusByNotExistingIdReturn0() {
        int updated = roleDAO.updateRoleById(new Role(21, "admin"));
        assertEquals(0,updated);
    }

    private void fillInTable(){
        Statement statement = null;
        try {
            statement = connectionProvider.getCon().createStatement();

            String sql = "INSERT INTO role " +
                    "VALUES (1,'buyer')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO role " +
                    "VALUES (2,'admin')";
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