<%@ page session="true"  %>
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
 <%
     response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
     response.setHeader("Pragma","no-cache"); //HTTP 1.0
     response.setHeader ("Expires", "0"); //prevents caching at the proxy server
 %>
<title>Akvariedagboken</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<font class="normal">
<center>
<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
<tr>
<td colspan="2" width="100%" height="80" valign="center" align="center">
<a href="portal?do=logout"><img src="images/logo.gif" border="0"></img></a>
<%
String p = (String) request.getAttribute("header");
if(p!=null) {
    %>
    <jsp:include page="<%=p%>"/>
    <%
}
%>
</td>
</tr>
<tr>
<td colspan="2" height="3" valign="top" bgcolor="#397AC0">
</td>
</tr>
<tr>
<td height="5" valign="top" bgcolor="#E5F4FF">
</td>
</tr>
<tr>
<td width="300" height="100%" valign="top" align="left" bgcolor="#E5F4FF">
<%
p = (String) request.getAttribute("menu");
if(p!=null) {
    %>
    <jsp:include page="<%=p%>"/>
    <%
}
%>
</td>
<td width="100%" heigth="100%" class="margin" valign="top" align="left">
<%
p = (String) request.getAttribute("content");
if(p!=null) {
    %>
    <jsp:include page="<%=p%>"/>
    <%
}
%>
</td>
</table>
</center>
</font>
</body>