<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<center>
    <table width="100%" class="no-border-tight">
        <tr><td width="100%" align="center"><a class="normal" href="<html:rewrite page="/do/logout"/>"><img src="<%=request.getContextPath()%>/images/logo.gif" border="0"></img></a></td></tr>
        <tr><td align="center"><div class="bold"><bean:message key="issuetracking.welcome.title"/></div></td></tr>
        <tr><td width="100%" height="3" valign="top" bgcolor="#397AC0"></td></tr>
    </table>
</center>

