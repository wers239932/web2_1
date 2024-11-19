package filters;

import jakarta.servlet.*;

import java.io.IOException;

public class EverythingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher("/controller");
        requestDispatcher.forward(servletRequest, servletResponse);
    }
}
