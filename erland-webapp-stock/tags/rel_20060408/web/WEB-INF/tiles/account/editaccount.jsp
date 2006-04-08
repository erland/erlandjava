<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<table class="propertypage-body">
<html:form action="/editaccount" method="POST">
    <html:hidden property="accountId"/>
    <tr><td><bean:message key="stock.account.edit.name"/></td><td>
    <html:text property="name"/>
    </td></tr>
    <tr><td></td><td>
    <input type="submit" value="<bean:message key="stock.account.edit.save"/>">
    </td></tr>
</html:form>
</table>
