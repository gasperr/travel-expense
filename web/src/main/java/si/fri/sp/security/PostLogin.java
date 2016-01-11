package si.fri.sp.security;

import si.fri.sp.entities.User;
import si.fri.sp.entities.enums.UserType;
import si.fri.sp.interfaces.UserServiceLocal;
import si.fri.sp.utils.PermissionChecker;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Gasper Andrejc, created on 11/jan/2016
 */
@WebServlet("/login")
public class PostLogin extends HttpServlet {

    @EJB
    private UserServiceLocal userServiceLocal;

    @Inject
    private PermissionChecker permissionChecker;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //todo check password and all that potatoe


        User user = null;
        if (username.equalsIgnoreCase("Rok Nerovac")) {
            user = userServiceLocal.read(1);
            request.getSession().setAttribute("user", user); // Put user in session.

        } else if (username.equalsIgnoreCase("Peter Demolis")) {
            user = userServiceLocal.read(2);
            request.getSession().setAttribute("user", user); // Put user in session.

        } else if (username.equalsIgnoreCase("Ana Konda")) {
            user = userServiceLocal.read(3);
            request.getSession().setAttribute("user", user); // Put user in session.
        }

        if(user != null){
            permissionChecker.setCurrentUser(user);
            makeRedirect(user, response);
        }else{
            request.setAttribute("error", "Unknown login, try again"); // Set error msg for ${error}
            request.getRequestDispatcher("/index.html").forward(request, response); // Go back to login page.
        }


    }

    protected void makeRedirect(User user, HttpServletResponse response) throws ServletException, IOException {
        if (user.getType() == UserType.DRIVER) {
            response.sendRedirect("/views/user/dashboard.xhtml");
        } else if (user.getType() == UserType.FINANCE) {
            response.sendRedirect("/views/finance/dashboard.xhtml");
        } else {
            response.sendRedirect("/views/managment/dashboard.xhtml");
        }
    }
}
