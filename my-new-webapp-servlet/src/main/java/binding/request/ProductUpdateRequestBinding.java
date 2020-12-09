package binding.request;

import database.entity.Product;

public class ProductUpdateRequestBinding implements RequestBinding {
    private Integer id;
    private ProductRequestBinding product;

    public ProductUpdateRequestBinding(Integer id, ProductRequestBinding product) {
        this.id = id;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductRequestBinding getProduct() {
        return product;
    }

    public void setProduct(ProductRequestBinding product) {
        this.product = product;
    }

    @Override
    public Product toEntityObject() {
        return new Product(this.product.getName(), this.product.getCategory(),
                this.product.getPrice(), this.product.getPriceDiscount());
    }
}
