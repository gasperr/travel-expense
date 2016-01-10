package si.fri.sp.servlet;

import si.fri.ApplicationCache;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author Gasper Andrejc, created on 10/jan/2016
 */

@WebServlet("/clearcache")
public class CacheClear  extends HttpServlet{

    @Inject
    private ApplicationCache applicationCache;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        applicationCache.clearAllCache();
        PrintWriter pw = resp.getWriter();
        pw.println("Successfully cleared all cache..");
    }
}
