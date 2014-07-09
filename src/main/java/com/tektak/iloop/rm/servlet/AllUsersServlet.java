package com.tektak.iloop.rm.servlet;

import com.tektak.iloop.rm.dao.UserDetailDAO;
import com.tektak.iloop.rm.datamodel.UserDetail;
import com.tektak.iloop.rmodel.RmodelException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tektak on 7/8/14.
 */
@WebServlet("/allusers")
public class AllUsersServlet extends HttpServlet{


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDetailDAO userDetailDAO = new UserDetailDAO();
        UserDetail[] list = null;
        try {
            list = userDetailDAO.fetchUser();
            request.setAttribute("ulist",list);
        }catch (Exception e){
            request.setAttribute("error","Error while fetching");
        }finally {
            userDetailDAO.closeConnection();
        }



        RequestDispatcher dispatch=request.getRequestDispatcher("/pages/user/allUsers.jsp");
        dispatch.forward(request,response);
    }

}
