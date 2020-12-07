package database.dao;

import database.entity.Transaction;

import java.math.BigInteger;
import java.util.List;

public interface TransactionDAO {
    Transaction getTransactionById(BigInteger id);
    List<Transaction> getTransactions();
    int insertTransaction(Transaction transaction);
    int updateTransactionStatusById(String status, BigInteger id);
}
