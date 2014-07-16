<%@ page import="com.tektak.iloop.rm.common.Session" %>
<% Session obj = Session.getSession(request); %>
<%--
  Created by IntelliJ IDEA.
  User: tektak
  Date: 7/16/14
  Time: 2:23 PM
  To change this template use File | Settings | File Templates.
--%>
<nav class="navbar navbar-fixed-top navbar-inverse" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/home">HOME</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav">
                <li><a href="/adduser">Add Users</a>
                </li>
                <li><a href="/allusers">View Users</a>
                </li>
                <li><a href="/UserActivitylog">View log</a>
                </li>
            </ul>
            <li class="dropdown navbar-right">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <%obj.getUserName();%><i class="glyphicon glyphicon-collapse-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li><a href="#"><i class="glyphicon glyphicon-user"></i><% obj.getUserEmail();%></a>
                    </li>
                    <li><a href="#"><i class="glyphicon glyphicon-asterisk"></i> Settings</a>
                    </li>
                    <li class="divider"></li>
                    <li><a href="logout"><i class="glyphicon glyphicon-log-out"></i> Logout</a>
                    </li>
                </ul>
                <!-- /.dropdown-user -->
            </li>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>
<hr>
<div id="loginStatus">
    Email::<%=obj.getUserId()%><br/>
    UserName::<%=obj.getUserEmail()%><br/>
    <a href="/logout?logout=set">Logout</a>
</div>