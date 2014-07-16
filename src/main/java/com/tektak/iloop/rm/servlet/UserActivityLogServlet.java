package com.tektak.iloop.rm.servlet;

import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.common.session;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;


/**
 * Created by tektak on 7/3/14.
 */
@WebServlet("/UserActivitylog")
public class UserActivityLogServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*HttpSession session = request.getSession(false);
        String jsonObjectString = (String) session.getAttribute("session");
        if (jsonObjectString == null) {
            response.sendRedirect("/login");
            return;
        }*/

        if(!session.IsValidSession()){
            response.sendRedirect("/login");
            return;
        }

        LogReportParamater lrParam = new LogReportParamater();
        lrParam.getParameter(request);


        System.out.println("Sabai vanda bahira-- /// from Date::" + lrParam.getFromDate());
        System.out.println("To Date::" + lrParam.getToDate());
        Calendar now = Calendar.getInstance();
        String today = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH)+1) + "-" + now.get(Calendar.DAY_OF_MONTH);

        String logIdToDelete = (String) request.getParameter("logIdToDelete");

        ULogDAO uLogDAO = null;
        try {
            uLogDAO = new ULogDAO();
            if (logIdToDelete != null) {
                uLogDAO.deleteLogByLogId(Integer.parseInt(logIdToDelete));
            }


            ULogDM[] logs = null;

            if (lrParam.IsNull()) {
                lrParam.setUId("all");
                lrParam.setFromDate("2014", "1", "1");

                lrParam.setToDate(String.valueOf(now.get(Calendar.YEAR)), String.valueOf((now.get(Calendar.MONTH)+1)), String.valueOf(now.get(Calendar.DAY_OF_MONTH)));


                logs = uLogDAO.ReadAllLog();


            } else {
                if (lrParam.getUId().equals("all") && lrParam.getFromDate().equals("2014-1-1") && lrParam.getToDate().equals(today)) {


                    lrParam.setUId("all");
                    lrParam.setFromDate("2014", "1", "1");

                    logs = uLogDAO.ReadLogByFilter(lrParam.getFromDate(), lrParam.getToDate());

                    System.out.println("IF() vitra /// from Date::" + lrParam.getFromDate());
                    System.out.println("To Date::" + lrParam.getToDate());
                } else {
                    if (lrParam.getUId().equals("all")) {
                        logs = uLogDAO.ReadLogByFilter(lrParam.getFromDate(), lrParam.getToDate());
                    } else {
                        logs = uLogDAO.ReadLogByFilter(lrParam.getUId(), lrParam.getFromDate(), lrParam.getToDate());
                    }

                    System.out.println("if ko else vitra /// from Date::" + lrParam.getFromDate());
                    System.out.println("To Date::" + lrParam.getToDate());
                    System.out.println("No. of rows::" + logs.length);
                }
            }
            request.setAttribute("lrParam", lrParam);

            JSONArray jsonArrayOfLogs = new JSONArray();

            for (ULogDM u : logs) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("logId", u.getLogId());
                jsonObject1.put("userId", u.getUID());
                jsonObject1.put("userName", u.getUserName());
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
            return;
        } catch (RmException.DBConnectionError dbConnectionError) {
            dbConnectionError.printStackTrace();
        } catch (BaseException.ConfigError configError) {
            configError.printStackTrace();
        } catch (RmodelException.SqlException e) {
            e.printStackTrace();
        } catch (RmodelException.CommonException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            uLogDAO.closeDbConnection();
        }
    }
}
