<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
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
<table border="0" width="100%" cellspacing="5">
<tr>
  <td colspan="2"><tiles:insert attribute="header" /></td>
</tr>
<tr><td colspan="2" height="3" valign="top" bgcolor="#397AC0"></td></tr>
<tr><td colspan="2" height="5" valign="top"></td></tr>
<tr>
  <td valign="top"  align="left">
    <tiles:insert attribute='body' />
  </td>
</tr>
<tr>
  <td colspan="2">
    <tiles:insert attribute="footer" />
  </td>
</tr>
</table>
</body>
</html>