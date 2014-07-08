package com.tektak.iloop.rm.servlet;

import com.tektak.iloop.rm.dao.UserDetailDAO;
import com.tektak.iloop.rm.datamodel.UserDetail;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tektak on 7/3/14.
 */

@WebServlet("/adduser")
public class AddUserServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page;
        Integer result = null;

        UserDetail userDetail = new com.tektak.iloop.rm.datamodel.UserDetail();
        userDetail.setUserName(request.getParameter("username"));
        userDetail.setUserEmail(request.getParameter("useremail"));
        userDetail.setUserStatus(Integer.parseInt(request.getParameter("userstatus")));
        userDetail.setUserRole(Integer.parseInt(request.getParameter("userrole")));

        UserDetailDAO userDetailDAO = new UserDetailDAO();

        try {
            userDetailDAO.createUserTable();
            result = userDetailDAO.putUser(userDetail);
            userDetailDAO.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("/allusers");
        /*if (result == 1) {
            page = ("/pages/user/allUsers.jsp");
        } else {
            page = ("/pages/user/addUser.jsp");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);*/

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/user/addUser.jsp");
        dispatcher.forward(request, response);
    }
}

