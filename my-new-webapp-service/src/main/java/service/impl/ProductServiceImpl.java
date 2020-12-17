package service.impl;

import database.dao.ProductDAO;
import database.dao.impl.ProductDAOImpl;
import database.entity.Product;
import exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import service.ProductService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProductServiceImpl implements ProductService {

    private final ProductDAO pd;

    static final Logger LOGGER = Logger.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(Properties properties) {
        pd = new ProductDAOImpl(properties);
    }

    public ProductServiceImpl(ProductDAO productDAO){
        this.pd = productDAO;
    }

    @Override
    public List<Product> searchProductById(BigInteger id) throws EntityNotFoundException {
        List<Product> products = new ArrayList<>();
        Product product = pd.getProductById(id);

        if(product == null){
            LOGGER.debug("getProductById(id) returned null");
            throw new EntityNotFoundException("Search product returned null");
        }
        products.add(product);
        return products;
    }

    @Override
    public Product addNewProduct(Product product) throws EntityNotFoundException {
        int id = pd.insertProduct(product);

        if(id != 0){
            return pd.getProductById(BigInteger.valueOf(id));
        }
        else {
            LOGGER.debug("Product was not created in database");
            throw new EntityNotFoundException("Product with id=0 is not exists");
        }
    }

    @Override
    public Product updateProductById(Product product, BigInteger id) throws EntityNotFoundException {
        int updatedRows = pd.updateProduct(product, id);

        if(updatedRows != 0){
            product.setId(id.intValue());
            return product;
        }
        else {
            LOGGER.debug("Product was not updated in database");
            throw new EntityNotFoundException("0 rows were updated");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return pd.getProducts();
    }


}
