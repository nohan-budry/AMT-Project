package ch.heigvd.amt.project.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.internal.util.reflection.FieldSetter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LogoutServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private LogoutServlet servlet;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        MockitoAnnotations.initMocks(this);

        servlet = new LogoutServlet();

        when(request.getContextPath()).thenReturn("/test");
    }

    @Test
    void itInvalidateAnExistingSession() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(session);

        servlet.doGet(request, response);

        verify(request).getSession(false);
        verify(session).invalidate();
        verify(response).sendRedirect(ArgumentMatchers.contains("login"));
    }

    @Test
    void itDoesNotInvalidateIfNoSession() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(null);

        servlet.doGet(request, response);

        verify(request).getSession(false);
        verify(session, never()).invalidate();
        verify(response).sendRedirect(ArgumentMatchers.contains("login"));
    }
}
