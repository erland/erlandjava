<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<%@ page session="true" %>

<center>
<tiles:insert page="/WEB-INF/tiles/company/listcompanies.jsp"/>
<br>
<bean:message key="companylist.welcome.logintext"/>
<br>
<form name="loginForm" action="j_security_check" method="POST">
<table>
<tr><td><bean:message key="companylist.login.username"/></td><td>
<input type="text" name="j_username" value="">
</td></tr>
<tr><td><bean:message key="companylist.login.password"/></td><td>
<input type="password" name="j_password" value="">
</td></tr>
<tr><td></td><td>
<input type="submit" value="<bean:message key="companylist.login.button"/>">
</td></tr>
</table>
</form>
</center>