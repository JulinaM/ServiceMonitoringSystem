package com.tektak.iLoop.rm.servlet;

import com.tektak.iLoop.rm.application.loginSystem.AuthenticateUser;
import com.tektak.iLoop.rm.common.CommonConfig;
import com.tektak.iLoop.rm.common.RmException;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.util.common.BaseException;
import com.tektak.iloop.util.configuration.Config;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by tektak on 7/2/14.
 */
public class LoginServlet extends HttpServlet {

    public void init(ServletConfig servletConfig){
        String RealPath=servletConfig.getServletContext().getRealPath("WEB-INF/classes/configuration.properties");
        try {
            CommonConfig commonConfig=new CommonConfig(RealPath);
            Config config=commonConfig.getConfig();
            System.out.println("Username::"+config.ReadString("username"));
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        }
    }
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
        String address="/pages/loginSystem/login.jsp";
        RequestDispatcher dispatcher;

        if(email==null||password==null){
            address="/pages/loginSystem/login.jsp";
            request.setAttribute("msg","Invalid Username or Password!!");
        }else{
            try {
                AuthenticateUser authenticateUser=new AuthenticateUser();
                if(authenticateUser.IsAuthenticateUser(email,password)==1){
                    System.out.println("Authenticate user!! navigting to new page!!");
                    System.out.println("email:"+email+" password:"+password+"    User Type:"+uType);
                    //address="/pages/loginSystem/home.jsp";
                    address="/pages/loginSystem/home.jsp";

                    HttpSession httpSession=request.getSession();
                    httpSession.setAttribute("email",email);
                    request.setAttribute("email",email);
                    httpSession.setAttribute("password",password);
                    request.setAttribute("password",password);
                }else{
                    request.setAttribute("msg","Invalid Username or Password!!");
                    System.out.println("Not Authenticate user!! navigting to Login page!!");
                    PrintWriter ps=response.getWriter();
                    ps.append("Not Authenticate user!! navigting to Login page!!....");
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
        }
        dispatcher= request.getRequestDispatcher(address);
        dispatcher.forward(request,response);

    }
}
