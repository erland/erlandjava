<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table border="0">
    <logic:iterate name="purchaseEntriesPB" id="entry">
        <tr>
        <td nowrap><p class="normal"><bean:write name="entry" property="dateDisplay"/></p></td>
        <td nowrap><p class="normal"><bean:write name="entry" property="description"/></p></td>
        <td nowrap><p class="normal">&nbsp&nbsp&nbsp</p></td>
        <td nowrap><p class="normal"><bean:write name="entry" property="priceDisplay"/> kr</p></td>
        </tr>
        <tr>
        <td></td>
        <td nowrap><p class="normal"><bean:write name="entry" property="store"/></p></td>
        <td nowrap><erland-common:beanlink name="entry" property="updateLink" style="bold-link"><bean:message key="diary.purchase.button.edit"/></erland-common:beanlink></td>
        <td nowrap><erland-common:beanlink name="entry" property="deleteLink" style="bold-link" onClickMessageKey="diary.purchase.button.delete.are-you-sure"><bean:message key="diary.purchase.button.delete"/></erland-common:beanlink></td>
        <tr>
    </logic:iterate>
</table>
