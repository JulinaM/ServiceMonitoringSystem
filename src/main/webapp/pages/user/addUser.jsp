<%--
  Created by IntelliJ IDEA.
  User: tektak
  Date: 7/3/14
  Time: 11:53 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<%request.getContextPath();%>/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title></title>
    <style>body {
        padding-top: 60px;
    }</style>
</head>
<body>
<%@ include file="../include/navTop.jsp" %>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">

            <form role="form" action="adduser" method="post">
                <input type="hidden" name="token" value="${token}">

                <div class="form-group">
                    <label for="nameInput">Name</label>
                    <input type="text" name="username" class="form-control" id="nameInput" placeholder="Name"
                           required>
                </div>
                <div class="form-group">
                    <label for="inputEmail1">Email address</label>
                    <input type="email" name="useremail" class="form-control" id="inputEmail1"
                           placeholder="Email" required>
                </div>
                <div class="form-group">
                    <label>User Status</label>
                    <label class="radio-inline">
                        <input type="radio" name="userstatus" id="active" value="1" checked>Active
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="userstatus" id="inactive" value="0">Inactive
                    </label>
                </div>
                <div class="form-group">
                    <label>User Role</label>
                    <input type="checkbox" name="userrole" value="ViewUser">View User
                    <input type="checkbox" name="userrole" value="AddUser">Add User
                    <input type="checkbox" name="userrole" value="UpdateUser">Update User
                    <input type="checkbox" name="userrole" value="DeleteUser">Delete User
                    <input type="checkbox" name="userrole" value="ViewLog">View Log
                    <input type="checkbox" name="userrole" value="DeleteLog">Delete Log

                </div>
                <button type="submit" class="btn btn-default">Add</button>
            </form>
        </div>
    </div>
</div>
<%@ include file="../include/footer.jsp" %>
</body>
</html>
