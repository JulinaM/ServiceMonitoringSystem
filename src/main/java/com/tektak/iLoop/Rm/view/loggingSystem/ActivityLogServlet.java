package com.tektak.iLoop.Rm.view.loggingSystem;

import com.tektak.iLoop.Rm.view.loggingSystem.log.Log;
import com.tektak.iLoop.Rm.view.loggingSystem.log.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

/**
 * Created by tektak on 7/3/14.
 */
public class ActivityLogServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        java.util.Date date= new java.util.Date();
        Timestamp timestamp=new Timestamp(date.getTime());

        LogDataModel logDataModel=new LogDataModel();
        logDataModel.setTimestamp(timestamp);
        logDataModel.setUID("1");
        logDataModel.setUserActivity("Password has been Changed");

        System.out.println("UID"+logDataModel.getUID());
        System.out.println("Actvity"+logDataModel.getUserActivity());
        System.out.println("Time"+logDataModel.getTimestamp());

        PrintWriter ps=response.getWriter();

        ps.println("UID::" + logDataModel.getUID());
        ps.println("Actvity::" + logDataModel.getUserActivity());
        ps.println("Time::" + logDataModel.getTimestamp());

        LogFactory logFactory=new LogFactory();
        Log log1=logFactory.getLog("Password");
        ps.println(log1.generateLog());

        Log log2=logFactory.getLog("Profile");
        ps.println(log2.generateLog());

        Log log3=logFactory.getLog("Add");
        ps.println(log3.generateLog());

        Log log4=logFactory.getLog("DeleTE");
        ps.println(log4.generateLog());
    }
}
