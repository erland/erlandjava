<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<center>
    <table width="100%" class="no-border-tight">
        <tr class="header-logo"><td class="header-logo" width="100%" align="center"><a class="header-logo" href="<html:rewrite page="/do/logout"/>"><img src="<%=request.getContextPath()%>/images/logo.gif" border="0"></img></a></td></tr>
        <tr class="header-text"><td class="header-text" align="center"><div class="header-text"><bean:message key="dirgallery.welcome.title"/></div></td></tr>
        <tr class="header-separator-line"><td class="header-separator-line" width="100%" height="3" valign="top" bgcolor="#397AC0"></td></tr>
    </table>
</center>

