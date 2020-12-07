//package servlet;
//
//import binding.response.ErrorResponseBinding;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import util.DataToJson;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class WelcomeServletTest {
//    @Mock
//    HttpServletResponse response;
//    @Mock
//    HttpServletRequest request;
//    @Mock
//    DataToJson dataToJson;
//    @Mock
//    RequestDispatcher dispatcher;
//
//    WelcomeServlet servlet;
//
//    @Before
//    public void setUp() throws ServletException, IOException {
//        servlet = new WelcomeServlet(dataToJson);
//
//        when(request.getRequestDispatcher("welcome.html")).thenReturn(dispatcher);
//
//        doNothing().when(dispatcher).forward(request,response);
//        doNothing().when(dataToJson).processResponse(response, 500,ErrorResponseBinding.ERROR_RESPONSE_500 );
//    }
//
//    @Test
//    public void whenDoGetReturnWelcomePage() throws ServletException, IOException {
//
//        servlet.doGet(request, response);
//
//        verify(request.getRequestDispatcher("welcome.html")).forward(request, response);
//    }
//
//    @Test
//    public void whenDoGetServletException() throws ServletException, IOException {
//
//        doThrow(new ServletException()).when(dispatcher).forward(request, response);
//
//        servlet.doGet(request, response);
//
//        verify(dataToJson).processResponse(response, 500,
//                ErrorResponseBinding.ERROR_RESPONSE_500);
//    }
//    @Test
//    public void whenDoGetIOException() throws ServletException, IOException {
//
//        doThrow(new IOException()).when(dispatcher).forward(request, response);
//
//        servlet.doGet(request, response);
//
//        verify(dataToJson).processResponse(response, 500,
//                ErrorResponseBinding.ERROR_RESPONSE_500);
//    }
//
//}
