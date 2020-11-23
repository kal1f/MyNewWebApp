package servlet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import util.HttpResponseModel;
import util.ResponseHandlerToJson;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WelcomeServletTest {
    @Mock
    HttpServletResponse response;
    @Mock
    HttpServletRequest request;
    @Mock
    ResponseHandlerToJson responseHandlerToJson;
    @Mock
    HttpResponseModel httpResponseModel;

    WelcomeServlet servlet;

    @Before
    public void setUp(){
        servlet = new WelcomeServlet(httpResponseModel, responseHandlerToJson);

        doNothing().when(responseHandlerToJson).processResponse(response, httpResponseModel);
    }

    @Test
    public void whenCallDoGetThenServletReturnWelcomePage() {

        servlet.doGet(request, response);

        verify(responseHandlerToJson).processResponse(response, httpResponseModel);
    }

}
