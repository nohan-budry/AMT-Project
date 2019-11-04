package ch.heigvd.amt.project.presentation;


import ch.heigvd.amt.project.services.FarmersManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import java.io.IOException;


public class TestServlet extends javax.servlet.http.HttpServlet {


    @EJB
    private FarmersManagerLocal farmersManager;

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("farmers",farmersManager.findAll());
        request.getRequestDispatcher("/WEB-INF/pages/farmer.jsp").forward(request, response);

    }
}
