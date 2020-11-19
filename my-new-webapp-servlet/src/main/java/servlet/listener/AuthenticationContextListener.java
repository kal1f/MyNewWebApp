package servlet.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import service.authentication.Authentication;
import service.authentication.AuthenticationImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

//import org.apache.logging.log4j.PropertyConfigurator;

@WebListener
public class AuthenticationContextListener implements ServletContextListener {

    public Authentication authenticationImpl;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();

        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        File file = new File("/Users/ashcherbak/IdeaProjects/MyNewWebApp/my-new-webapp-servlet/src/main/webapp/WEB-INF/log4j2.properties");
        context.setConfigLocation(file.toURI());

        authenticationImpl = new AuthenticationImpl();

        servletContext.setAttribute("authenticationImpl", authenticationImpl);

       // PropertyConfigurator.configure()
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
