<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<table>
<logic:iterate name="companiesPB" id="company" >
    <tr><td><a class="bold-link" href="<html:rewrite page="/do/user/viewcompany"/>?id=<bean:write name="company" property="id"/>"><bean:write name="company" property="name"/></a></td></tr>
    <tr><td><bean:write name="company" property="owner"/></td></tr>
    <tr><td>&nbsp</td></tr>
</logic:iterate>
</table>
