<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="inventorypage-body">
    <logic:iterate name="inventoryEntriesPB" id="entry">
        <tr>
        <td nowrap><erland-common:beanlink name="entry" property="viewLink" style="inventorypage-button"><bean:write name="entry" property="name"/></erland-common:beanlink></td>
        <td><p class="inventorypage-list-field"><bean:write name="entry" property="sexDescription"/></p></td>
        <logic:iterate name="entry" property="events" id="event" length="1">
            <td><p class="inventorypage-list-field"><bean:write name="event" property="sizeText"/></p></td>
            <td><p class="inventorypage-list-field"><bean:write name="event" property="descriptionText"/></p></td>
            <td><p class="inventorypage-list-field"><bean:write name="event" property="dateDisplay"/></p></td>
            <td><erland-common:beanlink name="event" property="updateLink" style="inventorypage-button"><bean:message key="diary.inventory.buttons.edit"/></erland-common:beanlink></td>
            <td><erland-common:beanlink name="event" property="deleteLink" style="inventorypage-button" onClickMessageKey="diary.inventory.buttons.delete.are-you-sure"><bean:message key="diary.inventory.buttons.delete"/></erland-common:beanlink></td>
        </logic:iterate>
        </tr>
        <logic:iterate offset="1" name="entry" property="events" id="event">
            <tr>
            <td><p class="inventorypage-list-field">&nbsp</p></td>
            <td><p class="inventorypage-list-field">&nbsp&nbsp&nbsp</p></td>
            <td><p class="inventorypage-list-field"><bean:write name="event" property="sizeText"/></p></td>
            <td><p class="inventorypage-list-field"><bean:write name="event" property="descriptionText"/></p></td>
            <td><p class="inventorypage-list-field"><bean:write name="event" property="dateDisplay"/></p></td>
            <td><erland-common:beanlink name="event" property="updateLink" style="inventorypage-button"><bean:message key="diary.inventory.buttons.edit"/></erland-common:beanlink></td>
            <td><erland-common:beanlink name="event" property="deleteLink" style="inventorypage-button" onClickMessageKey="diary.inventory.buttons.delete.are-you-sure"><bean:message key="diary.inventory.buttons.delete"/></erland-common:beanlink></td>
            </tr>
        </logic:iterate>
        <tr>
        <td></td>
        <td><erland-common:beanlink name="entry" property="updateLink" style="inventorypage-button"><bean:message key="diary.inventory.buttons.edit"/></erland-common:beanlink></td>
        <td><erland-common:beanlink name="entry" property="deleteLink" style="inventorypage-button" onClickMessageKey="diary.inventory.buttons.delete.are-you-sure"><bean:message key="diary.inventory.buttons.delete"/></erland-common:beanlink></td>
        <td></td>
        <td colspan="3"><erland-common:beanlink name="entry" property="newEventLink" style="inventorypage-button"><bean:message key="diary.inventory.buttons.new"/></erland-common:beanlink></td>
        </tr>
    </logic:iterate>
</table>
