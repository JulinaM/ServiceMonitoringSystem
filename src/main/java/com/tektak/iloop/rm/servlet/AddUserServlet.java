package com.tektak.iloop.rm.servlet;

import com.tektak.iloop.rm.application.logSystem.LogGenerator;
import com.tektak.iloop.rm.common.OurSession;
import com.tektak.iloop.rm.common.PasswordEnc;
import com.tektak.iloop.rm.common.ServletCommon;
import com.tektak.iloop.rm.dao.UserDetailDAO;
import com.tektak.iloop.rm.datamodel.UserDetail;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by tektak on 7/3/14.
 */

@WebServlet("/adduser")
public class AddUserServlet extends HttpServlet {
    String page = null;
    String error = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer result = null;
        String sesData = (String) request.getSession().getAttribute("session");
        HttpSession httpSession=request.getSession(false);
        if (OurSession.getSession(httpSession)!=null && ServletCommon.generateToken(request.getSession()).equals(request.getParameter("token"))) {
            JSONObject sesObj = new JSONObject(sesData);
            UserDetailDAO userDetailDAO = null;
            UserDetail userDetail = new UserDetail();
            userDetail.setUserName(request.getParameter("username"));
            userDetail.setUserEmail(request.getParameter("useremail"));
            userDetail.setUserStatus(request.getParameter("userstatus"));
            userDetail.setUserRole(request.getParameter("userrole"));
            try {
                userDetailDAO = new UserDetailDAO();
                result = userDetailDAO.checkAvailability(userDetail.getUserEmail());
                if (result == 1) {
                    userDetail.setUserPassword(PasswordEnc.createRandomString());
                    userDetailDAO.putUser(userDetail);

                    LogGenerator.generateLog(sesObj.getInt("userId"), request.getRemoteAddr(), "User Added Successfully");
                    page = "/allusers";
                } else {
                    page = "/adduser";
                    error = "?err=This email is already registered";
                }
            } catch (Exception e) {
                page = "/adduser";
                error = "?err=" + e.toString();
            } finally {
                if (userDetailDAO != null)
                    userDetailDAO.closeConnection();
            }
        } else {
            page = "/adduser";
            error = "?err=You don't have permission for this process";
        }
        response.sendRedirect(page + error);
        return;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession=request.getSession(false);
        if (OurSession.getSession(httpSession)==null) {
            response.sendRedirect("/login");
            return;
        }else {
            request.setAttribute("token", ServletCommon.generateToken(request.getSession()));
            request.setAttribute("error", request.getParameter("err"));
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/user/addUser.jsp");
            dispatcher.forward(request, response);
        }
    }
}

