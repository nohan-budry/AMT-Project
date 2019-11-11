package ch.heigvd.amt.project.presentation;

import ch.heigvd.amt.project.model.Field;
import ch.heigvd.amt.project.services.FieldManagerLocal;
import ch.heigvd.amt.project.utils.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.internal.util.reflection.FieldSetter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class FieldsServletTest extends ServletTest {

    @Mock
    private FieldManagerLocal fieldManager;

    private FieldsServlet servlet;

    @BeforeEach
    void setUpServlet() throws NoSuchFieldException {

        servlet = new FieldsServlet();
        FieldSetter.setField(servlet, servlet.getClass().getDeclaredField("fieldManager"), fieldManager);
    }

    @Test
    void doGet() throws ServletException, IOException, SQLException {
        List<Field> fields = new LinkedList<>();

        when(request.getParameter("amount")).thenReturn("25");
        when(request.getParameter("page")).thenReturn("1");
        when(fieldManager.findAll(any())).thenReturn(fields);

        servlet.doGet(request, response);

        verify(request).getParameter("amount");
        verify(request).getParameter("page");

        verify(fieldManager).findAll(any());

        verify(request).setAttribute("amount", 25);
        verify(request).setAttribute("page", 1);
        verify(request).setAttribute("fields", fields);

        verify(request).getRequestDispatcher(ArgumentMatchers.contains("fields"));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void itCanCreateAField() throws SQLException, ServletException, IOException {
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("create", new String[] {});

        when(request.getParameterMap()).thenReturn(parameterMap);
        when(request.getParameter("size")).thenReturn("1000");
        when(fieldManager.create(any())).thenReturn(null);

        servlet.doPost(request, response);

        verify(request).getParameter("size");
        verify(fieldManager).create(any());
        verify(request).setAttribute(eq("success"), anyString());
        verify(request, never()).setAttribute(eq("error"), anyString());
    }

    @Test
    void itHandlesCreationErrorsCorrectly() throws SQLException, ServletException, IOException {
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("create", new String[] {});

        when(request.getParameterMap()).thenReturn(parameterMap);
        when(request.getParameter("size")).thenReturn("1000");
        when(fieldManager.create(any())).thenThrow(new SQLException());

        servlet.doPost(request, response);

        verify(request).getParameter("size");
        verify(fieldManager).create(any());
        verify(request, never()).setAttribute(eq("success"), anyString());
        verify(request).setAttribute(eq("error"), anyString());
    }

    @Test
    void itHandlesNumberErrorsCorrectlyOnCreate() throws SQLException, ServletException, IOException {
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("create", new String[] {});

        when(request.getParameterMap()).thenReturn(parameterMap);
        when(request.getParameter("size")).thenThrow(new NumberFormatException());
        when(fieldManager.create(any())).thenReturn(null);

        servlet.doPost(request, response);

        verify(request).getParameter("size");
        verify(fieldManager, never()).create(any());
        verify(request, never()).setAttribute(eq("success"), anyString());
        verify(request).setAttribute(eq("error"), anyString());
    }
}
