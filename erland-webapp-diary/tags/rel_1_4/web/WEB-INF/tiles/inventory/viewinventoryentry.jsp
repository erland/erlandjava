<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<table class="inventorypage-main-body">
<tr>
<td valign="top">
<tiles:insert page="/WEB-INF/tiles/inventory/viewinventoryentries.jsp"/>
</td>
<td valign="top">
<table class="inventorypage-entry-body">
    <bean:define id="speciesInfo" name="inventoryEntryPB" property="speciesInfo"/>
    <tr>
    <td colspan="2">
    <erland-common:beanlink name="inventoryEntryPB" property="updateLink" style="inventorypage-button">
        <bean:message key="diary.inventory.buttons.edit"/>
    </erland-common:beanlink>
    <erland-common:beanlink name="inventoryEntryPB" property="deleteLink" style="inventorypage-button" onClickMessageKey="diary.inventory.buttons.delete.are-you-sure">
        <bean:message key="diary.inventory.buttons.delete"/>
    </erland-common:beanlink>
    </td>
    </tr>
    <tr>
    <td colspan="2">
    <p class="inventorypage-entry-title">
    <bean:write name="inventoryEntryPB" property="name"/>
    <logic:iterate name="inventoryEntryPB" property="events" length="1" id="event">
        <bean:write name="inventoryEntryPB" property="currentSizeText"/>
        <bean:write name="event" property="descriptionText"/>
        <bean:write name="event" property="dateDisplay"/>
    </logic:iterate>
    </p>
    </td>
    </tr>
    <logic:notEmpty name="inventoryEntryPB" property="image">
        <tr><td>&nbsp</td></tr>
        <tr>
        <td colspan="2">
        <erland-common:beanlink name="inventoryEntryPB" property="largeImage" target="_blank">
            <erland-common:beanimage name="inventoryEntryPB" property="image" border="0" />
        </erland-common:beanlink>
        </td>
        </tr>
    </logic:notEmpty>
    <logic:notEmpty name="inventoryEntryPB" property="sexDescription">
        <tr><td colspan="2"><bean:message key="diary.inventory.view.sex"/> : <bean:write name="inventoryEntryPB" property="sexDescription"/></td></tr>
    </logic:notEmpty>
    <logic:notEmpty name="inventoryEntryPB" property="currentContainerDescription">
        <tr><td colspan="2"><bean:message key="diary.inventory.view.container"/>&nbsp;:&nbsp;
        <logic:notEmpty name="inventoryEntryPB" property="containerLink">
            <erland-common:beanlink name="inventoryEntryPB" property="containerLink" style="inventorypage-button">
                <bean:write name="inventoryEntryPB" property="currentContainerDescription"/>
            </erland-common:beanlink>
        </logic:notEmpty>
        <logic:empty name="inventoryEntryPB" property="containerLink">
            <bean:write name="inventoryEntryPB" property="currentContainerDescription"/>
        </logic:empty>
        </td></tr>
    </logic:notEmpty>
    <logic:notEmpty name="inventoryEntryPB" property="description">
        <tr><td>&nbsp</td></tr>
        <tr>
        <td colspan="2">
        <p class="inventorypage-entry-description"><erland-common:expandhtml><bean:write name="inventoryEntryPB" property="description"/></erland-common:expandhtml></p>
        </td>
        </tr>
    </logic:notEmpty>
    <tr><td>&nbsp</td></tr>
    <logic:iterate name="inventoryEntryPB" property="events" id="event">
        <tr>
        <td nowrap><p class="inventorypage-entry-event"><bean:write name="event" property="dateDisplay"/> <bean:write name="event" property="descriptionText"/> <bean:write name="event" property="sizeText"/></p></td>
        <td nowrap><erland-common:beanlink name="event" property="updateLink" style="inventorypage-button"><bean:message key="diary.inventory.event.buttons.edit"/></erland-common:beanlink> <erland-common:beanlink name="event" property="deleteLink" style="inventorypage-button" onClickMessageKey="diary.inventory.event.buttons.delete.are-you-sure"><bean:message key="diary.inventory.event.buttons.delete"/></erland-common:beanlink></td>
        <tr>
    </logic:iterate>
    <tr><td colspan="2" nowrap>
    <erland-common:beanlink name="inventoryEntryPB" property="newEventLink" style="inventorypage-button"><bean:message key="diary.inventory.event.buttons.new"/></erland-common:beanlink>
    </td></tr>

    <tr><td>&nbsp</td></tr>
    <logic:notEmpty name="inventoryEntryPB" property="species">
        <tr>
        <td colspan="2">
        <erland-common:beanlink style="inventorypage-button" name="speciesInfo" property="viewLink">
            <bean:message key="diary.inventory.species-description"/>
        </erland-common:beanlink>
        </td>
        </tr>
    </logic:notEmpty>
    <logic:notEmpty name="inventoryEntryPB" property="gallery">
        <tr>
        <td colspan="2">
        <erland-common:beanlink style="inventorypage-button" name="inventoryEntryPB" property="galleryLink">
            <bean:message key="diary.inventory.gallery"/>
        </erland-common:beanlink>
        </td>
        </tr>
    </logic:notEmpty>
    <logic:notEmpty name="inventoryEntryPB" property="link">
        <tr>
        <td colspan="2">
        <logic:empty name="inventoryEntryPB" property="linkSource">
            <erland-common:beanlink style="inventorypage-button" name="inventoryEntryPB" property="link" target="_blank">
                <bean:message key="diary.inventory.more-information"/>
            </erland-common:beanlink>
        </logic:empty>
        <logic:notEmpty name="inventoryEntryPB" property="linkSource">
            <erland-common:beanlink style="inventorypage-button" name="inventoryEntryPB" property="link" target="_blank">
                <bean:message key="diary.inventory.more-information-with-source"/> <bean:write name="inventoryEntryPB" property="linkSource"/>
            </erland-common:beanlink>
        </logic:notEmpty>
        </td>
        </tr>
    </logic:notEmpty>
</table>
</td></tr>
</table>
