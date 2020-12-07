package binding.response;

import database.entity.Transaction;

import java.util.List;
import java.util.Objects;

public class TransactionsResponseBinding implements ResponseBinding {
    private List<Transaction> transactions;

    public TransactionsResponseBinding(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionsResponseBinding that = (TransactionsResponseBinding) o;
        return Objects.equals(transactions, that.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactions);
    }
}
