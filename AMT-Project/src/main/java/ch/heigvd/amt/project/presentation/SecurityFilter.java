package ch.heigvd.amt.project.presentation;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class SecurityFilter implements Filter {
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    HttpSession session = request.getSession(false);


    //TODO:change the route
    String loginURI = request.getContextPath() + "/registration";

    boolean loggedIn = session != null && session.getAttribute("farmer") != null;
    boolean loginRequest = request.getRequestURI().equals(loginURI);

    if (loggedIn || loginRequest) {
      filterChain.doFilter(servletRequest, servletResponse);
    } else {
      response.sendRedirect(loginURI);
    }

  }
}
