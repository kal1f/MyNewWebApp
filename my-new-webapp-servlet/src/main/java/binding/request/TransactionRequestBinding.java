package binding.request;

import database.entity.Transaction;


public class TransactionRequestBinding implements RequestBinding{
    private Integer customerId;
    private Integer productId;
    private String paymentType;
    private String crdCardNumber;

    public TransactionRequestBinding(Integer customerId, Integer productId,
                                     String paymentType, String crdCardNumber) {
        this.customerId = customerId;
        this.productId = productId;
        this.paymentType = paymentType;
        this.crdCardNumber = crdCardNumber;
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCrdCardNumber() {
        return crdCardNumber;
    }

    public void setCrdCardNumber(String crdCardNumber) {
        this.crdCardNumber = crdCardNumber;
    }

    @Override
    public Transaction toEntityObject() {
        return new Transaction(customerId, productId, paymentType, crdCardNumber);
    }
}
