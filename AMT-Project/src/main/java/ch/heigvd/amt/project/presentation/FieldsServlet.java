package ch.heigvd.amt.project.presentation;

import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.project.model.Field;
import ch.heigvd.amt.project.services.FieldManager;
import ch.heigvd.amt.project.services.FieldManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

//TODO access only by admins?
@WebServlet(name = "FieldsServlet", urlPatterns = "/fields")
public class FieldsServlet extends HttpServlet {

    private static final int DEFAULT_AMOUNT = 25;
    private static final int DEFAULT_PAGE = 1;

    @EJB
    FieldManagerLocal fieldManager;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        int amount;
        try {
            amount = Integer.parseInt(request.getParameter("amount"));
            if (amount < 1) {
                amount = DEFAULT_AMOUNT;
            }
        } catch (NumberFormatException e) {
            amount = DEFAULT_AMOUNT;
        }

        int page;

        try {
            page = Integer.parseInt(request.getParameter("page"));
            if (page < 1) {
                page = DEFAULT_PAGE;
            }
        } catch (NumberFormatException e) {
            page = DEFAULT_PAGE;
        }

        List<Field> fields = null;
        try {
            fields = fieldManager.findAll(amount, page);
        } catch (SQLException e) {
            fields = new LinkedList<>();
        }

        request.setAttribute("amount", amount);
        request.setAttribute("page", page);
        request.setAttribute("fields", fields);

        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/WEB-INF/pages/fields.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameterMap().containsKey("create")) {
            try {
                fieldManager.create(Field.builder()
                        .size(Integer.parseInt(request.getParameter("size")))
                        .build()
                );
                request.setAttribute("success", "Field created");
            } catch (SQLException | NumberFormatException e) {
                request.setAttribute("error", "Creation failed!");
            }

        } else if (request.getParameterMap().containsKey("update")) {
            try {
                fieldManager.update(Field.builder()
                        .idField(Integer.parseInt(request.getParameter("id")))
                        .size(Integer.parseInt(request.getParameter("size")))
                        .build()
                );
                request.setAttribute("success", "Field updated");
            } catch (KeyNotFoundException | NumberFormatException e) {
                request.setAttribute("error", "Update failed!");
            }

        } else if (request.getParameterMap().containsKey("delete")) {
            try {
                fieldManager.deleteById(request.getParameter("id"));
                request.setAttribute("success", "Field deleted");
            } catch (KeyNotFoundException e) {
                request.setAttribute("error", "Deletion failed!");
            }
        }

        doGet(request, response);
    }
}
