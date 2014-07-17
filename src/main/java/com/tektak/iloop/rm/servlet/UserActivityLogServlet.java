package com.tektak.iloop.rm.servlet;

import com.tektak.iloop.rm.common.OurSession;
import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.common.ServletCommon;
import com.tektak.iloop.rm.dao.ULogDAO;
import com.tektak.iloop.rm.dao.UserDetailDAO;
import com.tektak.iloop.rm.datamodel.LogReportParamater;
import com.tektak.iloop.rm.datamodel.ULogDM;
import com.tektak.iloop.rm.datamodel.UserDetail;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.util.common.BaseException;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;


/**
 * Created by tektak on 7/3/14.
 */
@WebServlet("/UserActivitylog")
public class UserActivityLogServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ULogDAO uLogDAO = null;
        String generatedToken = ServletCommon.generateToken(request.getSession(false));

        String logIdToDelete = (String) request.getParameter("logIdToDelete");
        try {
            uLogDAO = new ULogDAO();
            String receivedToken = (String) request.getParameter("token");

            if (logIdToDelete != null && receivedToken != null) {
                if (receivedToken.equals(generatedToken)) {
                    uLogDAO.deleteLogByLogId(Integer.parseInt(logIdToDelete));
                } else {
                    response.sendRedirect("/UserActivityLog");
                    return;
                }
            }
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        }
        doGet(request, response);
        return;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletCommon.generateToken(request.getSession(false));
        if (OurSession.getSession(request.getSession(false))==null) {
            response.sendRedirect("/login");
            return;
        }

        LogReportParamater lrParam = new LogReportParamater();
        lrParam.getParameter(request);


        Calendar now = Calendar.getInstance();
        String today = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);

        ULogDAO uLogDAO = null;
        try {
            ULogDM[] logs = null;
            uLogDAO = new ULogDAO();


            if (lrParam.IsNull()) {
                lrParam.setUId("all");
                lrParam.setFromDate("2014", "1", "1");
                lrParam.setToDate(String.valueOf(now.get(Calendar.YEAR)), String.valueOf((now.get(Calendar.MONTH) + 1)), String.valueOf(now.get(Calendar.DAY_OF_MONTH)));
                logs = uLogDAO.ReadAllLog();
            } else {
                if (lrParam.getUId().equals("all") && lrParam.getFromDate().equals("2014-1-1") && lrParam.getToDate().equals(today)) {


                    lrParam.setUId("all");
                    lrParam.setFromDate("2014", "1", "1");

                    logs = uLogDAO.ReadLogByFilter(lrParam.getSearch(), lrParam.getFromDate(), lrParam.getToDate());

                } else {
                    if (lrParam.getUId().equals("all")) {
                        logs = uLogDAO.ReadLogByFilter(lrParam.getSearch(), lrParam.getFromDate(), lrParam.getToDate());
                    } else {
                        logs = uLogDAO.ReadLogByFilter(lrParam.getUId(), lrParam.getSearch(), lrParam.getFromDate(), lrParam.getToDate());
                    }
                }
            }
            request.setAttribute("lrParam", lrParam);

            JSONArray jsonArrayOfLogs = new JSONArray();

            for (ULogDM u : logs) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("logId", u.getLogId());
                jsonObject1.put("userId", u.getUID());
                //jsonObject1.put("userName", u.getUserName());
                jsonObject1.put("userName",u.getUserDetail().getUserName());
                jsonObject1.put("userIPaddress", u.getIPaddress());
                jsonObject1.put("userActivity", u.getUserActivity());
                jsonObject1.put("userTimestamp", u.getTimestamp());

                jsonArrayOfLogs.put(jsonObject1);
            }

            UserDetailDAO userDetailDAO = new UserDetailDAO();
            UserDetail[] userDetails = userDetailDAO.fetchUser();
            JSONArray jsonArrayOfUserDetails = new JSONArray();

            for (UserDetail u : userDetails) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("userId", u.getUserId());
                jsonObject2.put("userEmail", u.getUserEmail());
                jsonObject2.put("userName", u.getUserName());
                jsonObject2.put("userStatus", u.getUserStatus());
                jsonObject2.put("joinDate", u.getJoinDate());
                jsonArrayOfUserDetails.put(jsonObject2);
            }
            request.setAttribute("jsonArrayOfUserDetails", jsonArrayOfUserDetails);
            request.setAttribute("jsonArrayOfLogs", jsonArrayOfLogs);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/loginSystem/userActivityLog.jsp");
            dispatcher.forward(request, response);
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } finally {
            uLogDAO.closeDbConnection();
        }
    }
}
