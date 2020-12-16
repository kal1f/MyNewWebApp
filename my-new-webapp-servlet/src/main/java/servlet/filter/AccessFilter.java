package servlet.filter;

import binding.request.CustomerUpdateRequestBinding;
import binding.response.ErrorResponseBinding;
import database.entity.Customer;
import database.entity.Role;
import org.apache.log4j.Logger;
import service.authentication.Authentication;
import util.DataToJson;
import util.JsonToData;
import util.validator.DataValidator;
import wrapper.CachedHttpServletRequestWrapper;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class AccessFilter implements Filter {

    private Authentication authenticationImpl;
    private DataToJson dataToJson;
    private JsonToData jsonToData;
    private DataValidator dataValidator;

    static final Logger LOGGER = Logger.getLogger(AccessFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        this.authenticationImpl = (Authentication) filterConfig.getServletContext().getAttribute("authenticationImpl");
        this.dataToJson = new DataToJson();
        this.jsonToData = new JsonToData();
        this.dataValidator = new DataValidator();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException {

        CachedHttpServletRequestWrapper req = new CachedHttpServletRequestWrapper( (HttpServletRequest) request);
        HttpServletResponse resp = (HttpServletResponse) response;
        final HttpSession session = req.getSession(false);
        String method = req.getMethod();
        String path = req.getRequestURI().substring(req.getContextPath().length());
        String requestUrl = method+" "+path;

        final List<String> userRestrictedEndpoints = Arrays.asList("POST /role","PUT /role","PUT /transactions",
                "PUT /products","POST /products", "GET /customers", "PUT /customers");

        final List<String> neededCheckIdEndpoints = Arrays.asList("GET /customers", "PUT /customers");

        try {
            if(authenticationImpl.isSessionPresent(session.getId())) {
                LOGGER.debug("session present -> check access restriction");
                Customer customer = authenticationImpl.getCustomer(session.getId());
                Role role = customer.getRole();

                if (Role.ROLE_ADMIN.equals(role)) {
                    LOGGER.debug("Admin can access to all pages");
                    filterChain.doFilter(req, resp);
                }

                if (Role.ROLE_BUYER.equals(role)) {
                    if (userRestrictedEndpoints.contains(requestUrl)) {
                        if (neededCheckIdEndpoints.contains(requestUrl)){
                            if (method.equals("GET")){
                                LOGGER.debug("check valid id in GET method");
                                String id = req.getParameter("id");
                                if (!dataValidator.isIdValid(id) || !id.equals(Integer.toString(customer.getId()))) {
                                    LOGGER.debug("id: "+id+" is not valid or not provided");
                                    dataToJson.processResponse(resp, 403, ErrorResponseBinding.ERROR_RESPONSE_403);
                                }
                                else{
                                    LOGGER.debug("id: "+id+" in GET method is valid");
                                    filterChain.doFilter(req, resp);
                                }
                            }
                            else {
                                LOGGER.debug("check valid id in "+method+" method");
                                CustomerUpdateRequestBinding putBody = jsonToData.jsonToCustomerUpdateData(req);
                                Integer id = putBody.getId();
                                if(!id.equals(customer.getId())){
                                    LOGGER.debug("id: "+id+" is not equal to self");
                                    dataToJson.processResponse(resp, 403, ErrorResponseBinding.ERROR_RESPONSE_403);
                                }
                                else{
                                    LOGGER.debug("id: "+id+" is equal to self");
                                    filterChain.doFilter(req, resp);
                                }
                            }
                        }
                        else {
                            LOGGER.debug("Buyer role can not have access to " + requestUrl);
                            dataToJson.processResponse(resp, 403, ErrorResponseBinding.ERROR_RESPONSE_403);
                        }
                    } else {
                        LOGGER.debug("Method and path are not in endpoints list");
                        filterChain.doFilter(req, resp);
                    }
                }

            }
            else {
                LOGGER.debug("session not present -> skip");
                filterChain.doFilter(req, resp);
            }
        } catch (ServletException | IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

}