<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="propertypage-body">
<logic:iterate name="accountsPB" id="account">
    <tr>
    <td>
    <a class="propertypage-button" href="<html:rewrite page="/do/user/viewotheruseraccount"/>?username=<bean:write name="account" property="username"/>">
    <div align="center"><bean:write name="account" property="firstName"/> <bean:write name="account" property="lastName"/></div>
    </a>
    </td>
    </tr>
    <tr><td>&nbsp</td></tr>
</logic:iterate>
</table>
