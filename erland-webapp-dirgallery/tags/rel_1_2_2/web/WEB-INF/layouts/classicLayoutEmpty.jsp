<%@ page contentType="text/html;charset=iso8859-1" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<HTML>
  <HEAD>
    <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
     <%
         response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
         response.setHeader("Pragma","no-cache"); //HTTP 1.0
         response.setHeader ("Expires", "0"); //prevents caching at the proxy server
     %>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/stylesheet.css"
                  type="text/css"/>
    <title><tiles:getAsString name="title"/></title>
  </HEAD>
<body class="layoutempty">
<center>
<table class="layoutempty-main" border="0" valign="top" cellspacing="5">
<tiles:useAttribute name="body" ignore="true" />
 <logic:notEmpty name="body">
<tr class="layoutempty-body-content">
  <td class="layoutempty-body-content" valign="top"  align="left">
    <tiles:insert attribute='body' />
  </td>
</tr>
</logic:notEmpty>
</table>
</center>
</body>
</html>