package servlet.listener;

import org.apache.log4j.PropertyConfigurator;
import service.authentication.Authentication;
import service.authentication.AuthenticationImpl;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;


public class AuthenticationContextListener implements ServletContextListener {

    public Authentication authenticationImpl;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();

        authenticationImpl = new AuthenticationImpl();

        servletContext.setAttribute("authenticationImpl", authenticationImpl);

        String log4jConfigFile = servletContext.getInitParameter("log4j-config-location");
        String fullPath = servletContext.getRealPath("") + File.separator + log4jConfigFile;
        PropertyConfigurator.configure(fullPath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
