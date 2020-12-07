package binding.response;

import database.entity.Transaction;

import java.util.Objects;

public class TransactionResponseBinding implements ResponseBinding{
    private Integer id;
    private Integer customerId;
    private Integer productId;
    private String purchTmst;
    private String paymentType;
    private String status;
    private String crdCardNumber;

    public TransactionResponseBinding(Integer id, Integer customerId, Integer productId,
                                      String purchTmst, String paymentType, String status,
                                      String crdCardNumber) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.purchTmst = purchTmst;
        this.paymentType = paymentType;
        this.status = status;
        this.crdCardNumber = crdCardNumber;
    }

    public TransactionResponseBinding(Transaction transaction) {
        this.id = transaction.getId();
        this.customerId = transaction.getCustomerId();
        this.productId = transaction.getProductId();
        this.purchTmst = transaction.getPurchTmst();
        this.paymentType = transaction.getPaymentType();
        this.status = transaction.getStatus();
        this.crdCardNumber = transaction.getCrdCardNumber();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getPurchTmst() {
        return purchTmst;
    }

    public void setPurchTmst(String purchTmst) {
        this.purchTmst = purchTmst;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCrdCardNumber() {
        return crdCardNumber;
    }

    public void setCrdCardNumber(String crdCardNumber) {
        this.crdCardNumber = crdCardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionResponseBinding that = (TransactionResponseBinding) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(purchTmst, that.purchTmst) &&
                Objects.equals(paymentType, that.paymentType) &&
                Objects.equals(status, that.status) &&
                Objects.equals(crdCardNumber, that.crdCardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, productId, purchTmst, paymentType, status, crdCardNumber);
    }
}
