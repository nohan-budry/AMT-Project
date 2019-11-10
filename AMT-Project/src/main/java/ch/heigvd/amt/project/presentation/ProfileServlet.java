package ch.heigvd.amt.project.presentation;

import ch.heigvd.amt.project.model.ExploitationRight;
import ch.heigvd.amt.project.model.Farmer;
import ch.heigvd.amt.project.services.ExploitationRightLocal;
import ch.heigvd.amt.project.utils.Pagination;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "ProfileServlet", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {

    @EJB
    private ExploitationRightLocal rightsManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Farmer farmer = (Farmer) req.getSession().getAttribute("farmer");

        Pagination pagination = new Pagination(
                req.getParameter("amount"), req.getParameter("page"),
                25, 1);

        List<ExploitationRight> rights = null;
        try {
             rights = rightsManager.findAllForFarmer(farmer, pagination);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("farmer", farmer);
        req.setAttribute("rights", rights);
        req.setAttribute("amount", pagination.getAmount());
        req.setAttribute("page", pagination.getPage());
        req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);
    }
}
