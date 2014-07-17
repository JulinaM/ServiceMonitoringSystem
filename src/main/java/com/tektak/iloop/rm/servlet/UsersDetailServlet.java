package com.tektak.iloop.rm.servlet;

import com.tektak.iloop.rm.common.OurSession;
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
 * Created by tektak on 7/2/14.
 */

@WebServlet("/usersdetail")
public class UsersDetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (OurSession.getSession(request.getSession(false)) == null) {
            response.sendRedirect("/login");
            return;
        } else {
            Integer uId = null;
            UserDetailDAO userDetailDAO = null;
            UserDetail detail = null;
            try {
                uId = Integer.parseInt((request.getParameter("uid")));
            } catch (Exception e) {
                request.setAttribute("error", "User not Selected");
            }
            if (uId != null) {
                try {
                    userDetailDAO = new UserDetailDAO();
                    detail = userDetailDAO.fetchUser(uId);
                    if(detail.getUserId()==0){
                        throw new Exception();
                    }
                    request.setAttribute("detail", detail);
                } catch (Exception e) {
                    request.setAttribute("error", "No such user");
                } finally {
                    if (userDetailDAO != null)
                        userDetailDAO.closeConnection();
                }
            }


            RequestDispatcher dispatch = request.getRequestDispatcher("/pages/user/usersDetail.jsp");
            dispatch.forward(request, response);
        }
    }

}
