<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<table class="no-border">
<logic:iterate name="brokersPB" id="broker">
    <tr><td nowrap>
    <a class="bold-link" href="<html:rewrite page="/do/newaccountcontinouslyforvalue"/>?broker=<bean:write name="broker" property="id"/>"><bean:write name="broker" property="description"/> : <bean:message key="stock.account.add-continously-for-value"/></a>
    </td></tr>
</logic:iterate>
</table>
<table class="no-border">
<tr><td nowrap><p class="bold"><bean:message key="stock.broker"/></p></td><td nowrap><p class="bold"><bean:message key="stock.stock"/></p></td><td nowrap><p class="bold"><bean:message key="stock.date"/></p></td><td nowrap><p class="bold"><bean:message key="stock.value"/></p></td></tr>
<logic:iterate name="accountEntriesPB" id="entry">
    <tr>
    <td nowrap><bean:write name="entry" property="brokerDescription"/></td>
    <td nowrap><bean:write name="entry" property="stockDescription"/></td>
    <td nowrap><bean:write name="entry" property="purchaseDateDisplay"/></td>
    <td nowrap><bean:write name="entry" property="value"/></td>
    <td nowrap>
    <a class="bold-link" href="<html:rewrite page="/do/removeaccountcontinously"/>?broker=<bean:write name="entry" property="broker"/>&stock=<bean:write name="entry" property="stock"/>&purchaseDateDisplay=<bean:write name="entry" property="purchaseDateDisplay"/>" onClick="return confirm('<bean:message key="stock.account.are-you-sure-remove"/>')"><bean:message key="stock.account.remove"/></a>
    </td>
    </tr>
</logic:iterate>
</table>
