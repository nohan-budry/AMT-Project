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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name="RegistrationServlet", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {

  @EJB
  private FarmersManagerLocal farmersManager;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    Map<String, String> errors = new HashMap<>();

    String username = req.getParameter("username");
    String firstName = req.getParameter("firstName");
    String lastName = req.getParameter("lastName");
    String email = req.getParameter("email");
    String address = req.getParameter("address");
    String password = req.getParameter("password");
    String confirm = req.getParameter("confirm");

    if (!checkRequired(username)) {
      errors.put("username", "Username is required!");
    }
    if (!checkRequired(firstName)) {
      errors.put("firstName", "First name is required!");
    }
    if (!checkRequired(lastName)) {
      errors.put("lastName", "Last name is required!");
    }
    if (!checkRequired(email)) {
      errors.put("email", "E-Mail is required!");
    }
    if (!checkRequired(password)) {
      errors.put("password", "Password is required!");
    }
    if (!checkPasswords(password, confirm)) {
      errors.put("confirm", "Passwords do not match!");
    }

    Farmer farmer = null;
    if (errors.isEmpty()) {
      try {
        farmersManager.create(Farmer.builder()
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .address(address)
                .password(password)
                .build()
        );
      } catch (SQLException e) {
        errors.put("other", "Registration failed!");
      }
    }

    if (!errors.isEmpty()) {
      req.setAttribute("errors", errors);
      req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
      return;
    }

    username = "steve";

    try {
      farmer = farmersManager.findByUser(username);
    } catch (KeyNotFoundException e) {
      resp.sendRedirect(req.getContextPath() + "/login");
      return;
    }

    req.getSession().setAttribute("farmer", farmer);
    resp.sendRedirect(req.getContextPath() + "/profile");
  }

  private boolean checkRequired(String str) {
    return !(str == null || str.isEmpty());
  }

  private boolean checkPasswords(String str1, String str2) {
    return str1 != null && str1.equals(str2);
  }
 }
