package ch.heigvd.amt.project.presentation;

import ch.heigvd.amt.project.model.Farmer;
import ch.heigvd.amt.project.services.ExploitationRightLocal;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProfileServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private ExploitationRightLocal rightsManager;

    @Mock
    private HttpSession session;

    private ProfileServlet servlet;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        MockitoAnnotations.initMocks(this);

        servlet = new ProfileServlet();
        FieldSetter.setField(servlet, servlet.getClass().getDeclaredField("rightsManager"), rightsManager);

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/test");
    }

    @Test
    void doGet() throws ServletException, IOException, SQLException {
        Farmer farmer = Farmer.builder().build();
        when(session.getAttribute("farmer")).thenReturn(farmer);
        when(request.getParameter("amount")).thenReturn("25");
        when(request.getParameter("page")).thenReturn("1");

        servlet.doGet(request, response);

        verify(request).getSession();
        verify(session).getAttribute("farmer");

        verify(request).getParameter("amount");
        verify(request).getParameter("page");

        verify(rightsManager).findAllForFarmer(eq(farmer), any());

        verify(request).setAttribute("farmer", farmer);
        verify(request).setAttribute("amount", 25);
        verify(request).setAttribute("page", 1);

        verify(request).getRequestDispatcher(ArgumentMatchers.contains("profile"));
        verify(requestDispatcher).forward(request, response);
    }
}
