package com.tektak.iloop.rm.servlet;

import com.tektak.iloop.rm.application.logSystem.LogGenerator;
import com.tektak.iloop.rm.application.loginSystem.AuthenticateUser;
import com.tektak.iloop.rm.common.CommonConfig;
import com.tektak.iloop.rm.common.DateTime;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.dao.UserDetailDAO;
import com.tektak.iloop.rm.datamodel.UserDetail;
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
import java.util.Date;

/**
 * Created by tektak on 7/2/14.
 */
@WebServlet("/login")
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
        String address=null;
        RequestDispatcher dispatcher;
        System.out.println("email=="+email+"and password=="+password);

        if(email==null||password==null){
            address="/pages/loginSystem/login.jsp";
        }else{
            UserDetailDAO userDetailDAO=null;
            try {
                new CommonConfig(request);
                userDetailDAO=new UserDetailDAO();
                 if(userDetailDAO.userAuth(email, password)==1){
                    UserDetail userDetail=userDetailDAO.getUserDetail();
                    address="/UserActivitylog";
                    LogGenerator.generateLog(userDetail.getUserId(),request.getRemoteAddr(),"Logged into the system Successfully!!");//
                    HttpSession httpSession=request.getSession();
                    httpSession.setAttribute("ValidUser",userDetail);
                }else{
                     address="/pages/loginSystem/login.jsp";
                    request.setAttribute("msg","Invalid Username or Password!!");
                }
            } catch (RmodelException.SqlException e) {
                e.printStackTrace();
            } catch (RmException.DBConnectionError dbConnectionError) {
                dbConnectionError.printStackTrace();
            } catch (BaseException.ConfigError configError) {
                configError.printStackTrace();
            } catch (RmodelException.CommonException e) {
                e.printStackTrace();
            }finally {
                userDetailDAO.closeConnection();
            }
        }
        dispatcher= request.getRequestDispatcher(address);
        dispatcher.forward(request,response);

    }
}
