package ch.heigvd.amt.project.presentation.filters;

import ch.heigvd.amt.project.model.Farmer;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/fields", "/rights"})
public class AdminPagesFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        Farmer farmer = null;
        if (session != null) {
            farmer = (Farmer) session.getAttribute("farmer");
        }

        if (farmer != null && farmer.isAdmin()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }
}
