package servlet.listener;

import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesContextListener implements ServletContextListener {

    static final Logger LOGGER = Logger.getLogger(PropertiesContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        final ServletContext servletContext = sce.getServletContext();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String productionProperties = "/usr/local/my-web-app/production.properties";

        try {

            LOGGER.info("Trying use production properties by path: "+productionProperties);
            File file = new File(productionProperties);
            if(!file.exists()){
                LOGGER.info("Production properties by path: "+productionProperties+" is not existing.");
                file = new File("config.properties");
                LOGGER.info("Trying inner properties of MyNewWebApp by path: "+classLoader.getResourceAsStream(file.getPath()));
            }

            InputStream input = classLoader.getResourceAsStream(file.getPath());

            Properties properties = new Properties();
            properties.load(input);

            servletContext.setAttribute("properties", properties);
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }

}
