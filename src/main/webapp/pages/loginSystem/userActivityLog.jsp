<%@ page import="com.tektak.iloop.rm.datamodel.UserDetail" %>
<%@ page import="com.tektak.iloop.rm.datamodel.ULogDM" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>

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
</head>
<body>
<%

    String Email;
    String Password;

    HttpSession httpsession = request.getSession(false);
    JSONObject jsonObjectOfUserDetails=(JSONObject)request.getAttribute("jsonArrayOfUserDetails");

%>

<div id="loginStatus">
    Email::<%=httpsession.getAttribute("userEmail")%><br/>
    UserName::<%=httpsession.getAttribute("userName")%><br/>
    JoinDate::<%=httpsession.getAttribute("userJoinDate")%><br/>
    <a href="/logout?logout=set">Logout</a>
</div>
<div id="logOrder">
    Filter By:
    <form method="GET" action="/UserActivitylog" onchange="this.submit();">
        User::<select name="filter-by-user">
        <%--<%int jsonSize=jsonObjectOfUserDetails.length();
            for(int i=0;i<jsonSize;i++){%>
                <option value="<%=jsonObjectOfUserDetails.get("userName")%>"><%=jsonObjectOfUserDetails.get("userName")%></option>
     <%       }  %>--%>
        </select></form>
</div>
<h1>JSON object passing</h1>

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
        JSONArray jsonArray = (JSONArray) request.getAttribute("jsonArrayOfLogs");

        int ArraySize = jsonArray.length();
        for (int i = 0; i < ArraySize; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
    %>
    <tr>
        <td><%=jsonObject.get("userIPaddress")%>
        </td>
        <td><%=jsonObject.get("userId")%>
        </td>
        <td><%=jsonObject.get("userActivity")%>
        </td>
        <td><%=jsonObject.get("userTimestamp")%>
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
