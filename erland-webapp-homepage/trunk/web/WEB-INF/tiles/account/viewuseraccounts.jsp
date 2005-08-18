<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="propertypage-body">
<logic:iterate name="accountsPB" id="account">
    <tr>
    <td>
    <a class="propertypage-button" href="<html:rewrite page="/do/user/viewotheruseraccount"/>?username=<bean:write name="account" property="username"/>" title="<bean:write name="account" property="description"/>">
    <logic:notEmpty name="account" property="logo">
        <erland-common:beanimage name="account" property="logo" border="0" width="300"/>"<br>
    </logic:notEmpty>
    <div align="center"><bean:write name="account" property="firstName"/> <bean:write name="account" property="lastName"/></div>
    </a>
    </td>
    </tr>
    <tr><td>&nbsp</td></tr>
</logic:iterate>
</table>
