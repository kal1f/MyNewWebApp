package database.dao.impl;

import database.connection.ConnectionProvider;
import database.connection.ConnectionProviderImpl;
import database.dao.TransactionDAO;
import database.entity.Transaction;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    private final ConnectionProvider connectionProvider;

    static final Logger LOGGER = Logger.getLogger(TransactionDAOImpl.class);

    public TransactionDAOImpl() {
        connectionProvider = new ConnectionProviderImpl();
    }

    public TransactionDAOImpl(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Transaction getTransactionById(BigInteger id) {
        Transaction transaction = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            con = connectionProvider.getCon();
            ps = con.prepareStatement("SELECT * FROM transaction WHERE id=?");
            ps.setString(1, String.valueOf(id));

            rs=ps.executeQuery();


            while(rs.next()){
                transaction = new Transaction();
                transaction.setCustomerId((rs.getInt(2)));
                transaction.setProductId(rs.getInt(3));
                transaction.setPurchTmst(rs.getTimestamp(4));
                transaction.setPaymentType(rs.getString(5));
                transaction.setStatus(rs.getString(6));
                transaction.setCrdCardNumber(rs.getString(7));
                transaction.setId(rs.getInt(1));
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
        return transaction;
    }

    @Override
    public List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            con = connectionProvider.getCon();
            ps = con.prepareStatement("select * from transaction ");

            rs = ps.executeQuery();

            while(rs.next()){
                Transaction transaction =
                        new Transaction(rs.getInt("id"),
                                rs.getInt("customer_id"),
                                rs.getInt("product_id"),
                                rs.getTimestamp("purch_tmst"),
                                rs.getString("payment_type"),
                                rs.getString("status"),
                                rs.getString("crd_card_number"));
                transactions.add(transaction);
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
        return transactions;
    }

    @Override
    public int insertTransaction(Transaction transaction) {
        int id = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            con = connectionProvider.getCon();
            ps = con.prepareStatement("INSERT INTO transaction (customer_id, product_id, purch_tmst, payment_type, status, crd_card_number)" +
                    " VALUES(?,?,CURRENT_TIMESTAMP ,?,'CREATED',?)", Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, transaction.getCustomerId());
            ps.setInt(2, transaction.getProductId());
            ps.setString(3, transaction.getPaymentType());
            //ps.setString(4, transaction.getStatus());
            ps.setString(4, transaction.getCrdCardNumber());

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
    public int updateTransactionStatusById(String status, BigInteger id) {
        int rows = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            con = connectionProvider.getCon();
            ps = con.prepareStatement("UPDATE transaction SET status=? WHERE id = ? ",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, status);
            ps.setBigDecimal(2, new BigDecimal(id));

            rows = ps.executeUpdate();


        } catch (Exception e) {
            LOGGER.debug(e.getMessage(), e);
        }
        finally {
            connectionProvider.closeRS(rs);
            connectionProvider.closeStatement(ps);
            connectionProvider.closeCon(con);
        }
        return rows;
    }
}
