<%@ page import="com.tektak.iloop.rm.common.ServletCommon" %>
<%--
  Created by IntelliJ IDEA.
  User: tektak
  Date: 7/17/14
  Time: 5:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<%request.getContextPath();%>/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title>User Detail</title>
    <style>body {
        padding-top: 60px;
    }</style>

</head>
<body>
<%@ include file="../include/navTop.jsp" %>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <form role="form" action="changepwd" method="post">
                <input type="hidden" name="token" value="${token}">

                <div class="form-group">
                    <label for="oldpsswd">Old Password</label>
                    <input type="password" name="oldpassword" class="form-control" id="oldpsswd" required>
                </div>

                <div class="form-group">
                    <label for="psswd">New Password</label>
                    <input type="password" name="password" class="form-control" id="psswd" pattern=".{5,10}" required>
                    (Minimum 5 characters)
                </div>
                <div class="form-group">
                    <label for="psswd1">Retype New Password</label>
                    <input type="password" name="password1" class="form-control" id="psswd1" required>

                    <div id="msg"></div>
                </div>
                <button type="submit" class="btn btn-danger" id="sbtn" disabled>Change</button>
            </form>
        </div>
    </div>
</div>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script>
    $(document).ready(
            function () {
                $('#psswd1').keyup(
                        function () {
                            $pass1 = $("#psswd").val();
                            $pass2 = $("#psswd1").val();
                            if ($pass1 != $pass2) {
                                $("#msg").html("Passwords do not match");
                                $("#sbtn").attr("disabled","disabled");
                            } else {
                                $("#msg").html("Passwords match");
                                $("#sbtn").removeAttr("disabled");
                            }
                        });
            });
</script>
<script src="<%request.getContextPath();%>/assets/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>

