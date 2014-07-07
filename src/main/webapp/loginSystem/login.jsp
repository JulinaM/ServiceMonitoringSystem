<%--
  Created by IntelliJ IDEA.
  User: tektak
  Date: 7/2/14
  Time: 2:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="<%=request.getContextPath()%>/loginSystem/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="<%=request.getContextPath()%>/loginSystem/css/signin.css" rel="stylesheet">
    <%System.out.println(request.getContextPath());%>
</head>
<body>
    <div class="container">
        <form class="form-signin" role="form" method="post" action="/login">
            <h2 class="form-signin-heading">Please sign in</h2>
            <input name="email" type="email" class="form-control" placeholder="Email address" required autofocus>
            <input name="password" type="password" class="form-control" placeholder="Password" required>
            <lable class="radio">
                <input type="radio" value="0" name="uType">Admin
                <input type="radio" value="1" name="uType">Local
            </lable>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        </form>
    </div><!-- /container -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="<%=request.getContextPath()%>/assets/bootstrap/js/jquery-1.11.1.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<%=request.getContextPath()%>/assets/bootstrap/js/bootstrap.min.js"></script>
    <%--- See more at: http://www.w3resource.com/twitter-bootstrap/tutorial.php#sthash.jOx9N4uP.dpuf--%>
</body>
</html>
