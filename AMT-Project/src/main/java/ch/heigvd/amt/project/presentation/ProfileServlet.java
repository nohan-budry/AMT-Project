package ch.heigvd.amt.project.presentation;

import ch.heigvd.amt.project.datastore.exceptions.DuplicateKeyException;
import ch.heigvd.amt.project.model.Farmer;
import ch.heigvd.amt.project.services.FarmersManagerLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name="ProfileServlet", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {


  @EJB
  private FarmersManagerLocal farmersManager;



  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session=req.getSession();
    Farmer farmer=(Farmer)session.getAttribute("farmer");



    if(farmer!=null){
      req.setAttribute("farmer",farmer);
      req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);
    }else {
      req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }


  }

}
