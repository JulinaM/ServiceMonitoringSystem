package com.tektak.iloop.rm.servlet;

import com.tektak.iloop.rm.common.RmException;
import com.tektak.iloop.rm.common.ServletCommon;
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
import java.util.Calendar;
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

        String fy=(String)request.getParameter("from-filter-by-year");
        String fm=(String)request.getParameter("from-filter-by-month");
        String fd=(String)request.getParameter("from-filter-by-day");
        String ty=(String)request.getParameter("to-filter-by-year");
        String tm=(String)request.getParameter("to-filter-by-month");
        String td=(String)request.getParameter("to-filter-by-day");

        String fdate=fy+"-"+fm+"-"+fd;
        String tdate=ty+"-"+tm+"-"+td;
        System.out.println("Sabai vanda bahira-- /// from Date::"+fy+"/"+fm+"/"+fd);
        System.out.println("To Date::"+ty+"/"+tm+"/"+td);
        Calendar now=Calendar.getInstance();
        String today=now.get(Calendar.YEAR)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.DAY_OF_MONTH);

        String logIdToDelete=(String)request.getParameter("logIdToDelete");

        if(filterByUser!=""){
            System.out.println("filter-by-user=="+filterByUser);
        }
        ULogDAO uLogDAO= null;
        try {
            uLogDAO = new ULogDAO();
            if(logIdToDelete!=null){
                uLogDAO.deleteLogByLogId(Integer.parseInt(logIdToDelete));
            }
            String selectedUId=(String)request.getParameter("filter-by-user");
            System.out.println("selectedUId"+selectedUId);
            ULogDM[] logs=null;

            if(selectedUId!=null&&fy!=null&&fy!=null&&fm!=null&&fd!=null&&ty!=null&&tm!=null&&td!=null) {
                if(selectedUId.equals("all")&&fdate.equals("2014-1-1")&&today.equals(tdate)){
                    selectedUId="all";
                    request.setAttribute("selectedUId",selectedUId);
                    request.setAttribute("fy","2014");
                    request.setAttribute("fm","1");
                    request.setAttribute("fd","1");

                    logs=uLogDAO.ReadLogByFilter(fdate,tdate);

                    System.out.println("IF() vitra /// from Date::"+fy+"/"+fm+"/"+fd);
                    System.out.println("To Date::"+ty+"/"+tm+"/"+td);
                }else {
                    if(selectedUId.equals("all")){
                        logs=uLogDAO.ReadLogByFilter(fdate,tdate);
                    }else{
                        logs=uLogDAO.ReadLogByFilter(selectedUId,fdate,tdate);
                    }
                    request.setAttribute("selectedUId",selectedUId );

                    request.setAttribute("fy",fy);
                    request.setAttribute("fm",fm);
                    request.setAttribute("fd",fd);

                    request.setAttribute("ty",ty);
                    request.setAttribute("tm",tm);
                    request.setAttribute("td",td);

                    System.out.println("if ko else vitra /// from Date::"+fy+"/"+fm+"/"+fd);
                    System.out.println("To Date::"+ty+"/"+tm+"/"+td);
                    System.out.println("No. of rows::"+logs.length);
                }

            }else{
                selectedUId="all";
                request.setAttribute("selectedUId",selectedUId);

                request.setAttribute("fy","2014");
                request.setAttribute("fm","1");
                request.setAttribute("fd","1");

                ty= String.valueOf(now.get(Calendar.YEAR));
                tm= String.valueOf(now.get(Calendar.MONTH));
                td= String.valueOf(now.get(Calendar.DAY_OF_MONTH));

                System.out.println("initial phase // from Date::"+fy+"/"+fm+"/"+fd);
                System.out.println("To Date::"+ty+"/"+tm+"/"+td);

                request.setAttribute("ty",ty);
                request.setAttribute("tm",tm);
                request.setAttribute("td",td);

                logs=uLogDAO.ReadAllLog();
            }


            JSONArray jsonArrayOfLogs=new JSONArray();
            int i=0;
            for(ULogDM u:logs){
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("logId", logs[i].getLogId());
                jsonObject1.put("userId", logs[i].getUID());
                jsonObject1.put("userName", logs[i].getUserName());
                jsonObject1.put("userIPaddress", logs[i].getIPaddress());
                jsonObject1.put("userActivity", logs[i].getUserActivity());
                jsonObject1.put("userTimestamp", logs[i].getTimestamp());

                jsonArrayOfLogs.put(jsonObject1);
                i++;
            }
            UserDetailDAO userDetailDAO=new UserDetailDAO();
            UserDetail[] userDetails=userDetailDAO.fetchUser();
            JSONArray jsonArrayOfUserDetails=new JSONArray();

            for(UserDetail u:userDetails){
                JSONObject jsonObject2=new JSONObject();
                jsonObject2.put("userId", u.getUserId());
                jsonObject2.put("userEmail", u.getUserEmail());
                jsonObject2.put("userName", u.getUserName());
                jsonObject2.put("userStatus", u.getUserStatus());
                jsonObject2.put("joinDate", u.getJoinDate());
                jsonArrayOfUserDetails.put(jsonObject2);
            }


            request.setAttribute("jsonArrayOfUserDetails",jsonArrayOfUserDetails);
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
