package binding.response;

import database.entity.Product;
import java.util.List;
import java.util.Objects;

public class ProductsResponseBinding implements ResponseBinding{
    private List<Product> products;

    public ProductsResponseBinding(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsResponseBinding that = (ProductsResponseBinding) o;
        return Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products);
    }
}
