package servlet;

import binding.request.ProductRequestBinding;
import binding.request.ProductUpdateRequestBinding;
import binding.response.ErrorResponseBinding;
import binding.response.ProductResponseBinding;
import binding.response.ProductsResponseBinding;
import database.entity.Product;
import exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import service.ProductService;
import service.impl.ProductServiceImpl;
import util.DataToJson;
import util.JsonToData;
import util.validator.DataValidator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;


public class ProductServlet extends HttpServlet {

    private DataToJson dataToJson;
    private JsonToData jsonToData;
    private DataValidator dataValidator;
    private ProductService productService;

    static final Logger LOGGER = Logger.getLogger(ProductServlet.class);

    public ProductServlet() {
        super();
    }

    public ProductServlet(DataToJson dataToJson, JsonToData jsonToData,
                          DataValidator dataValidator, ProductService productService) {
        super();
        this.dataToJson = dataToJson;
        this.jsonToData = jsonToData;
        this.dataValidator = dataValidator;
        this.productService = productService;
    }

    @Override
    public void init(){
        this.dataToJson = new DataToJson();
        this.jsonToData = new JsonToData();
        this.productService = new ProductServiceImpl();
        this.dataValidator = new DataValidator();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        String id = request.getParameter("id");

        if(id == null){
            List<Product> products = productService.getAllProducts();
            dataToJson.processResponse(response,  200, new ProductsResponseBinding(products));
        }
        else if(dataValidator.isIdValid(id)){

            try {
                List<Product> products = productService.searchProductById(new BigInteger(id));
                dataToJson.processResponse(response, 200, new ProductsResponseBinding(products));
            } catch (EntityNotFoundException e) {
                LOGGER.debug("id is not existing", e);
                dataToJson.processResponse(response, 404, ErrorResponseBinding.ERROR_RESPONSE_404);
            }
        }
        else {
            LOGGER.debug("Id is not valid");
            dataToJson.processResponse(response, 400,
                    new ErrorResponseBinding(400, "Id is not valid"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ProductRequestBinding requestBinding;

        try {
            requestBinding = jsonToData.jsonToProductData(request);
        }catch (IOException e){
            LOGGER.debug(e.getMessage(), e);
            dataToJson.processResponse(response, 422, ErrorResponseBinding.ERROR_RESPONSE_422);
            return;
        }

        if(dataValidator.isProductDataValid(requestBinding.getCategory(), requestBinding.getName(),
                requestBinding.getPrice(), requestBinding.getPriceDiscount())){
            try {
                Product product = productService.addNewProduct(requestBinding.toEntityObject());
                LOGGER.debug("Product is created");
                dataToJson.processResponse(response, 201, new ProductResponseBinding(product));
            }catch (EntityNotFoundException e){
                LOGGER.debug("Customer is not created",e );
                dataToJson.processResponse(response, 404,ErrorResponseBinding.ERROR_RESPONSE_404);
            }
        }
        else {
            LOGGER.info("Product with name:"+
                    requestBinding.getName()+" category: "+
                    requestBinding.getCategory()+" price "+
                    requestBinding.getPrice()+" priceDiscount "+
                    requestBinding.getPriceDiscount()+" can not be registered");

            dataToJson.processResponse(response, 400,
                    new ErrorResponseBinding(400,"Product with name:"+
                            requestBinding.getName()+" category: "+
                            requestBinding.getCategory()+" price "+
                            requestBinding.getPrice()+" priceDiscount "+
                            requestBinding.getPriceDiscount()+" can not be registered"));
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        ProductUpdateRequestBinding requestBinding = null;

        try{
            requestBinding=jsonToData.jsonToProductUpdateData(request);
        }catch (IOException e){
            LOGGER.debug(e.getMessage(), e);
            dataToJson.processResponse(response, 422, ErrorResponseBinding.ERROR_RESPONSE_422);
            return;
        }

        if(dataValidator.isProductUpdateDataValid(requestBinding.getId(), requestBinding.toEntityObject().getName(),
                requestBinding.toEntityObject().getCategory(), requestBinding.toEntityObject().getPrice(), requestBinding.toEntityObject().getPriceDiscount())){
            try {
                Product p = productService.updateProductById(requestBinding.toEntityObject(), BigInteger.valueOf(requestBinding.getId()));
                dataToJson.processResponse(response, 200, new ProductResponseBinding(p));
            }catch (EntityNotFoundException e){
                LOGGER.debug("Product with id"+
                        requestBinding.getId()+
                        "was not been updated" , e);
                dataToJson.processResponse(response, 404, ErrorResponseBinding.ERROR_RESPONSE_404);
            }
        }
        else {
            LOGGER.debug("Input data is not valid");

            dataToJson.processResponse(response, 400, new ErrorResponseBinding(400,
                    "Input data in not valid"));
        }
    }
}
