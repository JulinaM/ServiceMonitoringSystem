package com.tektak.iloop.rm.servlet;

import com.tektak.iloop.rm.common.PasswordEnc;
import com.tektak.iloop.rm.common.ServletCommon;
import com.tektak.iloop.rm.dao.UserDetailDAO;
import com.tektak.iloop.rm.datamodel.UserDetail;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by tektak on 7/3/14.
 */

@WebServlet("/adduser")
public class AddUserServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        Integer result = null;
        String error = "";
        UserDetailDAO userDetailDAO = null;
        UserDetail userDetail = new UserDetail();
        userDetail.setUserName(request.getParameter("username"));
        userDetail.setUserEmail(request.getParameter("useremail"));
        userDetail.setUserStatus(request.getParameter("userstatus"));
        userDetail.setUserRole(request.getParameter("userrole"));
        userDetail.setUserPassword(PasswordEnc.createRandomString());
        try {
            userDetailDAO = new UserDetailDAO();
            result = userDetailDAO.checkAvailability(userDetail.getUserEmail());
            if (result == 1) {
                userDetailDAO.putUser(userDetail);
                page = "/allusers";
            } else {
                page = "/adduser";
                error = "?err=This email is already registered";
            }
        } catch (Exception e) {
            page="/adduser";
            error = "?err="+e.toString();
        } finally {
            if (userDetailDAO != null)
                userDetailDAO.closeConnection();
            response.sendRedirect(page + error);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = ServletCommon.generateToken(request.getSession());
        request.setAttribute("token", token);
        request.setAttribute("error", request.getParameter("err"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/user/addUser.jsp");
        dispatcher.forward(request, response);
    }
}

