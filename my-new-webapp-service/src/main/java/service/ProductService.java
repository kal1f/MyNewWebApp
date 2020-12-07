package service;

import database.entity.Product;
import exception.EntityNotFoundException;

import java.math.BigInteger;
import java.util.List;

public interface ProductService {
    List<Product> searchProductById(BigInteger id) throws EntityNotFoundException;
    Product addNewProduct(Product product) throws EntityNotFoundException;
    Product updateProductById(Product product, BigInteger id) throws EntityNotFoundException;
    List<Product> getAllProducts();

}
