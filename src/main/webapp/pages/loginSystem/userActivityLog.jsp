<%@ page import="com.tektak.iloop.rm.datamodel.UserDetail" %>
<%@ page import="com.tektak.iloop.rm.datamodel.ULogDM" %>
<%@ page import="com.tektak.iloop.rm.dao.ULogDAO" %>
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
    <link href="<%=request.getContextPath()%>/pages/loginSystem/css/userActivityLog.css" rel="stylesheet" media="screen">
</head>
<body>
        <% String Email;
           String Password;
        %>

        <h5>Retrieved from Session</h5>
        <% HttpSession  httpsession=request.getSession();
            UserDetail validUser=(UserDetail)httpsession.getAttribute("ValidUser");
        %>

        Email::<%=validUser.getUserEmail()%><br/>
        UserName::<%=validUser.getUserName()%><br/>
    <hr/><hr/>
        <h1>Welcome To the User Activity Log Management System.</h1>
    <table class="table table-striped table-bordered table-condensed table-hover">
        <thead>
            <tr><th>Host IP</th><th>User</th><th>Activity</th><th>Date and Time</th></tr>
        </thead>
        <tbody>
            <%ULogDM[] log= (ULogDM[])request.getAttribute("logs");
                int logSize=log.length;
                for (int i=0;i<logSize;i++){%>
                    <tr><td><%=log[i].getIPaddress()%></td><td><%=log[i].getUID()%></td><td><%=log[i].getUserActivity()%></td><td><%=log[i].getTimestamp()%></td></tr>

            <%    }
            %>
        </tbody>
    </table>

        <script src="<%=request.getContextPath()%>/assets/bootstrap/js/jquery-1.11.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/assets/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
