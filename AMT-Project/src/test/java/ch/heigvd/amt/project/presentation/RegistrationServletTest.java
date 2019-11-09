package ch.heigvd.amt.project.presentation;

import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.project.model.Farmer;
import ch.heigvd.amt.project.services.FarmersManagerLocal;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.internal.util.reflection.FieldSetter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class RegistrationServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private FarmersManagerLocal farmersManager;

    @Mock
    private HttpSession session;

    private RegistrationServlet servlet;

    @Captor
    private ArgumentCaptor<Map<String, String>> argumentCaptor;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        MockitoAnnotations.initMocks(this);

        servlet = new RegistrationServlet();
        FieldSetter.setField(servlet, servlet.getClass().getDeclaredField("farmersManager"), farmersManager);

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/test");
    }

    @Test
    void doGet() throws ServletException, IOException {

        servlet.doGet(request, response);

        verify(request).getRequestDispatcher(ArgumentMatchers.contains("register.jsp"));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void itCannotCreateAUserWithoutTheRequiredFields() throws ServletException, IOException, SQLException {
        when(request.getParameter("username")).thenReturn(null);
        when(request.getParameter("firstName")).thenReturn(null);
        when(request.getParameter("lastName")).thenReturn(null);
        when(request.getParameter("email")).thenReturn(null);
        when(request.getParameter("password")).thenReturn(null);
        when(request.getParameter("confirm")).thenReturn(null);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("errors"), argumentCaptor.capture());

        Assert.assertTrue(argumentCaptor.getValue().containsKey("username"));
        Assert.assertTrue(argumentCaptor.getValue().containsKey("firstName"));
        Assert.assertTrue(argumentCaptor.getValue().containsKey("lastName"));
        Assert.assertTrue(argumentCaptor.getValue().containsKey("email"));
        Assert.assertTrue(argumentCaptor.getValue().containsKey("password"));

        verify(request).getRequestDispatcher(ArgumentMatchers.contains("register.jsp"));
        verify(requestDispatcher).forward(request, response);
        verify(farmersManager, never()).create(any());
    }

    @Test
    void itCanCreateAUserAndRedirectToProfile() throws ServletException, IOException, SQLException, KeyNotFoundException {
        Farmer farmer = Farmer.builder().username("steve").build();

        when(request.getParameter("username")).thenReturn("steve");
        when(request.getParameter("firstName")).thenReturn("Steve");
        when(request.getParameter("lastName")).thenReturn("steve");
        when(request.getParameter("email")).thenReturn("steve");
        when(request.getParameter("address")).thenReturn("steve");
        when(request.getParameter("password")).thenReturn("steve");
        when(request.getParameter("confirm")).thenReturn("steve");

        when(farmersManager.create(any())).thenReturn(farmer);
        when(farmersManager.findByUser(farmer.getUsername())).thenReturn(farmer);

        servlet.doPost(request, response);

        verify(request).getParameter("username");
        verify(request).getParameter("firstName");
        verify(request).getParameter("lastName");
        verify(request).getParameter("email");
        verify(request).getParameter("address");
        verify(request).getParameter("password");
        verify(request).getParameter("confirm");

        verify(request, never()).setAttribute(eq("errors"), any());

        verify(farmersManager).create(any());
        verify(farmersManager).findByUser(farmer.getUsername());

        verify(request).getSession();
        verify(session).setAttribute("farmer", farmer);
        verify(response).sendRedirect(ArgumentMatchers.contains("profile"));
    }
}
