<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<bean:message key="tvguide.account.new.new-user-created"/>
<table class="propertypage-body">
    <tr>
    <td><bean:message key="tvguide.account.new.username"/></td>
    <td><bean:write name="accountPB" property="username"/></td>
    </tr>
    <tr>
    <td><bean:message key="tvguide.account.new.firstname"/></td>
    <td><bean:write name="accountPB" property="firstName"/></td>
    </tr>
    <tr>
    <td><bean:message key="tvguide.account.new.lastname"/></td>
    <td><bean:write name="accountPB" property="lastName"/></td>
    </tr>
</table>
