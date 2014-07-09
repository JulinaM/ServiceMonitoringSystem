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
    <title></title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">

            <form role="form" action="adduser" method="post">
                <div class="form-group">
                    <label for="nameInput">Name</label>
                    <input type="text" name="username" class="form-control" id="nameInput" placeholder="Name">
                </div>
                <div class="form-group">
                    <label for="inputEmail1">Email address</label>
                    <input type="email" name="useremail" class="form-control" id="inputEmail1" placeholder="Email">
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
                    <select class="form-control" name="userrole">
                        <option value="1">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-default">Add</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
