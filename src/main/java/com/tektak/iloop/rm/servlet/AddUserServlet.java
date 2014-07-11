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
        String page = null;
        Integer result = null;
        String error = null;
        UserDetailDAO userDetailDAO = null;

        UserDetail userDetail = new UserDetail();
        userDetail.setUserName(request.getParameter("username"));
        userDetail.setUserEmail(request.getParameter("useremail"));
        userDetail.setUserStatus(request.getParameter("userstatus"));
        userDetail.setUserRole(request.getParameter("userrole"));

        try {
            userDetailDAO = new UserDetailDAO();
            result = userDetailDAO.checkAvailability(userDetail.getUserEmail());
            System.out.println(result);
            if (result == 1) {
                userDetailDAO.putUser(userDetail);
                page = "/allusers";
                error = "";
            }
            else {
                page = "/adduser";
                error = "?err=This email is already registered";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDetailDAO.closeConnection();
            response.sendRedirect(page+error);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("error",request.getParameter("err"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/user/addUser.jsp");
        dispatcher.forward(request, response);
    }
}

