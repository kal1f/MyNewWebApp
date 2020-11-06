package servlet;

import database.entity.Customer;
import service.Authentication;
import service.AuthenticationImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.nio.file.Files;

@WebListener
public class ContextListenerServlet implements ServletContextListener {

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
