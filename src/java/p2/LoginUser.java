package p2;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        String actionType = request.getParameter("actionType");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Integer userId = (Integer) session.getAttribute("userId");

        Boolean logout = request.getParameter("logout").equals("Log Out");
        Boolean isUserComplete;

        if (!logout) {
            if (actionType.equals("login")) {   // login user
                if ((email != null) && (password != null)) { // if email and pass from request exists
                    if (dataBase.checkUserDB(email, password, 0)) {
                        try {
                            // existing user
                            ResultSet user = dataBase.loginUser(email, password);

                            user.next();

                            isUserComplete = true;
                            session.setAttribute("isUserComplete", isUserComplete);

                            session.setAttribute("userId", user.getInt("id"));
                            session.setAttribute("name", user.getString("name"));
                            session.setAttribute("surname", user.getString("surname"));
                            session.setAttribute("phone", user.getString("phone"));
                            session.setAttribute("country", user.getString("country"));
                            session.setAttribute("city", user.getString("city"));
                            session.setAttribute("address", user.getString("address"));
                            session.setAttribute("passport", user.getString("passport"));

                            session.setAttribute("user", email);
                            session.setAttribute("password", password);
                        } catch (SQLException ex) {
                            Logger.getLogger(LoginUser.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {    // no such user
                        session.setAttribute("message", "Email and/or password is incorect");
                    }
                } else {
                    session.setAttribute("message", "Input email and/or password"); // No email/pass
                }
            } else if (actionType.equals("register")) {     // register new user
                if (dataBase.checkUserDB(email, password, 1)) {    // check email only
                    session.setAttribute("message", "Such user already exist");
                } else {    // no such user -> register
                    // login page
                    isUserComplete = false;
                    session.setAttribute("isUserComplete", isUserComplete);
                    session.setAttribute("user", email); //Registramos al usuario en el entorno de la sesión
                    session.setAttribute("password", password); //Registramos al usuario en el entorno de la sesión
                }
            } else if (actionType.equals("fullInfo")) {     // edit info or set full info
                email = request.getParameter("email");    // it can change
                password = request.getParameter("password");    // it can change

                String uName = request.getParameter("name");
                String uSurname = request.getParameter("surname");
                String uPhone = request.getParameter("phone");
                String uCountry = request.getParameter("country");
                String uCity = request.getParameter("city");
                String uAddress = request.getParameter("address");
                String uPassport = request.getParameter("passport");
                isUserComplete = true;

                session.setAttribute("user", email);
                session.setAttribute("password", password);
                session.setAttribute("name", uName);
                session.setAttribute("surname", uSurname);
                session.setAttribute("phone", uPhone);
                session.setAttribute("country", uCountry);
                session.setAttribute("city", uCity);
                session.setAttribute("address", uAddress);
                session.setAttribute("passport", uPassport);
                session.setAttribute("isUserComplete", isUserComplete);

                if (userId == null) {   // id does not exists --> register
                    dataBase.registerUser(email, password, uName, uSurname, uPhone, uCountry, uCity, uAddress, uPassport);
                    // set id
                    userId = dataBase.getUserId(email, password);
                    session.setAttribute("userId", userId);
                } else {  // id exists --> update
                    dataBase.updateUser(email, password, uName, uSurname, uPhone, uCountry, uCity, uAddress, uPassport, userId);
                }
            }
        } else {    // log out
            session.removeAttribute("userId");
            session.removeAttribute("user");
            session.removeAttribute("password");
            session.removeAttribute("name");
            session.removeAttribute("surname");
            session.removeAttribute("phone");
            session.removeAttribute("country");
            session.removeAttribute("city");
            session.removeAttribute("address");
            session.removeAttribute("passport");
            session.removeAttribute("isUserComplete");

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
