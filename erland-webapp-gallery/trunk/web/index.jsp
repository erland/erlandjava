<%@ page session="true"  %>
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
 <%
     response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
     response.setHeader("Pragma","no-cache"); //HTTP 1.0
     response.setHeader ("Expires", "0"); //prevents caching at the proxy server
 %>
<title>Gallery page</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
<tr>
<td width="300" height="100" valign="center" halign="center" background="gradient.gif">
<a href="portal?do=logout"><img src="logo.gif" border="0"></img></a>
</td>
<td width="10" bgcolor="#76C7FF"></td>
<td width="*" bgcolor="#76C7FF">
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
<td height="10" valign="top" background="gradient.gif">
</td>
<td background="gradient4.gif"></td>
<td background="gradient3.gif"></td>
</tr>
<tr>
<td height="*" valign="top" background="gradient.gif">
<%
p = (String) request.getAttribute("menu");
if(p!=null) {
    %>
    <jsp:include page="<%=p%>"/>
    <%
}
%>
</td>
<td width="10" valign="top" background="gradient2.gif">
</td>
<td valign="top">
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
</body>