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
<%
String p = (String) request.getAttribute("content");
if(p!=null) {
    %>
    <jsp:include page="<%=p%>"/>
    <%
}
%>
</body>