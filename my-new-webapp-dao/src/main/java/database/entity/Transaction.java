package database.entity;

import database.entity.enums.PaymentType;
import database.entity.enums.Status;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Objects;

public class Transaction {
    private Integer id;
    private Integer customerId;
    private Integer productId;
    private Timestamp purchTmst;
    private PaymentType paymentType;
    private Status status;
    private String crdCardNumber;

    public Transaction() {
    }

    public Transaction(Integer customerId, Integer productId, String paymentType, String status, String crdCardNumber) {
        this.customerId = customerId;
        this.productId = productId;
        this.paymentType = PaymentType.valueOf(paymentType);
        this.status = Status.valueOf(status);
        this.crdCardNumber = crdCardNumber;
    }

    public Transaction(Integer id, Integer customerId, Integer productId, String paymentType, String status, String crdCardNumber) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.paymentType = PaymentType.valueOf(paymentType);
        this.status = Status.valueOf(status);
        this.crdCardNumber = crdCardNumber;
    }

    public Transaction(Integer id, Integer customerId, Integer productId, Timestamp purchTmst,
                       PaymentType paymentType, Status status, String crdCardNumber) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.purchTmst = purchTmst;
        this.paymentType = paymentType;
        this.status = status;
        this.crdCardNumber = crdCardNumber;
    }

    public Transaction(Integer id, Integer customerId, Integer productId, Timestamp purchTmst,
                       String paymentType, String status, String crdCardNumber) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.purchTmst = purchTmst;
        this.paymentType = PaymentType.valueOf(paymentType);
        this.status = Status.valueOf(status);
        this.crdCardNumber = crdCardNumber;
    }

    public Transaction(Integer customerId, Integer productId,
                       String paymentType, String crdCardNumber) {
        this.customerId = customerId;
        this.productId = productId;
        this.paymentType = PaymentType.valueOf(paymentType);
        this.crdCardNumber = crdCardNumber;
    }

    public Transaction(Integer id, String status) {
        this.id = id;
        this.status = Status.valueOf(status);
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

    public String getPurchTmst(){
        return purchTmst.toString();
    }

    public void setPurchTmst(Timestamp purchTmst) {
        this.purchTmst = purchTmst;
    }

    public String getPaymentType() {
        return paymentType.toString();
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = PaymentType.valueOf(paymentType);
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStatus() {
        return status.toString();
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
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
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(purchTmst, that.purchTmst) &&
                Objects.equals(paymentType, that.paymentType) &&
                Objects.equals(status, that.status) &&
                Objects.equals(crdCardNumber, that.crdCardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, purchTmst, paymentType, status, crdCardNumber);
    }

}
