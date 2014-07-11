package com.tektak.iloop.rm.servlet;

import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.dao.ULogDAO;
import com.tektak.iloop.rm.datamodel.ULogDM;
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
 * Created by tektak on 7/3/14.
 */
@WebServlet("/UserActivitylog")
public class UserActivityLogServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filterByUser=(String)request.getParameter("filter-by-user");
        if(filterByUser==""){
            System.out.println("filter-by-user==");
        }else{
            System.out.println("filter-by-user=="+filterByUser);
        }
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ULogDAO uLogDAO= null;
        try {
            uLogDAO = new ULogDAO();
            ULogDM[] logs=uLogDAO.ReadAllLog();
            request.setAttribute("logs",logs);
            RequestDispatcher dispatcher=request.getRequestDispatcher("/pages/loginSystem/userActivityLog.jsp");
            dispatcher.forward(request,response);
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }finally {
            uLogDAO.closeDbConnection();
        }


    }
}
