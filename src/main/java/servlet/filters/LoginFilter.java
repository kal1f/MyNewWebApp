package servlet.filters;

import database.CustomerDAO;
import database.CustomerDAOImpl;
import service.Authentication;
import service.AuthenticationImpl;

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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        authenticationImpl = (AuthenticationImpl) filterConfig.getServletContext().getAttribute("authenticationImpl");
        cd = new CustomerDAOImpl();
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        final HttpSession session = req.getSession();

        String path = req.getRequestURI().substring(req.getContextPath().length());


        if(path.startsWith("/login") || path.startsWith("/register")){
            filterChain.doFilter(request, response);
            return;
        }

        if (authenticationImpl.isSessionPresent(session.getId())) {
            filterChain.doFilter(req,resp);
        }
        else {
            ((HttpServletResponse) response).sendRedirect("/login");
        }


    }
}
