package binding.response;

import binding.request.RequestBinding;
import database.entity.Product;

import java.security.Timestamp;
import java.util.Date;
import java.util.Objects;

public class ProductResponseBinding implements ResponseBinding {

    private Integer id;
    private String name;
    private String category;
    private Date dateAdded;
    private Double price;
    private Double priceDiscount;

    public ProductResponseBinding(Integer id, String name, String category,
                                  Date dateAdded, Double price,
                                  Double priceDiscount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.dateAdded = dateAdded;
        this.price = price;
        this.priceDiscount = priceDiscount;
    }

    public ProductResponseBinding(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.category = product.getCategory();
        this.dateAdded = product.getDateAdded();
        this.price = product.getPrice();
        this.priceDiscount = product.getPriceDiscount();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(Double priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductResponseBinding that = (ProductResponseBinding) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(category, that.category) &&
                Objects.equals(dateAdded, that.dateAdded) &&
                Objects.equals(price, that.price) &&
                Objects.equals(priceDiscount, that.priceDiscount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, dateAdded, price, priceDiscount);
    }
}
