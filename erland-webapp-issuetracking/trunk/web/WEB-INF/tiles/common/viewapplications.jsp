<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="propertypage-body">
<logic:iterate name="applicationsPB" id="appl">
    <tr>
    <td>
    <a class="propertypage-button" href="<html:rewrite page="/do/guest/home"/>?application=<bean:write name="appl" property="name"/>">
    <logic:notEmpty name="appl" property="logo">
        <img src="<bean:write name="appl" property="logo"/>" border="0" width="300"></img><br>
    </logic:notEmpty>
    <div align="center"><bean:write name="appl" property="title"/></div>
    </a>
    </td>
    </tr>
    <tr><td>&nbsp</td></tr>
</logic:iterate>
</table>
