<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table border="0">
<logic:iterate name="accountsPB" id="account">
    <tr>
    <td>
    <a class="bold-link" href="<html:rewrite page="/do/guest/home"/>?user=<bean:write name="account" property="username"/>" title="<bean:write name="account" property="description"/>">
    <logic:notEmpty name="account" property="logo">
        <img src="<bean:write name="account" property="logo"/>" border="0" width="300"></img><br>
    </logic:notEmpty>
    <div align="center"><bean:write name="account" property="firstName"/> <bean:write name="account" property="lastName"/></div>
    </a>
    </td>
    </tr>
    <tr><td>&nbsp</td></tr>
</logic:iterate>
</table>
