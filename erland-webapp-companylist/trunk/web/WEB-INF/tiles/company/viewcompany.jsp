<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<center>
<table>
    <tr>
    <td colspan="2" align="left"><p class="title"><bean:write name="companyFB" property="name"/></p></td>
    </tr>
    <tr>
    <td><bean:message key="companylist.edit.owner"/></td>
    <td><bean:write name="companyFB" property="owner"/></td>
    </tr>
    <tr>
    <td colspan="2" align="right"><a class="bold-link" href="<html:rewrite page="/"/>"><bean:message key="companylist.back"/></a></td>
    </tr>
</table>
</center>