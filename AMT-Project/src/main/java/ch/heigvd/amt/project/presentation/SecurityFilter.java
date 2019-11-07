package ch.heigvd.amt.project.presentation;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(urlPatterns = "/*")
public class SecurityFilter implements Filter {

  private static String PROFILE_PATH = "/profile";
  private static String LOGIN_PATH = "/login";
  private static String REGISTER_PATH = "/registration";
  private static List<String> DISCONNECTED_PATHS = Arrays.asList(LOGIN_PATH, REGISTER_PATH);

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    HttpSession session = request.getSession(false);

    String path = request.getRequestURI()
            .substring(request.getContextPath().length())
            .replaceAll("[/]+$", "");

    boolean loggedIn = session != null && session.getAttribute("farmer") != null;

    if (DISCONNECTED_PATHS.contains(path)) {
      if (!loggedIn) {
        filterChain.doFilter(servletRequest, servletResponse);
      } else {
        response.sendRedirect(request.getContextPath() + PROFILE_PATH);
      }

    } else if (loggedIn) {
      filterChain.doFilter(servletRequest, servletResponse);
    } else {
      response.sendRedirect(request.getContextPath() + LOGIN_PATH);
    }
  }
}
