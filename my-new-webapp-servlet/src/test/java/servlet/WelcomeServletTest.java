package servlet;

import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class WelcomeServletTest {


    private final static String path = "/welcome.html";

    @Test
    public void whenCallDoGetThenServletReturnWelcomePage() throws IOException, ServletException {
        WelcomeServlet servlet = new WelcomeServlet();

        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);

        doNothing().when(response).sendRedirect(path);

        servlet.doGet(request, response);

        verify(response).sendRedirect(path);
    }

}
