package service.impl;

import database.dao.ProductDAO;
import database.entity.Product;
import exception.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.ProductService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    ProductDAO pd;

    ProductService productService;

    List<Product> products;

    @Before
    public void setUp() {
        productService = new ProductServiceImpl(pd);
        products = new ArrayList<>();
        products.add(new Product("fish", "foods", 10.2, 0.1));
        products.add(new Product("Java EE", "books", 5.2, 0.1));
        products.add(new Product("Iphone 6", "tech", 450.0, 2.1));

        when(pd.getProductById(BigInteger.valueOf(450))).thenReturn(new Product(450,"Iphone 6", "tech",new Date(), 450.0, 2.1));
        when(pd.getProducts()).thenReturn(products);
        when(pd.insertProduct(new Product("Iphone 6", "tech", 450.0, 2.1))).thenReturn(450);
        when(pd.insertProduct(new Product("Iphone 6", null, 450.0, 2.1))).thenReturn(0);
        when(pd.insertProduct(new Product(null, "tech", 450.0, 2.1))).thenReturn(0);
        when(pd.insertProduct(new Product("Iphone 6", "tech", null, 2.1))).thenReturn(0);
        when(pd.insertProduct(new Product("Iphone 6", "tech", 450.0, null))).thenReturn(0);
        when(pd.updateProduct(new Product("fish",null, 10.2, 0.2), BigInteger.valueOf(450))).thenReturn(0);
        when(pd.updateProduct(new Product("fish","foods", null, 0.2), BigInteger.valueOf(450))).thenReturn(0);
        when(pd.updateProduct(new Product("fish","foods", 10.2, null), BigInteger.valueOf(450))).thenReturn(0);
        when(pd.updateProduct(new Product(null,"foods", 10.2, 0.2), BigInteger.valueOf(450))).thenReturn(0);
        when(pd.updateProduct(new Product("fish","foods", 10.2, 0.2), BigInteger.valueOf(103))).thenReturn(1);
    }

    @Test
    public void searchProductExpectEntityNotFoundException() {
        List<Product> products;
        String message = null;

        try {
            products = productService.searchProductById(BigInteger.valueOf(24));
        }catch (EntityNotFoundException e){
            message = e.getMessage();
        }

        verify(pd).getProductById(BigInteger.valueOf(24));

        assertEquals("Search product returned null", message);
    }

    @Test
    public void searchProductExpectObjectWhenIdExisting() throws EntityNotFoundException {
        List<Product> products;

        products = productService.searchProductById(BigInteger.valueOf(450));

        verify(pd).getProductById(BigInteger.valueOf(450));

        assertEquals("Iphone 6", products.get(0).getName());
        assertEquals("tech", products.get(0).getCategory());
        assertEquals(Double.valueOf(450.0), products.get(0).getPrice());
        assertEquals(Double.valueOf(2.1), products.get(0).getPriceDiscount());

    }

    @Test
    public void getAllProducts() {
        List<Product> products = productService.getAllProducts();

        verify(pd).getProducts();

        assertEquals(3, products.size());
        assertEquals(products.get(0), new Product("fish", "foods", 10.2, 0.1));
        assertEquals(products.get(1), new Product("Java EE", "books", 5.2, 0.1));
        assertEquals(products.get(2), new Product("Iphone 6", "tech", 450.0, 2.1));

    }

    @Test
    public void addAnyProductReturnProduct() throws EntityNotFoundException {


        Product product = productService.addNewProduct(new Product("Iphone 6", "tech", 450.0, 2.1));

        verify(pd).insertProduct(Matchers.any(Product.class));

        assertEquals(Integer.valueOf(450), product.getId());
        assertEquals("Iphone 6",product.getName());
        assertEquals("tech", product.getCategory());
        assertEquals(Double.valueOf(450.0), product.getPrice());
        assertEquals(Double.valueOf(2.1), product.getPriceDiscount());

    }

    @Test
    public void addProductWithNullFieldExpectEntityNotFoundException(){

        Product product;
        String message=null;

        try {
            product = productService.addNewProduct(new Product("fish",null, 10.2, 0.2));
        } catch (EntityNotFoundException e){
            message = e.getMessage();
        }

        assertEquals("Product with id=0 is not exists", message);

    }

    @Test
    public void updateProductWithNullCategoryExpectEntityNotFoundException() {
        Product product;

        String message=null;

        try {
            product = productService.updateProductById(
                    new Product("fish",null, 10.2, 0.2), BigInteger.valueOf(450));
        } catch (EntityNotFoundException e){
            message = e.getMessage();
        }

        assertEquals("0 rows were updated", message);
    }

    @Test
    public void updateProductWithNullNameExpectEntityNotFoundException() {
        Product product;

        String message=null;

        try {
            product = productService.updateProductById(
                    new Product(null,"foods", 10.2, 0.2), BigInteger.valueOf(450));
        } catch (EntityNotFoundException e){
            message = e.getMessage();
        }

        assertEquals("0 rows were updated", message);
    }

    @Test
    public void updateProductWithNullPriceExpectEntityNotFoundException() {
        Product product;

        String message=null;

        try {
            product = productService.updateProductById(
                    new Product("fish","foods", null, 0.2), BigInteger.valueOf(450));
        } catch (EntityNotFoundException e){
            message = e.getMessage();
        }

        assertEquals("0 rows were updated", message);
    }

    @Test
    public void updateProductWithNullPriceDiscountExpectEntityNotFoundException() {
        Product product;

        String message=null;

        try {
            product = productService.updateProductById(
                    new Product("fish","foods", 10.2, null), BigInteger.valueOf(450));
        } catch (EntityNotFoundException e){
            message = e.getMessage();
        }

        assertEquals("0 rows were updated", message);
    }

    @Test
    public void updateProductExpectProduct() throws EntityNotFoundException {
        Product product = productService.updateProductById(new Product("fish","foods", 10.2, 0.2), BigInteger.valueOf(103));

        verify(pd).updateProduct(Matchers.any(Product.class), Matchers.any(BigInteger.class));

        assertEquals(Integer.valueOf(103), product.getId());
        assertEquals("fish",product.getName());
        assertEquals("foods", product.getCategory());
        assertEquals(Double.valueOf(10.2), product.getPrice());
        assertEquals(Double.valueOf(0.2), product.getPriceDiscount());
    }

}