package database.entity;

import java.util.Date;
import java.util.Objects;

public class Product {

    private Integer id;
    private String name;
    private String category;
    private Date dateAdded;
    private Double price;
    private Double priceDiscount;

    public Product() {
    }

    public Product(String name, String category, Double price, Double priceDiscount) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.priceDiscount = priceDiscount;
    }

    public Product(Integer id, String name, String category,
                   Date dateAdded, Double price, Double priceDiscount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.dateAdded = dateAdded;
        this.price = price;
        this.priceDiscount = priceDiscount;
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
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(category, product.category) &&
                Objects.equals(dateAdded, product.dateAdded) &&
                Objects.equals(price, product.price) &&
                Objects.equals(priceDiscount, product.priceDiscount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, dateAdded, price, priceDiscount);
    }
}
