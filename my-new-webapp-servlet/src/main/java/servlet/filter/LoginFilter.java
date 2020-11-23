package servlet.filter;

import database.CustomerDAO;
import database.CustomerDAOImpl;
import org.apache.log4j.Logger;
import service.authentication.Authentication;
import service.authentication.AuthenticationImpl;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {

    private Authentication authenticationImpl;
    CustomerDAO cd;

    static final Logger LOGGER = Logger.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        authenticationImpl = (AuthenticationImpl) filterConfig.getServletContext().getAttribute("authenticationImpl");
        cd = new CustomerDAOImpl();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        final HttpSession session = req.getSession();

        String path = req.getRequestURI().substring(req.getContextPath().length());


        try {
            LOGGER.debug("Filter url");
            if(path.startsWith("/login") || path.startsWith("/register")){
                LOGGER.debug("url starts with /login or /register -> skip");
                filterChain.doFilter(request, response);
                return;
            }

            if (authenticationImpl.isSessionPresent(session.getId())) {
                LOGGER.debug("session present -> skip");
                filterChain.doFilter(req,resp);
            }
            else {
                LOGGER.debug("send redirect to /login");
                ((HttpServletResponse) response).sendRedirect("/login");
            }
        } catch (IOException | ServletException e) {
            LOGGER.error(e.getMessage(), e);
        }


    }
}
