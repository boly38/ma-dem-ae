package net.mademocratie.gae.server.jdo;

import javax.servlet.*;
import java.io.IOException;

public class PersistenceManagerFilter implements Filter {
    // private static final Logger logger = Logger.getLogger(PersistenceManagerFilter.class.getName());

    public void init(FilterConfig filterConfig) throws ServletException {
        DataStore.initialize();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        DataStore.finishRequest();
    }
}
