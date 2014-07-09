<%--
  Created by IntelliJ IDEA.
  User: tektak
  Date: 7/2/14
  Time: 3:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<%request.getContextPath();%>/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title>User Detail</title>

</head>
<body>
<c:if test="${error != null}">
    <div class="alert alert-danger"><span class="close" data-dismiss="alert">&times;</span><strong>${error}!</strong></div>
</c:if>
<table class="table table-bordered">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Status</th>
        <th>Role</th>
        <th>Join Date</th>
        <th></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${requestScope['ulist']}" var="list">
        <tr>
            <td>${list.userId}</td>
            <td>${list.userName}</td>
            <td>${list.userEmail}</td>
            <td>${list.userStatus==1 ? "Active" : "Inactive"}</td>
            <td>${list.userRole==1 ? "Super Admin" : "Normal Admin"}</td>
            <td>${list.joinDate}</td>
            <td><a href="">Disable/Enable</a> <a href="">Delete</a> <a href="<%request.getContextPath();%>/usersdetail?uid=${list.userId}">Edit</a></td>
        </tr>
    </c:forEach>

    </tbody>
</table>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="<%request.getContextPath();%>/assets/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
