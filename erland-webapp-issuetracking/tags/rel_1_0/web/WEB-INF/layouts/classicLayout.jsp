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
<body>
<center>
<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
<tiles:useAttribute name="header" ignore="true" />
<logic:notEmpty name="header">
<tr>
  <td colspan="2"><tiles:insert attribute="header" /></td>
</tr>
</logic:notEmpty>
<tr><td colspan="2" height="5" valign="top"></td></tr>
<tr>
<tiles:useAttribute name="menu" ignore="true" />
<logic:notEmpty name="menu">
  <td width="15%" height="100%" valign="top">
    <tiles:insert attribute='menu' />
  </td>
</logic:notEmpty>
<tiles:useAttribute name="body" ignore="true" />
<logic:notEmpty name="body">
  <td valign="top" height="100%" align="left">
    <tiles:insert attribute='body' />
  </td>
</logic:notEmpty>
</tr>
<tiles:useAttribute name="footer" ignore="true" />
<logic:notEmpty name="footer">
<tr>
  <td colspan="2">
    <tiles:insert attribute="footer" />
  </td>
</tr>
</logic:notEmpty>
</table>

</body>
</html>