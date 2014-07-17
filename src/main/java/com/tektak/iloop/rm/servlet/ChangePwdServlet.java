package com.tektak.iloop.rm.servlet;

import com.tektak.iloop.rm.common.OurSession;
import com.tektak.iloop.rm.common.ServletCommon;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tektak on 7/17/14.
 */
@WebServlet("/changepwd")
public class ChangePwdServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (OurSession.getSession(request.getSession(false)) == null) {
            response.sendRedirect("/login");
            return;
        } else {
            String pass1 = request.getParameter("password");
            String pass2 = request.getParameter("password1");
            if (pass1 != null && !pass1.isEmpty() && pass1.equals(pass2)) {
            }
            /*UserDetailDAO userDetailDAO = null;
            try {
                userDetailDAO = new UserDetailDAO();
            } catch (Exception e) {
                request.setAttribute("error", "Password not changed!");
            } finally {
                if (userDetailDAO != null)
                    userDetailDAO.closeConnection();
            }


            RequestDispatcher dispatch = request.getRequestDispatcher("/pages/user/allUsers.jsp");
            dispatch.forward(request, response);*/
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (OurSession.getSession(request.getSession(false)) == null) {
            response.sendRedirect("/login");
            return;
        } else {
            request.setAttribute("token", ServletCommon.generateToken(request.getSession()));
            RequestDispatcher dispatch = request.getRequestDispatcher("/pages/user/changepwd.jsp");
            dispatch.forward(request, response);
        }
    }

}
