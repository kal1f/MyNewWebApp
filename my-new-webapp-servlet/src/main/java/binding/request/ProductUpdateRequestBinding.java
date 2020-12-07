package binding.request;

import binding.response.ProductResponseBinding;
import database.entity.Customer;
import database.entity.Product;

public class ProductUpdateRequestBinding implements RequestBinding {
    private Integer id;
    private ProductResponseBinding product;

    public ProductUpdateRequestBinding(Integer id, ProductResponseBinding product) {
        this.id = id;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductResponseBinding getProduct() {
        return product;
    }

    public void setProduct(ProductResponseBinding product) {
        this.product = product;
    }

    @Override
    public Product toEntityObject() {
        return new Product(this.product.getName(), this.product.getCategory(),
                this.product.getPrice(), this.product.getPriceDiscount());
    }
}
