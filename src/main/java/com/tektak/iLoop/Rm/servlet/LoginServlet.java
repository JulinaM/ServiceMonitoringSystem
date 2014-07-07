package com.tektak.iLoop.Rm.servlet;

import com.tektak.iLoop.Rm.Application.loginSystem.AuthenticateUser;
import com.tektak.iLoop.Rm.Common.RmException;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.util.common.BaseException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.sql.SQLException;

/**
 * Created by tektak on 7/2/14.
 */
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    /**
     * Authenthicate the email and password with database record
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        String uType=request.getParameter("uType");
        if(email==null||password==null||uType==null){
            RequestDispatcher dispatcher= request.getRequestDispatcher("/loginSystem/login.jsp");
            dispatcher.forward(request,response);
        }else{
            try {
                AuthenticateUser authenticateUser=new AuthenticateUser();
                if(authenticateUser.IsAuthenticateUser(email,password)==1){
                    System.out.println("Authenticate user!! navigting to new page!!");
                }else{
                    System.out.println("Not Authenticate user!! navigting to new page!!");
                }
            } catch (RmodelException.SqlException e) {
                e.printStackTrace();
            } catch (SQLException e){
            } catch (RmException.DBConnectionError dbConnectionError) {
                dbConnectionError.printStackTrace();
            } catch (BaseException.ConfigError configError) {
                configError.printStackTrace();
            } catch (RmodelException.CommonException e) {
                e.printStackTrace();
            }
            System.out.println("email:"+email+" password:"+password+"    User Type:"+uType);
            RequestDispatcher dispatcher= request.getRequestDispatcher("/loginSystem/home.jsp");

            request.setAttribute("email",email);
            request.setAttribute("password",password);
            request.setAttribute("uType",uType);
            dispatcher.forward(request,response);
        }

    }
}
