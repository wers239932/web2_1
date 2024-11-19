package filters;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JspFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException {
        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied.");
    }
}
