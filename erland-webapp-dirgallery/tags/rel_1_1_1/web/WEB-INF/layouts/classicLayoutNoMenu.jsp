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
<table border="0" cellspacing="0" cellpadding="0" width="500" height="100%">
<tiles:useAttribute name="header" ignore="true" />
<logic:notEmpty name="header">
<tr>
  <td width="100%" colspan="2"><tiles:insert attribute="header" /></td>
</tr>
</logic:notEmpty>
<tr><td width="100%" colspan="2" height="5" valign="top"></td></tr>
<tiles:useAttribute name="body" ignore="true" />
<logic:notEmpty name="body">
<tr>
  <td valign="top" height="100%" align="left">
    <tiles:insert attribute='body' />
  </td>
</tr>
</logic:notEmpty>
<tiles:useAttribute name="footer" ignore="true" />
<logic:notEmpty name="footer">
<tr>
  <td colspan="2">
    <tiles:insert attribute="footer" />
  </td>
</tr>
</logic:notEmpty>
</table>
</center>
</body>
</html>