package service;

import database.entity.Transaction;
import exception.EntityNotFoundException;

import java.math.BigInteger;
import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactions();

    List<Transaction> searchTransactionById(BigInteger id) throws EntityNotFoundException;

    Transaction createTransaction(Transaction transaction) throws EntityNotFoundException;

    Transaction updateStatusById(String status, BigInteger id) throws EntityNotFoundException;
}
