<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<bean:message key="homepage.account.new.new-user-created"/>
<table class="propertypage-body">
    <tr>
    <td><bean:message key="homepage.account.new.username"/></td>
    <td><bean:write name="accountPB" property="username"/></td>
    </tr>
    <tr>
    <td><bean:message key="homepage.account.new.firstname"/></td>
    <td><bean:write name="accountPB" property="firstName"/></td>
    </tr>
    <tr>
    <td><bean:message key="homepage.account.new.lastname"/></td>
    <td><bean:write name="accountPB" property="lastName"/></td>
    </tr>
</table>
<bean:message key="homepage.account.new.login-message"/>
<form name="loginForm" action="j_security_check" method="POST">
    <table class="propertypage-body">
    <tr><td><bean:message key="homepage.login.username"/></td><td>
    <input type="text" name="j_username" value="<bean:write name="accountPB" property="username"/>">
    </td></tr>
    <tr><td><bean:message key="homepage.login.password"/></td><td>
    <input type="password" name="j_password" value="">
    </td></tr>
    <tr><td></td><td>
    <input type="submit" value="<bean:message key="homepage.login.button"/>">
    </td></tr>
    </table>
</form>
