package servlet.listener;

import service.authentication.Authentication;
import service.authentication.AuthenticationImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AuthenticationContextListener implements ServletContextListener {

    public Authentication authenticationImpl;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();
        authenticationImpl = new AuthenticationImpl();

        servletContext.setAttribute("authenticationImpl", authenticationImpl);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
