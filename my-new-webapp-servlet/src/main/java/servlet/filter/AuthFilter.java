package servlet.filter;

import binding.response.FilterResponseBinding;
import org.apache.log4j.Logger;
import service.authentication.Authentication;
import service.authentication.AuthenticationImpl;
import util.DataToJson;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthFilter implements Filter {

    private Authentication authenticationImpl;
    private DataToJson dataToJson;

    static final Logger LOGGER = Logger.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {

        authenticationImpl = (AuthenticationImpl) filterConfig.getServletContext().getAttribute("authenticationImpl");

        dataToJson = new DataToJson();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        final HttpSession session = req.getSession();

        String path = req.getRequestURI().substring(req.getContextPath().length());


        try {
            LOGGER.debug("AuthFilter url");
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
                dataToJson.processResponse(resp, 401,
                        new FilterResponseBinding(401, "Unauthorized", "http://localhost:8080/login"));
            }
        }
        catch (IOException | ServletException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
