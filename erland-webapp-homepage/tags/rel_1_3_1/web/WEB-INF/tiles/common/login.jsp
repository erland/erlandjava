<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<p class="loginpage-description">
<erland-common:expandhtml><erland-common:cfgresource name="welcometext"/></erland-common:expandhtml>
<center>
<tiles:insert page="/WEB-INF/tiles/common/viewuseraccounts.jsp"/>
<form name="loginForm" action="j_security_check" method="POST">
<table class="loginpage-body">
<tr><td><bean:message key="homepage.login.username"/></td><td>
<input type="text" name="j_username" value="">
</td></tr>
<tr><td><bean:message key="homepage.login.password"/></td><td>
<input type="password" name="j_password" value="">
</td></tr>
<tr><td></td><td>
<input type="submit" value="<bean:message key="homepage.login.button"/>">
</td></tr>
</table>
</form>
</center>