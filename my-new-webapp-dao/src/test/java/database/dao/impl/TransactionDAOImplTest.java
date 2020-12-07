package database.dao.impl;

import database.connection.ConnectionProvider;
import database.connection.ConnectionProviderTestImpl;
import database.dao.TransactionDAO;
import database.entity.Transaction;
import org.apache.log4j.Logger;
import org.junit.*;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class TransactionDAOImplTest {

    static ConnectionProvider connectionProvider;
    static TransactionDAO transactionDAO;

    static final Logger LOGGER = Logger.getLogger(TransactionDAOImplTest.class);

    @BeforeClass
    public static void  databaseUp() {
        connectionProvider = new ConnectionProviderTestImpl();
        transactionDAO = new TransactionDAOImpl(connectionProvider);
    }
    @AfterClass
    public static void databaseDown(){
        connectionProvider.closeCon(connectionProvider.getCon());
    }
    @Before
    public void beforeMethod() throws SQLException {
        Statement createTable = connectionProvider.getCon().createStatement();
        createTable.execute("CREATE TABLE transaction (id INT PRIMARY KEY AUTO_INCREMENT," +
                " customer_id INT NOT NULL," +
                " product_id INT NOT NULL," +
                " purch_tmst TIMESTAMP NOT NULL," +
                " payment_type VARCHAR(15) NOT NULL, " +
                "status VARCHAR(15) NOT NULL," +
                "crd_card_number VARCHAR(20))");
        fillInTable();
        connectionProvider.closeStatement(createTable);
    }
    @After
    public void afterMethod() throws SQLException {
        connectionProvider.getCon().createStatement().execute("DROP TABLE transaction ");
    }

    @Test
    public void getTransactionByExistingIdReturnTransaction() {
        Transaction transaction = transactionDAO.getTransactionById(BigInteger.valueOf(101));
        assertNotNull(transaction);
    }

    @Test
    public void getTransactionByNotExistingIdReturnNull() {
        Transaction transaction = transactionDAO.getTransactionById(BigInteger.valueOf(120));
        assertNull(transaction);
    }

    @Test
    public void getTransactionsReturnAllTransactions() {
        List<Transaction> transactions = transactionDAO.getTransactions();
        assertEquals(5, transactions.size());
    }

    @Test
    public void insertTransactionReturnIndex() {
        Transaction transaction  = new Transaction();
        transaction.setCustomerId(101);
        transaction.setProductId(451);
        transaction.setPaymentType("CREDITCARD");
        transaction.setCrdCardNumber("213FSDFC21XZ256");
        assertEquals(106, transactionDAO.insertTransaction(transaction));
    }

    @Test
    public void updateTransactionStatusByExistingIdReturn1() {
        int updated = transactionDAO.updateTransactionStatusById("COMPLETE", BigInteger.valueOf(101));
        assertEquals(1,updated);
    }

    @Test
    public void updateTransactionStatusByNotExistingIdReturn0() {
        int updated = transactionDAO.updateTransactionStatusById("COMPLETE", BigInteger.valueOf(65));
        assertEquals(0,updated);
    }


    private void fillInTable() {
        Statement statement = null;
        try {
            statement = connectionProvider.getCon().createStatement();

            String sql = "INSERT INTO TRANSACTION " +
                    "VALUES (101,101, 201, CURRENT_TIMESTAMP , 'CASH', 'CREATED', 'X1214568241')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO TRANSACTION " +
                    "VALUES (102,203, 213,CURRENT_TIMESTAMP, 'CREDITCARD', 'CREATED', 'X1214209851')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO TRANSACTION " +
                    "VALUES (103,212, 122, CURRENT_TIMESTAMP, 'CREDITCARD', 'BOXING', 'X0984212X21')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO TRANSACTION " +
                    "VALUES (104,123, 412, CURRENT_TIMESTAMP, 'CREDITCARD', 'CREATED', 'X121312X21')";
            statement.executeUpdate(sql);
            sql = "INSERT INTO TRANSACTION " +
                    "VALUES (105,100, 4124, CURRENT_TIMESTAMP, 'LOYALTY', 'COMPLETE','X1214212X21')";
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