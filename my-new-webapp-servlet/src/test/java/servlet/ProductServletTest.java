package servlet;


import binding.request.ProductRequestBinding;
import binding.request.ProductUpdateRequestBinding;
import binding.response.*;
import database.entity.Product;
import exception.EntityNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import service.ProductService;
import util.DataToJson;
import util.JsonToData;
import util.validator.DataValidator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServletTest {
    @Mock
    JsonToData jsonToData;
    @Mock
    DataToJson dataToJson;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    ProductService productService;
    @Mock
    DataValidator dataValidator;
    @Mock
    ProductUpdateRequestBinding productUpdateBinding;
    @Mock
    ProductRequestBinding productBinding;

    ProductServlet servlet;

    @Before
    public void setUp() throws IOException {

        servlet = new ProductServlet(dataToJson, jsonToData, dataValidator, productService);
        when(jsonToData.jsonToProductUpdateData(request)).thenReturn(productUpdateBinding);


        when(dataValidator.isIdValid("2124123")).thenReturn(false);
        when(dataValidator.isIdValid("123131231")).thenReturn(true);
        when(dataValidator.isIdValid("121")).thenReturn(true);

        when(productUpdateBinding.toEntityObject()).thenReturn(Mockito.mock(Product.class));
        when(productUpdateBinding.toEntityObject().getName()).thenReturn("lemon");
        when(productUpdateBinding.toEntityObject().getCategory()).thenReturn("foods");
        when(productUpdateBinding.toEntityObject().getPrice()).thenReturn(12.3);
        when(productUpdateBinding.toEntityObject().getPriceDiscount()).thenReturn(8.3);

        doNothing().when(dataToJson).processResponse(Matchers.any(HttpServletResponse.class), anyInt(), Matchers.any(ResponseBinding.class));

        when(jsonToData.jsonToProductData(request)).thenReturn(productBinding);
        when(productBinding.getName()).thenReturn("lemon");
        when(productBinding.getCategory()).thenReturn("foods");
        when(productBinding.getPrice()).thenReturn(12.3);
        when(productBinding.getPriceDiscount()).thenReturn(0.1);
    }

    @After
    public void clean(){
        reset(dataToJson, request, response, productService, dataValidator);
    }

    @Test
    public void doPutIOExceptionExpectStatus422() throws IOException {

        doThrow(new IOException()).when(jsonToData).jsonToProductUpdateData(request);

        servlet.doPut(request, response);

        verify(dataToJson).processResponse(response, 422,ErrorResponseBinding.ERROR_RESPONSE_422);

    }

    @Test
    public void doPutValidInputDataExpectStatus404() throws EntityNotFoundException, IOException {

        when(productUpdateBinding.getId()).thenReturn(90);
        when(dataValidator.isProductUpdateDataValid(productUpdateBinding.getId(), productUpdateBinding.toEntityObject().getName(),
                productUpdateBinding.toEntityObject().getCategory(), productUpdateBinding.toEntityObject().getPrice(),
                productUpdateBinding.toEntityObject().getPriceDiscount())).thenReturn(true);

        doThrow(new EntityNotFoundException()).when(productService).updateProductById(Matchers.any(Product.class), Matchers.any(BigInteger.class));

        servlet.doPut(request, response);

        verify(dataToJson).processResponse(response, 404,  ErrorResponseBinding.ERROR_RESPONSE_404);

    }

    @Test
    public void doPutValidDataExpectStatus200() throws EntityNotFoundException, IOException {
        when(jsonToData.jsonToProductUpdateData(request)).thenReturn(productUpdateBinding);

        when(productUpdateBinding.getId()).thenReturn(90);
        when(dataValidator.isProductUpdateDataValid(productUpdateBinding.getId(), productUpdateBinding.toEntityObject().getName(),
                productUpdateBinding.toEntityObject().getCategory(), productUpdateBinding.toEntityObject().getPrice(),
                productUpdateBinding.toEntityObject().getPriceDiscount())).thenReturn(true);

        Product p  = new Product(101,"lemon", "foods", new Date(),12.3, 0.1);

        when(productService.updateProductById(Matchers.any(Product.class), Matchers.any(BigInteger.class))).thenReturn(p);

        servlet.doPut(request, response);

        verify(dataToJson).processResponse(response, 200, new ProductResponseBinding(p));

    }

    @Test
    public void doPutIdNullDataExpectStatus400() throws IOException {

        when(productUpdateBinding.getId()).thenReturn(null);
        when(dataValidator.isProductUpdateDataValid(productUpdateBinding.getId(), productUpdateBinding.toEntityObject().getName(),
                productUpdateBinding.toEntityObject().getCategory(), productUpdateBinding.toEntityObject().getPrice(),
                productUpdateBinding.toEntityObject().getPriceDiscount())).thenReturn(false);


        servlet.doPut(request, response);

        verify(dataToJson).processResponse(response, 400,
                new ErrorResponseBinding(400, "Input data in not valid"));

    }

    @Test
    public void doGetIdNullExpectStatus200(){

        when(request.getParameter("id")).thenReturn(null);
        List<Product> p = new ArrayList<>();

        p.add(new Product(132,"lemon", "foods",new Date(), 12.3, 0.1));

        when(productService.getAllProducts()).thenReturn(p);

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 200, new ProductsResponseBinding(p) );
    }

    @Test
    public void doGetIdValidAndProductNotExistsProductWithValidIdExpectStatus404() throws EntityNotFoundException{

        when(request.getParameter("id")).thenReturn("123131231");

        doThrow(new EntityNotFoundException()).when(productService).searchProductById(Matchers.any(BigInteger.class));

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 404,  ErrorResponseBinding.ERROR_RESPONSE_404);

    }

    @Test
    public void doGetIdValidAndProductExistsExpectStatus200() throws EntityNotFoundException{
        when(request.getParameter("id")).thenReturn("121");

        List<Product> c = new ArrayList<>();

        c.add(new Product(121,"lemon","foods", new Date(), 12.3, 0.1));

        when(productService.searchProductById(BigInteger.valueOf(121))).thenReturn(c);

        servlet.doGet(request, response);

        verify(dataToJson).processResponse(response, 200, new ProductsResponseBinding(c));

    }

    @Test
    public void doGetIdNotValidExpectStatus400() {
        when(request.getParameter("id")).thenReturn("");

        servlet.doGet(request, response);

        verify(dataToJson, times(1)).processResponse(response, 400,
                new ErrorResponseBinding(400, "Id is not valid"));

    }

    @Test
    public void doPostIOExceptionExpectStatus422() throws IOException {
        doThrow(new IOException()).when(jsonToData).jsonToProductData(request);

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 422, ErrorResponseBinding.ERROR_RESPONSE_422);

    }

    @Test
    public void doPostProductDataIsValidAndProductExistsExpectStatus201() throws EntityNotFoundException, IOException {

        when(dataValidator.isProductDataValid(productBinding.getCategory(), productBinding.getName(),
                productBinding.getPrice(), productBinding.getPriceDiscount())).thenReturn(true);

        Product product = new Product();

        product.setId(101);
        product.setName("lemon");
        product.setCategory("foods");

        when(productService.addNewProduct(productBinding.toEntityObject())).thenReturn(product);

        servlet.doPost(request, response);


        verify(dataToJson).processResponse(response, 201, new ProductResponseBinding(product));
    }

    @Test
    public void doPostProductDataIsValidAndProductNotExistsExpectStatus404() throws EntityNotFoundException, IOException {

        when(dataValidator.isProductDataValid(productBinding.getCategory(), productBinding.getName(),
                productBinding.getPrice(), productBinding.getPriceDiscount())).thenReturn(true);

        doThrow(new EntityNotFoundException()).when(productService).addNewProduct(Matchers.any(Product.class));

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 404, ErrorResponseBinding.ERROR_RESPONSE_404);
    }

    @Test
    public void doPostProductDataIsNotValidThanExpectStatus400() throws IOException {
        when(jsonToData.jsonToProductData(request)).thenReturn(productBinding);

        when(productBinding.getName()).thenReturn("lemon");
        when(productBinding.getCategory()).thenReturn("foods");
        when(productBinding.getPrice()).thenReturn(null);
        when(productBinding.getPriceDiscount()).thenReturn(null);

        when(dataValidator.isProductDataValid(productBinding.getCategory(), productBinding.getName(),
                productBinding.getPrice(), productBinding.getPriceDiscount())).thenReturn(false);

        servlet.doPost(request, response);

        verify(dataToJson).processResponse(response, 400,new ErrorResponseBinding(400,
                "Product with name:"+
                        productBinding.getName()+" category: "+
                        productBinding.getCategory()+" price "+
                        productBinding.getPrice()+" priceDiscount "+
                        productBinding.getPriceDiscount()+" can not be registered"));


    }


}

