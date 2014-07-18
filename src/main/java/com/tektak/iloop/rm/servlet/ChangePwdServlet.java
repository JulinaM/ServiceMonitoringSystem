package com.tektak.iloop.rm.servlet;

import com.tektak.iloop.rm.application.logSystem.LogGenerator;
import com.tektak.iloop.rm.common.*;
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
import java.sql.SQLException;

/**
 * Created by tektak on 7/17/14.
 */
@WebServlet("/changepwd")
public class ChangePwdServlet extends HttpServlet {
    String message = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDetail sessData = OurSession.getSession(request.getSession(false));
        if (sessData == null) {
            response.sendRedirect("/login");
            return;
        } else {
            String oldPass = request.getParameter("oldpassword");
            String pass1 = request.getParameter("password");
            String pass2 = request.getParameter("password1");
            if (pass1 != null && !pass1.isEmpty() && !oldPass.isEmpty() && pass1.equals(pass2) && request.getParameter("token").equals(ServletCommon.generateToken(request.getSession()))) {
                UserDetailDAO userDetailDAO = new UserDetailDAO();
                try {
                    if (userDetailDAO.userAuth(sessData.getUserEmail(), oldPass) == 1) {
                        UserDetail userDetail = new UserDetail();
                        userDetail.setUserId(sessData.getUserId());
                        userDetail.setUserPassword(PasswordEnc.encrypt(sessData.getUserEmail(), pass2));
                        if (userDetailDAO.changePass(userDetail) == 1) {
                            ServletCommon.setSuccessMsg("pwdCsuccess");
                            LogGenerator.generateLog(sessData.getUserId(), request.getRemoteAddr(), CommonConfig.getConfig().ReadString("pwdCsuccess"));
                        }else {
                            LogGenerator.generateLog(sessData.getUserId(), request.getRemoteAddr(), CommonConfig.getConfig().ReadString("pwdCfail"));
                            throw new SQLException();
                        }
                    }else {
                        throw new RmodelException.CommonException(message.toString());
                    }
                } catch (SQLException e) {
                    throw new NullPointerException(e.toString());
                } catch (RmodelException.SqlException e) {
                    ServletCommon.setErrorMsg("pwdCfail");
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    ServletCommon.setErrorMsg("pwdCfail");
                    e.printStackTrace();
                } catch (RmodelException.CommonException e) {
                    ServletCommon.setErrorMsg("oldPwdMatch");
                } catch (RmException.DBConnectionError dbConnectionError) {
                    dbConnectionError.printStackTrace();
                } catch (BaseException.ConfigError configError) {
                    configError.printStackTrace();
                } finally {
                    if (userDetailDAO != null)
                        userDetailDAO.closeConnection();
                }
            } else {
                ServletCommon.setErrorMsg("pwdMatch");
            }
            response.sendRedirect("/changepwd");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (OurSession.getSession(request.getSession()) == null) {
            response.sendRedirect("/login");
        } else {
            ServletCommon.getMessage(request);
            request.setAttribute("token", ServletCommon.generateToken(request.getSession()));
            RequestDispatcher dispatch = request.getRequestDispatcher("/pages/user/changepwd.jsp");
            dispatch.forward(request, response);
        }
    }

}
