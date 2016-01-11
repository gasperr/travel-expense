package si.fri.sp.security;

import si.fri.sp.entities.User;
import si.fri.sp.entities.enums.UserType;

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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        User user = (User)((HttpServletRequest)req).getSession().getAttribute("user");

        if(user != null){
            if(user.getType() == UserType.DRIVER){
                ((HttpServletResponse)resp).sendRedirect("/views/user/dashboard.xhtml");
            }else if(user.getType() == UserType.FINANCE){
                filterChain.doFilter(req, resp);
            }else{
                ((HttpServletResponse)resp).sendRedirect("/views/managment/dashboard.xhtml");
            }

        }else{
            ((HttpServletResponse)resp).sendRedirect("/index.html");
        }
    }

    @Override
    public void destroy() {

    }

}
