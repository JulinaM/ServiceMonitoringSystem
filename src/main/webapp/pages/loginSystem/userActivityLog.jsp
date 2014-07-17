<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="com.tektak.iloop.rm.common.ServletCommon" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.tektak.iloop.rm.datamodel.LogReportParamater" %>

<%--
  Created by IntelliJ IDEA.
  User: tektak
  Date: 7/2/14
  Time: 5:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Home</title>
    <link href="<%=request.getContextPath()%>/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="<%=request.getContextPath()%>/pages/loginSystem/css/userActivityLog.css" rel="stylesheet"
          media="screen">
    <style>body {
        padding-top: 60px;
    }</style>
</head>
<body>
<%@ include file="../include/navTop.jsp" %>
<% HttpSession httpsession = request.getSession(false);
    JSONArray jsonArray = null;
    String sessionString = (String) httpsession.getAttribute("session");
    JSONObject jsonSession = new JSONObject(sessionString);

    LogReportParamater lrParam = (LogReportParamater) request.getAttribute("lrParam");
%>
<div id="logOrder">
    Filter By:
    <form method="GET" action="/UserActivitylog" onchange="this.submit();" onkeyup="this.submit()">
        User::<select name="filter-by-user">

        <% jsonArray = (JSONArray) request.getAttribute("jsonArrayOfUserDetails");
            String selectedUId = lrParam.getUId();
            int ArraySize = jsonArray.length();
        %>
        <option value="all" <%=(selectedUId.equals("all")) ? "selected" : ""%>>All</option>
        <%
            for (int i = 0; i < ArraySize; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String userName = (String) jsonObject.getString("userName");
                int UId = (int) jsonObject.getInt("userId");
                String uid = String.valueOf(UId);
        %>
        <option value="<%=UId%>" <%=(uid.equals(selectedUId)) ? "selected" : ""%>><%=userName%>
        </option>
        <% }
            String fy = lrParam.getFy();
            String fm = lrParam.getFm();
            String fd = lrParam.getFd();
            String ty = lrParam.getTy();
            String tm = lrParam.getTm();
            String td = lrParam.getTd();

            String search = lrParam.getSearch();


        %>
    </select>
        From::<select name="from-filter-by-year">
        <% int i = 0;
            for (i = 2014; i < 2050; i++) {%>
        <option value="<%=i%>" <%=(i == Integer.parseInt(fy)) ? "selected" : ""%>><%=i%>
        </option>
        <% }%>
    </select>Year
        <select name="from-filter-by-month">
            <% i = 0;
                for (i = 1; i < 13; i++) {%>
            <option value="<%=i%>"<%=(i == Integer.parseInt(fm)) ? "selected" : ""%>><%=i%>
            </option>
            <% }%>
        </select>Month
        <select name="from-filter-by-day">
            <% i = 0;
                for (i = 1; i < 31; i++) {%>
            <option value="<%=i%>"<%=(i == Integer.parseInt(fd)) ? "selected" : ""%>><%=i%>
            </option>
            <% }%>
        </select>Day


        To::<select name="to-filter-by-year">
        <% Calendar now = Calendar.getInstance();
        %>

        <% i = 0;

            for (i = 2014; i < 2050; i++) {%>
        <option value="<%=i%>" <%=(i == Integer.parseInt(ty)) ? "selected" : ""%>><%=i%>
        </option>
        <% }%>
    </select>Year
        <select name="to-filter-by-month">

            <% i = 0;
                for (i = 1; i < 13; i++) {%>
            <option value="<%=i%>" <%=(i == Integer.parseInt(tm)) ? "selected" : ""%>><%=i%>
            </option>
            <% }%>
        </select>Month
        <select name="to-filter-by-day">

            <% i = 0;
                for (i = 1; i < 31; i++) {%>
            <option value="<%=i%>" <%=(i == Integer.parseInt(td)) ? "selected" : ""%>><%=i%>
            </option>
            <% }%>
        </select>Day
        Search:<input id="sField" type="text" name="search" value="<%=search%>" size="15" maxlength="150" autofocus
                      onfocus="this.value=this.value;">
    </form>
</div>


<table class="table table-striped table-bordered table-condensed table-hover">
    <caption>Welcome To the User Activity Log Management System.</caption>
    <thead>
    <tr>
        <th>Host IP</th>
        <th>User</th>
        <th>Activity</th>
        <th>Date and Time</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <%
        jsonArray = (JSONArray) request.getAttribute("jsonArrayOfLogs");

        ArraySize = jsonArray.length();
        for (i = 0; i < ArraySize; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
    %>
    <tr>
        <td><%=jsonObject.get("userIPaddress")%>
        </td>
        <td><%=jsonObject.get("userName")%>
        </td>
        <td><%=jsonObject.get("userActivity")%>
        </td>
        <td><%=jsonObject.get("userTimestamp")%>
        </td>
        <td>
            <form action="/UserActivitylog" method="post">
                <input type="hidden" name="token" value="<%=ServletCommon.generateToken(request.getSession(false))%>">

                <input type="hidden" name="logIdToDelete" value="<%=jsonObject.get("logId")%>">
                <input type="submit" value="Delete">
            </form>
        </td>
    </tr>

    <% }
    %>
    </tbody>
</table>
<script src="<%=request.getContextPath()%>/assets/bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
