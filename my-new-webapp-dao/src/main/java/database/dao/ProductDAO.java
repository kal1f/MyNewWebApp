package database.dao;

import database.entity.Product;

import java.math.BigInteger;
import java.util.List;

public interface ProductDAO {
    int insertProduct(Product product);
    Product getProductById(BigInteger id);
    List<Product> getProducts();
    int updateProduct(Product product, BigInteger id);
}
