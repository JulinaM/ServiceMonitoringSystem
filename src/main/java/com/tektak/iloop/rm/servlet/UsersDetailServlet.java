package com.tektak.iloop.rm.servlet;

import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.dao.UserDetailDAO;
import com.tektak.iloop.rm.datamodel.UserDetail;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.util.common.BaseException;

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
        int uId = Integer.parseInt((request.getParameter("uid")));
        UserDetailDAO userDetailDAO = null;
        try {
            userDetailDAO = new UserDetailDAO();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }
        UserDetail detail = null;
        detail = userDetailDAO.fetchUser(uId);
        userDetailDAO.closeConnection();
        if (detail != null){

        }
        RequestDispatcher dispatch=request.getRequestDispatcher("/pages/user/usersDetail.jsp");
        dispatch.forward(request,response);
    }

}
