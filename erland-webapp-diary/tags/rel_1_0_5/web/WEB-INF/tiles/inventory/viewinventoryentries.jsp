<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table border="0">
    <logic:iterate name="inventoryEntriesPB" id="entry">
        <tr>
        <td><erland-common:beanlink name="entry" property="viewLink" style="bold-link"><bean:write name="entry" property="name"/></erland-common:beanlink></td>
        <td><p class="normal">&nbsp&nbsp&nbsp</p></td>
        <logic:iterate name="entry" property="events" id="event" length="1">
            <td><p class="normal"><bean:write name="event" property="sizeText"/></p></td>
            <td><p class="normal"><bean:write name="event" property="descriptionText"/></p></td>
            <td><p class="normal"><bean:write name="event" property="dateDisplay"/></p></td>
            <td><erland-common:beanlink name="event" property="updateLink" style="bold-link"><bean:message key="diary.inventory.buttons.edit"/></erland-common:beanlink></td>
            <td><erland-common:beanlink name="event" property="deleteLink" style="bold-link" onClickMessageKey="diary.inventory.buttons.delete.are-you-sure"><bean:message key="diary.inventory.buttons.delete"/></erland-common:beanlink></td>
        </logic:iterate>
        </tr>
        <logic:iterate offset="1" name="entry" property="events" id="event">
            <tr>
            <td><p class="normal">&nbsp</p></td>
            <td><p class="normal">&nbsp&nbsp&nbsp</p></td>
            <td><p class="normal"><bean:write name="event" property="sizeText"/></p></td>
            <td><p class="normal"><bean:write name="event" property="descriptionText"/></p></td>
            <td><p class="normal"><bean:write name="event" property="dateDisplay"/></p></td>
            <td><erland-common:beanlink name="event" property="updateLink" style="bold-link"><bean:message key="diary.inventory.buttons.edit"/></erland-common:beanlink></td>
            <td><erland-common:beanlink name="event" property="deleteLink" style="bold-link" onClickMessageKey="diary.inventory.buttons.delete.are-you-sure"><bean:message key="diary.inventory.buttons.delete"/></erland-common:beanlink></td>
            </tr>
        </logic:iterate>
        <tr>
        <td></td>
        <td><erland-common:beanlink name="entry" property="updateLink" style="bold-link"><bean:message key="diary.inventory.buttons.edit"/></erland-common:beanlink></td>
        <td><erland-common:beanlink name="entry" property="deleteLink" style="bold-link" onClickMessageKey="diary.inventory.buttons.delete.are-you-sure"><bean:message key="diary.inventory.buttons.delete"/></erland-common:beanlink></td>
        <td></td>
        <td colspan="3"><erland-common:beanlink name="entry" property="newEventLink" style="bold-link"><bean:message key="diary.inventory.buttons.new"/></erland-common:beanlink></td>
        </tr>
    </logic:iterate>
</table>
