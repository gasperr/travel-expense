package si.fri.sp.servlet;

import si.fri.ApplicationCache;
import si.fri.sp.entities.Log;
import si.fri.sp.interfaces.LoggerLocal;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Author Gasper Andrejc, created on 11/jan/2016
 */

@WebServlet("/logging")
public class LogChecker extends HttpServlet{

    @Inject
    private ApplicationCache applicationCache;

    @EJB
    private LoggerLocal loggerExpense;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = resp.getWriter();

        applicationCache.persistLogs();

        if(req.getParameterMap().containsKey("max")){
            int max = Integer.parseInt(req.getParameter("max"));
            List<Log> logs = loggerExpense.findAll(max);
            pw.println("Found "+logs.size()+" logs...:<br />");
            for(Log log : logs){
                pw.println(log.toString());
            }

        }else if(req.getParameterMap().containsKey("type")){
            int type = Integer.parseInt(req.getParameter("type"));
            List<Log> logs = loggerExpense.findByType(type);
            pw.println("Found "+logs.size()+" logs...:");
            for(Log log : logs){
                pw.println(log.toString());
            }
        }else{
            List<Log> logs = loggerExpense.findAll(-1);
            pw.println("Found "+logs.size()+" logs...:");
            for(Log log : logs){
                pw.println(log.toString());
            }
        }






    }
}
