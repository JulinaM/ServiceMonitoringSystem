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


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        Integer result = null;

        User user = new User();
        user.setUserName(request.getParameter("username"));
        user.setUserEmail(request.getParameter("useremail"));
        user.setUserStatus(Integer.parseInt(request.getParameter("userstatus")));
        user.setUserRole(Integer.parseInt(request.getParameter("userrole")));

        UserDAO userDAO = new UserDAO();

        try {
            userDAO.createUserTable();
            result = userDAO.putUser(user);
            userDAO.closeConnection();
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

