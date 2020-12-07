package binding.request;

import database.entity.Product;

public class ProductRequestBinding implements RequestBinding{

    private String name;
    private String category;
    private Double price;
    private Double priceDiscount;

    public ProductRequestBinding(String name, String category, Double price, Double priceDiscount) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.priceDiscount = priceDiscount;
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
    public Product toEntityObject() {
        return new Product(name, category, price, priceDiscount);
    }
}
