package com.tektak.iloop.rm;

import com.tektak.iloop.rm.dao.UserDAO;
import com.tektak.iloop.rm.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tektak on 7/3/14.
 */
public class AddUser extends HttpServlet {
    String page;
    Integer result;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("username");
        String email = request.getParameter("useremail");
        String status = request.getParameter("userstatus");
        String role = request.getParameter("userrole");

        User user = new User();
        user.setUserName(name);
        user.setUserEmail(email);
        user.setUserStatus(status);
        user.setUserRole(role);

        UserDAO userDAO = new UserDAO();

        try {
            userDAO.createUserTable();
            result = userDAO.putUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result == 1) {
            page = ("user/userDetails.jsp");
        } else {
            page = ("/user/addUser.jsp");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/addUser.jsp");
        dispatcher.forward(request, response);
    }
}

