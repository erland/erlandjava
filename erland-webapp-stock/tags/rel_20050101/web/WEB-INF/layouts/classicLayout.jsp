<%@ page contentType="text/html;charset=iso8859-1" %>
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
<body class="layout">
<table class="layout-main" border="0" width="100%" cellspacing="5">
<tr class="layout-header">
  <td class="layout-header" colspan="2"><tiles:insert attribute="header" /></td>
</tr>
<tr class="layout-header-separator-line"><td class="layout-header-separator-line" colspan="2" height="3" valign="top" bgcolor="#397AC0"></td></tr>
<tr class="layout-header-separator"><td class="layout-header-separator" colspan="2" height="5" valign="top"></td></tr>
<tr class="layout-body">
  <td class="layout-body-menu" width="140" valign="top">
    <tiles:insert attribute='menu' />
  </td>
  <td class="layout-body-content" valign="top"  align="left">
    <tiles:insert attribute='body' />
  </td>
</tr>
<tr class="layout-footer">
  <td class="layout-footer" colspan="2">
    <tiles:insert attribute="footer" />
  </td>
</tr>
</table>
</body>
</html>