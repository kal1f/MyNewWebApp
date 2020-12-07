package service.impl;

import database.dao.TransactionDAO;
import database.entity.Transaction;
import exception.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.TransactionService;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    @Mock
    TransactionDAO td;

    TransactionService transactionService;

    List<Transaction> transactions;

    @Before
    public void setUp(){
        transactionService = new TransactionServiceImpl(td);
        transactions = new ArrayList<>();
        transactions.add(new Transaction(101,101, 300,"CREDITCARD", "COMPLETE", "213131dsda23131"));
        transactions.add(new Transaction(102, 325, 300,"CREDITCARD","COMPLETE", "2dasfaddw131" ));
        transactions.add(new Transaction(103, 325, 320,"CREDITCARD","COMPLETE", "2dasfaddw131" ));

        when(td.getTransactions()).thenReturn(transactions);
        when(td.insertTransaction(Matchers.any(Transaction.class))).thenReturn(102);
        when(td.getTransactionById(BigInteger.valueOf(120))).thenReturn(
                new Transaction(120, 101,213,"CREDITCARD", "COMPLETE","612DASDA21DAS"));
        when(td.updateTransactionStatusById("COMPLETE", BigInteger.valueOf(120))).thenReturn(1);
    }

    @Test
    public void getTransactions() {
        List<Transaction> customers = transactionService.getTransactions();

        verify(td).getTransactions();

        assertEquals(3, customers.size());
        assertEquals(customers.get(0), new Transaction(101,101, 300,"CREDITCARD", "COMPLETE", "213131dsda23131"));
        assertEquals(customers.get(1), new Transaction(102, 325, 300,"CREDITCARD","COMPLETE", "2dasfaddw131" ));
        assertEquals(customers.get(2), new Transaction(103, 325, 320,"CREDITCARD","COMPLETE", "2dasfaddw131" ));
    }

    @Test
    public void createTransactionExpectTransactionObject() throws EntityNotFoundException {
        Transaction transaction = transactionService.createTransaction(
                new Transaction(325, 320,"CREDITCARD","COMPLETE", "2dasfaddw131" ));

        verify(td).insertTransaction(Matchers.any(Transaction.class));

        assertEquals(Integer.valueOf(102), transaction.getId());
        assertEquals(Integer.valueOf(325),transaction.getCustomerId());
        assertEquals(Integer.valueOf(320), transaction.getProductId());
        assertEquals("CREDITCARD", transaction.getPaymentType());
        assertEquals("COMPLETE", transaction.getStatus());
        assertEquals("2dasfaddw131", transaction.getCrdCardNumber());
    }

    @Test
    public void createTransactionExpectEntityNotFoundException() {

        when(td.insertTransaction(Matchers.any(Transaction.class))).thenReturn(0);

        String message = null;
        try {
            transactionService.createTransaction(new Transaction(325, 320,"CREDITCARD","COMPLETE", "2dasfaddw131" ));
        } catch (EntityNotFoundException e) {
            message = e.getMessage();
        }

        assertEquals("Transaction with id=0 is not exists", message);
    }

    @Test
    public void updateStatusByIdExpectTransactionObject() throws EntityNotFoundException {
        Transaction transaction = transactionService.updateStatusById("COMPLETE", BigInteger.valueOf(120));

        verify(td).updateTransactionStatusById("COMPLETE", BigInteger.valueOf(120));
        verify(td).getTransactionById(BigInteger.valueOf(120));

        assertEquals(Integer.valueOf(120),transaction.getId());
        assertEquals(Integer.valueOf(101), transaction.getCustomerId());
        assertEquals(Integer.valueOf(213), transaction.getProductId());
        assertEquals("CREDITCARD", transaction.getPaymentType());
        assertEquals("COMPLETE", transaction.getStatus());
        assertEquals("612DASDA21DAS", transaction.getCrdCardNumber());
    }

    @Test
    public void updateStatusByNotExistingIdExpectEntityNotFoundException(){
        String message = null;

        try {
            transactionService.updateStatusById("COMPLETE", BigInteger.valueOf(10));
        } catch (EntityNotFoundException e) {
            message = e.getMessage();
        }

        assertEquals("Transaction is not updated", message);
    }
}