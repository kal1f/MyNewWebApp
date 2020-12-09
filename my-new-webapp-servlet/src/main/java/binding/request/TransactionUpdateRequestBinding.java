package binding.request;

import database.entity.Transaction;

public class TransactionUpdateRequestBinding implements RequestBinding{

    private Integer id;
    private String status;

    public TransactionUpdateRequestBinding(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Transaction toEntityObject() {
        return new Transaction(id, status);
    }
}
