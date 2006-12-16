<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<table class="propertypage-body">
<html:form action="/editaccountpurchaseoncenumberforvalue" method="POST">
    <html:hidden property="accountId"/>
    <html:hidden name="brokerPB" property="broker"/>
    <tr><td><bean:message key="stock.account.edit.stock"/></td><td>
    <html:select property="stock">
        <html:optionsCollection name="brokerPB" property="stocks" label="description" value="stock"/>
    </html:select>
    </td></tr>
    <tr><td><bean:message key="stock.account.edit.date"/></td><td>
    <html:text property="purchaseDateDisplay"/>
    </td></tr>
    <tr><td><bean:message key="stock.account.edit.value"/></td><td>
    <html:text property="valueDisplay"/>
    </td></tr>
    <tr><td><bean:message key="stock.account.edit.number"/></td><td>
    <html:text property="numberDisplay"/>
    </td></tr>
    <tr><td></td><td>
    <input type="submit" value="<bean:message key="stock.account.edit.save"/>">
    </td></tr>
</html:form>
</table>
