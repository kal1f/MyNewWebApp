package database.dao.impl;

import database.connection.ConnectionProvider;
import database.connection.ConnectionProviderTestImpl;
import database.dao.ProductDAO;
import database.entity.Product;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.*;

import static org.junit.Assert.*;

public class ProductDAOImplTest {

    static ConnectionProvider connectionProvider;
    static ProductDAO productDAO;

    static final Logger LOGGER = Logger.getLogger(ProductDAOImplTest.class);

    @BeforeClass
    public static void databaseUp(){
        connectionProvider = new ConnectionProviderTestImpl();
        productDAO = new ProductDAOImpl(connectionProvider);
    }

    @AfterClass
    public static void databaseDown(){
        connectionProvider.closeCon(connectionProvider.getCon());
    }

    @Before
    public void beforeMethod() throws SQLException {
        Statement createTable = connectionProvider.getCon().createStatement();
        createTable.execute("CREATE TABLE product(ID INT PRIMARY KEY AUTO_INCREMENT," +
                " name VARCHAR(15) NOT NULL, " +
                "category VARCHAR(15) NOT NULL, " +
                "date_added DATE NOT  NULL, " +
                "price decimal(10,2) NOT NULL, " +
                "price_discount decimal(10,2))");
        fillInTable();
        connectionProvider.closeStatement(createTable);
    }

    @After
    public void afterMethod() throws SQLException {
        connectionProvider.getCon().createStatement().execute("DROP TABLE product");
    }

    @Test
    public void getProductByNotExistingIdReturnNull() {
        Product product = productDAO.getProductById(BigInteger.valueOf(120));
        assertNull(product);
    }

    @Test
    public void getProductByExistingIdReturnProduct() {
        Product product = productDAO.getProductById(BigInteger.valueOf(101));

        assertNotNull(product);
    }

    @Test
    public void getProductsReturnArrayWithAllProducts() {
        List<Product> products = productDAO.getProducts();
        assertEquals(5, products.size());
    }

    @Test
    public void insertProductReturnIndex() {
        Product product  = new Product();
        product.setName("RedBull");
        product.setCategory("products");
        product.setPrice(123.53);
        product.setPriceDiscount(1.2);
        assertEquals(106, productDAO.insertProduct(product));
    }

    @Test
    public void updateProductByExistingIdReturn1() {
        Product product  = new Product();
        product.setName("RedBull");
        product.setCategory("products");
        product.setPrice(123.53);
        product.setPriceDiscount(1.2);

        int updated = productDAO.updateProduct(product, BigInteger.valueOf(101));
        assertEquals(1, updated);
    }

    @Test
    public void updateProductByNotExistingIdReturn0() {
        Product product  = new Product();
        product.setName("RedBull");
        product.setCategory("products");
        product.setPrice(123.53);
        product.setPriceDiscount(1.2);

        int updated = productDAO.updateProduct(product, BigInteger.valueOf(65));
        assertEquals(0,updated);
    }

    private void fillInTable() {
    Statement statement = null;
    try {
        statement = connectionProvider.getCon().createStatement();

        String sql = "INSERT INTO product " +
                "VALUES (101,'T-shirt', 'clothes', current_date, 20.2, 0.12)";
        statement.executeUpdate(sql);
        sql = "INSERT INTO product " +
                "VALUES (102,'milka', 'foods', current_date, 0.7, null)";
        statement.executeUpdate(sql);
        sql = "INSERT INTO product " +
                "VALUES (103,'Jeans', 'clothes', current_date, 30, 0.1)";
        statement.executeUpdate(sql);
        sql = "INSERT INTO product " +
                "VALUES (104,'MacBook', 'tech', current_date, 2000, null)";
        statement.executeUpdate(sql);
        sql = "INSERT INTO product " +
                "VALUES (105,'Iphone', 'tech', current_date, 600, 0.5)";
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