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
 * Created by tektak on 7/2/14.
 */

@WebServlet("/usersdetail")
public class UsersDetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer  uId=null;
        try {
            uId = Integer.parseInt((request.getParameter("uid")));
        }catch (Exception e){
            request.setAttribute("error","User not Selected");
        }
        if (uId != null) {
            UserDetailDAO userDetailDAO = new UserDetailDAO();
            UserDetail detail = null;
            try {
                detail = userDetailDAO.fetchUser(uId);
                request.setAttribute("detail",detail);
            }catch (Exception e){
                request.setAttribute("error","No such user");
            }finally {
                userDetailDAO.closeConnection();
            }
        }



        RequestDispatcher dispatch=request.getRequestDispatcher("/pages/user/usersDetail.jsp");
        dispatch.forward(request,response);
    }

}
