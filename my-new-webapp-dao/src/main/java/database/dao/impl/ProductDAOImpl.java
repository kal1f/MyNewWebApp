package database.dao.impl;

import database.connection.ConnectionProvider;
import database.connection.ConnectionProviderImpl;
import database.dao.ProductDAO;
import database.entity.Product;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private final ConnectionProvider connectionProvider;

    static final Logger LOGGER = Logger.getLogger(ProductDAOImpl.class);

    public ProductDAOImpl(){

        this.connectionProvider = new ConnectionProviderImpl();
    }

    public ProductDAOImpl(ConnectionProvider connectionProvider) {

        this.connectionProvider = connectionProvider;
    }

    @Override
    public int insertProduct(Product product) {
        int id = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            con = connectionProvider.getCon();
            ps = con.prepareStatement("INSERT INTO product (name, category, date_added, price, price_discount) VALUES(?,?,current_date,?,?)",
                    new String[] {"ID"});

            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.setDouble(4, product.getPriceDiscount());

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
    public Product getProductById(BigInteger id) {
        Product product = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            con = connectionProvider.getCon();
            ps = con.prepareStatement("SELECT * FROM product WHERE id=?");
            ps.setString(1, String.valueOf(id));

            rs=ps.executeQuery();


            while(rs.next()){
                product = new Product();
                product.setName((rs.getString(2)));
                product.setCategory(rs.getString(3));
                product.setDateAdded(rs.getDate(4));
                product.setPrice(rs.getDouble(5));
                product.setPriceDiscount(rs.getDouble(6));
                product.setId(rs.getInt(1));
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

        return product;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            con = connectionProvider.getCon();
            ps = con.prepareStatement("SELECT * FROM product");

            rs = ps.executeQuery();

            while(rs.next()){
                Product product = new Product(rs.getInt("id"), rs.getString("name"),
                        rs.getString("category"), rs.getDate("date_added"),
                        rs.getDouble("price"), rs.getDouble("price_discount"));
                products.add(product);
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
        return products;
    }

    @Override
    public int updateProduct(Product product, BigInteger id) {
        int updated = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            con = connectionProvider.getCon();
            ps = con.prepareStatement("UPDATE product SET name=?," +
                    "category=?," +
                    "date_added=CURRENT_DATE," +
                    "price=?," +
                    "price_discount=? " +
                    "WHERE id=? ", new String[] {"ID"});

            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.setDouble(4, product.getPriceDiscount());
            ps.setBigDecimal(5, new BigDecimal(id));

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
}
