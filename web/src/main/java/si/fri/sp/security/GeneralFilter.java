package si.fri.sp.security;

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

@WebFilter("/views/*")
public class GeneralFilter implements Filter{

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if(((HttpServletRequest)req).getSession().getAttribute("user") != null){
            chain.doFilter(req, resp);
        }else{
            ((HttpServletResponse)resp).sendRedirect("/index.html");
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
