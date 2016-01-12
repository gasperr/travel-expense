package si.fri.sp.security;

import si.fri.sp.entities.User;
import si.fri.sp.entities.enums.UserType;
import si.fri.sp.utils.PermissionChecker;

import javax.inject.Inject;
import javax.naming.NoPermissionException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Gasper Andrejc, created on 11/jan/2016
 */

@WebFilter("/views/finance/*")
public class FinanceFilter implements Filter {

    @Inject
    private PermissionChecker permissionChecker;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        try {
            if (permissionChecker.hasFinancePermission()) {
                filterChain.doFilter(req, resp);
            } else {
                ((HttpServletResponse) resp).sendRedirect("/index.html");
            }
        } catch (NoPermissionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }

}
