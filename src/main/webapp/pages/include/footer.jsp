<%@ page import="com.tektak.iloop.rm.common.ServletCommon" %>

<script src="<%=request.getContextPath()%>/assets/bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/bootstrap/js/bootstrap.min.js"></script>

<!-- bootbox code -->
<script src="<%=request.getContextPath()%>/assets/bootstrap/js/bootbox.min.js"></script>
<% String errMsg = ServletCommon.getErrMsg(request);
    if (errMsg != null) {%>

<script>
    bootbox.alert("Error::<%=errMsg%>!!");
</script>

<% }
%>
