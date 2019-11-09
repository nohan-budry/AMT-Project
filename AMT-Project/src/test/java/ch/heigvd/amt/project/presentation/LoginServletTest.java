package ch.heigvd.amt.project.presentation;

import ch.heigvd.amt.project.model.Farmer;
import ch.heigvd.amt.project.services.FarmersManagerLocal;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.internal.util.reflection.FieldSetter;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class LoginServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Mock
    FarmersManagerLocal farmersManager;

    @Mock
    HttpSession session;

    LoginServlet servlet;

    @Captor
    ArgumentCaptor<Map<String, String>> argumentCaptor;

    @BeforeEach
    public void setUp() throws NoSuchFieldException {
        MockitoAnnotations.initMocks(this);

        servlet = new LoginServlet();
        FieldSetter.setField(servlet, servlet.getClass().getDeclaredField("farmersManager"), farmersManager);

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/amt");
    }

    @Test
    void doGet() throws ServletException, IOException {

        servlet.doGet(request, response);

        verify(request).getRequestDispatcher(ArgumentMatchers.contains("login"));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void itCannotLoginWithoutAUsername() throws ServletException, IOException, SQLException {
        when(request.getParameter("username")).thenReturn(null);
        when(request.getParameter("password")).thenReturn("secret");

        servlet.doPost(request, response);

        verify(request).getParameter("username");
        verify(request).getParameter("password");
        verify(request).setAttribute(eq("errors"), argumentCaptor.capture());

        Assert.assertTrue(argumentCaptor.getValue().containsKey("username"));
        Assert.assertFalse(argumentCaptor.getValue().containsKey("password"));
        Assert.assertFalse(argumentCaptor.getValue().containsKey("login"));

        verify(request).getRequestDispatcher(ArgumentMatchers.contains("login"));
        verify(requestDispatcher).forward(request, response);
        verify(farmersManager, never()).create(any());
    }

    @Test
    void itCannotLoginWithoutAPassword() throws ServletException, IOException, SQLException {
        when(request.getParameter("username")).thenReturn("jack");
        when(request.getParameter("password")).thenReturn(null);

        servlet.doPost(request, response);

        verify(request).getParameter("username");
        verify(request).getParameter("password");
        verify(request).setAttribute(eq("errors"), argumentCaptor.capture());

        Assert.assertFalse(argumentCaptor.getValue().containsKey("username"));
        Assert.assertTrue(argumentCaptor.getValue().containsKey("password"));
        Assert.assertFalse(argumentCaptor.getValue().containsKey("login"));

        verify(request).getRequestDispatcher(ArgumentMatchers.contains("login"));
        verify(requestDispatcher).forward(request, response);

        verify(farmersManager, never()).login(anyString(), anyString());
    }

    @Test
    void itHandlesNotExistingCredentialsCorrectly() throws ServletException, IOException {
        String username = "jack";
        String password = "secret";

        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);
        when(farmersManager.login(anyString(), anyString())).thenReturn(null);

        servlet.doPost(request, response);

        verify(request).getParameter("username");
        verify(request).getParameter("password");
        verify(request).setAttribute(eq("errors"), argumentCaptor.capture());

        Assert.assertFalse(argumentCaptor.getValue().containsKey("username"));
        Assert.assertFalse(argumentCaptor.getValue().containsKey("password"));
        Assert.assertTrue(argumentCaptor.getValue().containsKey("login"));

        verify(farmersManager).login(username, password);

        verify(request).getRequestDispatcher(ArgumentMatchers.contains("login"));
        verify(requestDispatcher).forward(request, response);

    }

    @Test
    void itCanLogin() throws ServletException, IOException, SQLException {
        Farmer farmer = Farmer.builder().username("jack").password("secret").build();

        when(request.getParameter("username")).thenReturn(farmer.getUsername());
        when(request.getParameter("password")).thenReturn(farmer.getPassword());
        when(farmersManager.login(anyString(), anyString())).thenReturn(farmer);

        servlet.doPost(request, response);

        verify(request).getParameter("username");
        verify(request).getParameter("password");
        verify(request, never()).setAttribute(any(), any());

        verify(farmersManager).login(farmer.getUsername(), farmer.getPassword());

        verify(request).getSession();
        verify(session).setAttribute("farmer", farmer);
        verify(response).sendRedirect(ArgumentMatchers.contains("profile"));
    }
}
