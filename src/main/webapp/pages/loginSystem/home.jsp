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
</head>
<body>
        <%
            String name = "das";
        %>


    <h1>Welcome To the home page of Service Monitoring System.</h1>
        <% String Email;
           String Password;
        %>

        <% Email=(String)request.getAttribute("email");
           Password=(String)request.getAttribute("password");%>

            Email::<%=Email%><br/>
            Password::<%=Password%><br/>
        <hr/>
        <h5>Retrieved from Session</h5>
        <% HttpSession  httpsession=request.getSession();
            Email=(String)httpsession.getAttribute("email");
            Password=(String)httpsession.getAttribute("password");
        %>
        Email::<%=Email%><br/>
        Password::<%=Password%><br/>

        <%--Email:${email}
        Password:${password}--%>
        <hr>

    <h1>::User Detail is Displayed Below::</h1>


</body>
</html>
