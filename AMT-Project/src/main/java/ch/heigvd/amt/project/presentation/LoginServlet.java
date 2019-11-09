package ch.heigvd.amt.project.presentation;

import ch.heigvd.amt.project.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.project.datastore.exceptions.KeyNotFoundException;
import ch.heigvd.amt.project.model.Farmer;
import ch.heigvd.amt.project.services.FarmersManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@WebServlet(name="LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @EJB
    private FarmersManagerLocal farmersManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, String> errors = new HashMap<>();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (!checkRequired(username)) {
            errors.put("username", "Username is required!");
        }
        if (!checkRequired(password)) {
            errors.put("password", "Password is required!");
        }

        Farmer farmer = null;
        if (errors.isEmpty()) {
            farmer = farmersManager.login(username, password);
            if (farmer == null) {
                errors.put("login", "Wrong username or password!");
            }
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
            return;
        }

        req.getSession().setAttribute("farmer", farmer);
        resp.sendRedirect(req.getContextPath() + "/profile");
    }

    private boolean checkRequired(String str) {
        return !(str == null || str.isEmpty());
    }
}
