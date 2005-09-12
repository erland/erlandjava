<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<table class="transactionpage-header">
<logic:iterate name="brokersPB" id="broker">
    <tr><td nowrap>
    <a class="transactionpage-button" href="<html:rewrite page="/do/newaccountcontinouslyforvalue"/>?accountId=<bean:write name="selectAccountFB" property="accountId"/>&broker=<bean:write name="broker" property="id"/>"><bean:write name="broker" property="description"/> : <bean:message key="stock.account.add-continously-for-value"/></a>
    </td></tr>
</logic:iterate>
</table>
<table class="transactionpage-body">
<tr><td nowrap><p class="transactionpage-column-title"><bean:message key="stock.broker"/></p></td><td nowrap><p class="transactionpage-column-title"><bean:message key="stock.stock"/></p></td><td nowrap><p class="transactionpage-column-title"><bean:message key="stock.date"/></p></td><td nowrap><p class="transactionpage-column-title"><bean:message key="stock.value"/></p></td></tr>
<logic:iterate name="accountEntriesPB" id="entry">
    <tr class="transactionpage-list-row">
    <td class="transactionpage-list-entry" nowrap><bean:write name="entry" property="brokerDescription"/></td>
    <td class="transactionpage-list-entry" nowrap><bean:write name="entry" property="stockDescription"/></td>
    <td class="transactionpage-list-entry" nowrap><bean:write name="entry" property="purchaseDateDisplay"/></td>
    <td class="transactionpage-list-entry" nowrap><bean:write name="entry" property="value"/></td>
    <td class="transactionpage-list-entry" nowrap>
    <a class="transactionpage-button" href="<html:rewrite page="/do/removeaccountcontinously"/>?accountId=<bean:write name="selectAccountFB" property="accountId"/>&broker=<bean:write name="entry" property="broker"/>&stock=<bean:write name="entry" property="stock"/>&purchaseDateDisplay=<bean:write name="entry" property="purchaseDateDisplay"/>" onClick="return confirm('<bean:message key="stock.account.are-you-sure-remove"/>')"><bean:message key="stock.account.remove"/></a>
    </td>
    </tr>
</logic:iterate>
</table>
