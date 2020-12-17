package service.impl;

import database.dao.TransactionDAO;
import database.dao.impl.TransactionDAOImpl;
import database.entity.Customer;
import database.entity.Product;
import database.entity.Transaction;
import exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import service.TransactionService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionDAO td;

    static final Logger LOGGER = Logger.getLogger(TransactionServiceImpl.class);

    public TransactionServiceImpl(Properties properties) {
        td = new TransactionDAOImpl(properties);
    }

    public TransactionServiceImpl(TransactionDAO transactionDAO){
        this.td = transactionDAO;
    }

    @Override
    public List<Transaction> getTransactions() {
        return td.getTransactions();
    }

    @Override
    public List<Transaction> searchTransactionById(BigInteger id) throws EntityNotFoundException {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(td.getTransactionById(id));

        if(transactions.isEmpty()){
            LOGGER.debug("getTransactionById(id) returned null");
            throw new EntityNotFoundException("Search transaction returned null");
        }

        return transactions;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) throws EntityNotFoundException {
        int id = td.insertTransaction(transaction);

        if(id != 0){
            transaction.setId(id);
            return td.getTransactionById(BigInteger.valueOf(id));
        }
        else {
            LOGGER.debug("Transaction was not created in database");
            throw new EntityNotFoundException("Transaction with id=0 is not exists");
        }
    }

    @Override
    public Transaction updateStatusById(String status, BigInteger id) throws EntityNotFoundException {
        int updatedRows = td.updateTransactionStatusById(status, id);

        if(updatedRows != 0){

            return td.getTransactionById(id);
        }
        else {
            LOGGER.debug("updateTransactionStatusById(String status, BigInteger id) returned null");
            throw new EntityNotFoundException("Transaction is not updated");
        }
    }

}
