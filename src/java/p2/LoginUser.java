package p2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        DBAccess dataBase = DBAccess.getInstance();
//        ResultSet usersSmall = con.getUsersSmallSet();

        String actionType = request.getParameter("actionType");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Boolean isUserComplete;

        if (actionType.equals("login")) {
            if ((email != null) && (password != null)) { // if email and pass from request exists
                if (dataBase.checkUserDB(email, password, 0)) {    // existing user
                    if (dataBase.checkUserComplete(email, password)) { // user profile is complete
                        isUserComplete = true;
                        session.setAttribute("isUserComplete", isUserComplete);
                        session.setAttribute("user", email); //Registramos al usuario en el entorno de la sesión
                        // user page
                    } else {
                        isUserComplete = false;
                        session.setAttribute("isUserComplete", isUserComplete);
                        session.setAttribute("user", email); //Registramos al usuario en el entorno de la sesión
                        // more data page
                    }
                } else {    // no such user
                    session.setAttribute("message", "Email and/or password is incorect");
                    // login again
                }
            } else {
                session.setAttribute("message", "Input email and/or password"); // No email/pass
                // login again
            }

//            response.sendRedirect("login.jsp");
        } else if (actionType.equals("register")) {
            if (dataBase.checkUserDB(email, password, 1)) {    // check email only
                session.setAttribute("message", "Such user already exist");
            } else {    // no such user -> register
                // login page
                isUserComplete = false;
                session.setAttribute("isUserComplete", isUserComplete);
                session.setAttribute("user", email); //Registramos al usuario en el entorno de la sesión
                session.setAttribute("password", password); //Registramos al usuario en el entorno de la sesión
//                session.setAttribute("message", "Email and/or password is incorect");
            }
        }

        response.sendRedirect("#user");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
