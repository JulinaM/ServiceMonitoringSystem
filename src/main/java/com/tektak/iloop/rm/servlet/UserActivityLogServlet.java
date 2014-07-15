package com.tektak.iloop.rm.servlet;

import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.dao.ULogDAO;
import com.tektak.iloop.rm.dao.UserDetailDAO;
import com.tektak.iloop.rm.datamodel.ULogDM;
import com.tektak.iloop.rm.datamodel.UserDetail;
import com.tektak.iloop.rmodel.RmodelException;
import com.tektak.iloop.util.common.BaseException;
import org.hamcrest.core.IsNull;
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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by tektak on 7/3/14.
 */
@WebServlet("/UserActivitylog")
public class UserActivityLogServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session=request.getSession(false);
        String jsonObjectString=(String)session.getAttribute("session");
         if(jsonObjectString==null){
            response.sendRedirect("/login");
            return;
        }

        String filterByUser=(String)request.getParameter("filter-by-user");
        if(filterByUser==""){
            System.out.println("filter-by-user==");
        }else{
            System.out.println("filter-by-user=="+filterByUser);
        }
        ULogDAO uLogDAO= null;
        try {
            uLogDAO = new ULogDAO();
            ULogDM[] logs=uLogDAO.ReadAllLog();

            JSONArray jsonArrayOfLogs=new JSONArray();
            int i=0;
            for(ULogDM u:logs){
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("userId", logs[i].getUID());
                jsonObject1.put("userIPaddress", logs[i].getIPaddress());
                jsonObject1.put("userActivity", logs[i].getUserActivity());
                jsonObject1.put("userTimestamp", logs[i].getTimestamp());

                jsonArrayOfLogs.put(jsonObject1);
                i++;
            }

            UserDetailDAO userDetailDAO=new UserDetailDAO();
            UserDetail[] userDetails=userDetailDAO.fetchUser();
            System.out.println(userDetails[0].getJoinDate());
            JSONArray jsonArrayOfUserDetails=new JSONArray();
            for(UserDetail u:userDetails){
                i=0;
                JSONObject jsonObject2=new JSONObject();
                jsonObject2.put("userId", userDetails[i].getUserId());
                jsonObject2.put("userEmail", userDetails[i].getUserEmail());
                jsonObject2.put("userName", userDetails[i].getUserName());
                jsonObject2.put("userStatus", userDetails[i].getUserStatus());
                jsonObject2.put("joinDate", userDetails[i].getJoinDate());
                jsonArrayOfUserDetails.put(jsonObject2);
                i++;
            }

            System.out.println(jsonArrayOfUserDetails);
            request.setAttribute("jsonArrayOfUserDetails",jsonArrayOfUserDetails);

            System.out.println(jsonArrayOfLogs);
            request.setAttribute("jsonArrayOfLogs",jsonArrayOfLogs);
            RequestDispatcher dispatcher=request.getRequestDispatcher("/pages/loginSystem/userActivityLog.jsp");
            dispatcher.forward(request,response);
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
