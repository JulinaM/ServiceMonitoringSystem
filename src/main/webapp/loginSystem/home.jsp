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
    <h1>Welcome To the home page of Service Monitoring System.</h1>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    Email:<c:out value="${email}"/><br>
    Password:<c:out value="${password}"/><br>
    User Type:<c:out value="${uType}"/><br>
    <hr>

    <h1>::Student Detail is Displayed Below::</h1>


</body>
</html>
