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
    <link href="<%=request.getContextPath()%>/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="<%=request.getContextPath()%>/pages/loginSystem/css/signin.css" rel="stylesheet">
    <%System.out.println(request.getContextPath());%>
</head>
<body>
    <div class="container">
        <%  String msg=(String)request.getAttribute("msg");
            if(msg!=""){%>
                <span id="ErrMsg"><%=msg%></span>
        <%  }
        %>
        <form class="form-signin" role="form" method="post" action="/loginservlet">
            <h2 class="form-signin-heading">Please sign in</h2>
            <input name="email" type="email" class="form-control" placeholder="Email address" required autofocus>
            <input name="password" type="password" class="form-control" placeholder="Password" required>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        </form>
    </div>
    <script src="<%=request.getContextPath()%>/assets/bootstrap/js/jquery-1.11.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
