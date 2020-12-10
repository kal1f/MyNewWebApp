package servlet.filter;

import binding.request.CustomerUpdateRequestBinding;
import binding.response.ErrorResponseBinding;
import database.entity.Customer;
import database.entity.Role;
import org.apache.log4j.Logger;
import service.authentication.Authentication;
import util.DataToJson;
import util.JsonToData;
import wrapper.CachedBodyHttpServletRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AccessFilter implements Filter {

    private Authentication authenticationImpl;
    private DataToJson dataToJson;
    private JsonToData jsonToData;

    static final Logger LOGGER = Logger.getLogger(AccessFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        this.authenticationImpl = (Authentication) filterConfig.getServletContext().getAttribute("authenticationImpl");
        this.dataToJson = new DataToJson();
        this.jsonToData = new JsonToData();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException {

        CachedBodyHttpServletRequest req = new CachedBodyHttpServletRequest( (HttpServletRequest) request);
        HttpServletResponse resp = (HttpServletResponse) response;
        final HttpSession session = req.getSession(false);
        String method = req.getMethod();
        String path = req.getRequestURI().substring(req.getContextPath().length());

        try {
            if(authenticationImpl.isSessionPresent(session.getId())) {
                LOGGER.debug("session present -> check access restriction");
                Customer customer = authenticationImpl.getCustomer(session.getId());
                Role role = customer.getRole();

                if (Role.ROLE_ADMIN.equals(role)) {
                    LOGGER.debug("Admin access to all pages");
                    filterChain.doFilter(req, resp);
                }

                if (Role.ROLE_BUYER.equals(role)) {
                    LOGGER.debug("Buyer role");
                    if (path.startsWith("/role")) {
                        if (method.equals("POST") || method.equals("PUT")) {
                            LOGGER.debug("role restriction");
                            dataToJson.processResponse(resp, 403, ErrorResponseBinding.ERROR_RESPONSE_403);
                        }
                        else {
                            LOGGER.debug("role not restrict method get");
                            filterChain.doFilter(req, resp);
                        }
                    }
                    else if (path.startsWith("/transactions")) {
                        if (method.equals("PUT")) {
                            LOGGER.debug("transactions put restriction");
                            dataToJson.processResponse(resp, 403, ErrorResponseBinding.ERROR_RESPONSE_403);
                        }
                        else {
                            LOGGER.debug("role not restrict post, get method");
                            filterChain.doFilter(req, resp);
                        }
                    }
                    else if (path.startsWith("/products")) {
                        if (method.equals("POST") || method.equals("PUT")) {
                            LOGGER.debug("products put post restriction");
                            dataToJson.processResponse(resp, 403, ErrorResponseBinding.ERROR_RESPONSE_403);
                        }
                        else {
                            LOGGER.debug("product get not restrict");
                            filterChain.doFilter(req, resp);
                        }

                    }
                    else if (path.startsWith("/customers")) {
                        if (method.equals("GET")) {
                            LOGGER.debug("customers get restriction");
                            String id = req.getParameter("id");
                            if (!id.equals(Integer.toString(customer.getId())) || id.equals("")) {
                                LOGGER.debug("customers get id restriction");
                                dataToJson.processResponse(resp, 403, ErrorResponseBinding.ERROR_RESPONSE_403);
                            }
                            else{
                                LOGGER.debug("customers id not restrict");
                                filterChain.doFilter(req, resp);
                            }
                        }
                        else if(method.equals("PUT")){
                            CustomerUpdateRequestBinding putBody = jsonToData.jsonToCustomerUpdateData(req);
                            Integer id = putBody.getId();

                            if(!id.equals(customer.getId())){
                                LOGGER.debug("customers put id restriction");
                                dataToJson.processResponse(resp, 403, ErrorResponseBinding.ERROR_RESPONSE_403);
                            }
                            else{
                                LOGGER.debug("customers put id not restricted");
                                filterChain.doFilter(req, resp);
                            }
                        }
                        else {
                            LOGGER.debug("customers post not restrict");
                            filterChain.doFilter(req, resp);
                        }
                    }
                    else {
                        LOGGER.debug("urls except role, transactions, products, customers not restrict");
                        filterChain.doFilter(req, resp);
                    }
                }

            }
            else {
                LOGGER.debug("session not present -> skip");
                filterChain.doFilter(req, resp);
            }

        }catch (ServletException | IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

}
