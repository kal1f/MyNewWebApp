package servlet.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SimpleCORSFilter implements Filter {

private final List<String> allowedOrigins = Arrays.asList("http://localhost:55921");

    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            String origin = request.getHeader("Origin");
            //response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
            if(origin == null){
                response.setHeader("Access-Control-Allow-Origin","*");
            }
            else {
                response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
                response.setHeader("Vary", "Origin");
            }
            // Access-Control-Max-Age
            //response.setHeader("Access-Control-Max-Age", "3600");

            // Access-Control-Allow-Credentials
            //response.setHeader("Access-Control-Allow-Credentials", "true");

            // Access-Control-Allow-Methods
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");

            // Access-Control-Allow-Headers
            response.setHeader("Access-Control-Allow-Headers",
                    "Origin, X-Requested-With, Content-Type, Accept");
        }

        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }
}
