<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<table>
<html:form action="/user/editcompany" method="POST">
    <html:hidden property="id"/>
    <tr>
    <td><bean:message key="companylist.edit.name"/></td>
    <td><html:text property="name"/></td>
    </tr>
    <tr>
    <td><bean:message key="companylist.edit.owner"/></td>
    <td><html:text property="owner"/></td>
    </tr>
    <tr>
    <td></td>
    <td><html:submit><bean:message key="companylist.edit.save"/></html:submit></td>
    </tr>
</html:form>
</table>
