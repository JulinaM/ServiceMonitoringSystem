package com.tektak.iloop.rm.servlet;

import com.tektak.iloop.rm.application.loginSystem.AuthenticateUser;
import com.tektak.iloop.rm.common.CommonConfig;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.util.common.BaseException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet("/loginservlet")
public class LoginServlet extends HttpServlet {

    public void init(ServletConfig servletConfig){
        String RealPath=servletConfig.getServletContext().getRealPath("WEB-INF/classes/configuration.properties");
        try {
            CommonConfig commonCommonConfig =new CommonConfig(RealPath);
            com.tektak.iloop.util.configuration.Config config= commonCommonConfig.getConfig();
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
                }
            } catch (RmodelException.SqlException e) {
                e.printStackTrace();
            } catch (SQLException e){
                e.printStackTrace();
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
