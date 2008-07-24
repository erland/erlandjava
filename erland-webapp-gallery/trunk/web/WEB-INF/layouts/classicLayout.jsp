<%@ page contentType="text/html;charset=iso8859-1" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
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
    <logic:empty name="stylesheetPB">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/stylesheet.css"
                      type="text/css"/>
    </logic:empty>
    <logic:notEmpty name="stylesheetPB">
        <link rel="stylesheet" href="<bean:write name="stylesheetPB"/>"
                      type="text/css"/>
    </logic:notEmpty>
    <logic:notEmpty name="titlePB">
        <title><bean:write name="titlePB"/></title>
    </logic:notEmpty>
    <logic:empty name="titlePB">
        <title><tiles:getAsString name="title"/></title>
    </logic:empty>
  </HEAD>
<body class="layout">
<table class="layout-main" border="0" width="100%" cellspacing="5">
<tiles:useAttribute name="header" ignore="true" />
<logic:notEmpty name="header">
<tr class="layout-header">
  <td class="layout-header" colspan="2"><tiles:insert attribute="header" /></td>
</tr>
</logic:notEmpty>
<tr class="layout-header-separator-line"><td class="layout-header-separator-line" colspan="2" height="3" valign="top" bgcolor="#397AC0"></td></tr>
<tr class="layout-header-separator" ><td class="layout-header-separator" colspan="2" height="5" valign="top"></td></tr>
<tr class="layout-body">
<tiles:useAttribute name="menu" ignore="true" />
<logic:notEmpty name="menu">
  <td class="layout-body-menu" width="140" valign="top">
    <tiles:insert attribute='menu' />
  </td>
</logic:notEmpty>
<tiles:useAttribute name="body" ignore="true" />
<logic:notEmpty name="body">
  <td class="layout-body-content" valign="top"  align="left">
    <tiles:insert attribute='body' />
  </td>
</logic:notEmpty>
</tr>
<tiles:useAttribute name="footer" ignore="true" />
<logic:notEmpty name="footer">
<tr class="layout-footer">
  <td class="layout-footer" colspan="2">
    <tiles:insert attribute="footer" />
  </td>
</tr>
</logic:notEmpty>
</table>
<tiles:insert attribute="postscript"/>
</body>
</html>