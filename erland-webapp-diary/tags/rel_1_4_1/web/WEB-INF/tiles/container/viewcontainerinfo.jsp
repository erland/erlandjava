<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<table class="containerpage-body">
    <tr>
    <td colspan="2">
    <erland-common:beanlink name="containerPB" property="updateLink" style="containerpage-button">
        <bean:message key="diary.container.buttons.edit"/>
    </erland-common:beanlink>
    <erland-common:beanlink name="containerPB" property="deleteLink" style="containerpage-button" onClickMessageKey="diary.container.buttons.delete.are-you-sure">
        <bean:message key="diary.container.buttons.delete"/>
    </erland-common:beanlink>
    </td>
    </tr>
    <tr>
    <td colspan="2">
    <p class="containerpage-title"><bean:write name="containerPB" property="name"/></p>
    </td>
    </tr>
    <logic:notEmpty name="containerPB" property="image">
        <tr><td>&nbsp</td></tr>
        <tr>
        <td colspan="2">
        <logic:notEmpty name="containerPB" property="largeImage">
            <erland-common:beanlink name="containerPB" property="largeImage" target="_blank">
                <erland-common:beanimage name="containerPB" property="image" border="0" />
            </erland-common:beanlink>
        </logic:notEmpty>
        <logic:empty name="containerPB" property="largeImage">
            <erland-common:beanimage name="containerPB" property="image" border="0" />
        </logic:empty>
        </td>
        </tr>
    </logic:notEmpty>
    <tr><td><bean:message key="diary.container.view.model"/> : </td><td><bean:write name="containerPB" property="model"/></td></tr>
    <tr><td><bean:message key="diary.container.view.volume"/> : </td><td><bean:write name="containerPB" property="volumeDisplay"/> <bean:message key="diary.container.view.litre"/></td></tr>
    <tr><td><bean:message key="diary.container.view.size"/> : </td><td><bean:write name="containerPB" property="lengthDisplay"/> x <bean:write name="containerPB" property="heightDisplay"/> x <bean:write name="containerPB" property="widthDisplay"/></td></tr>

    <logic:notEmpty name="containerPB" property="description">
        <tr><td>&nbsp</td></tr>
        <tr>
        <td colspan="2">
        <p class="containerpage-description"><erland-common:expandhtml><bean:write name="containerPB" property="description"/></erland-common:expandhtml></p>
        </td>
        </tr>
    </logic:notEmpty>

    <tr><td>&nbsp</td></tr>
    <tr><td colspan="2"><div class="containerpage-inventory-title"><bean:message key="diary.container.view.inventory"/></div></td></tr>
    <tr><td colspan="2">
        <table class="containerpage-inventory-body">
        <logic:iterate name="inventorySummaryEntriesPB" id="entry">
            <tr><td><bean:write name="entry" property="numberOf"/> st</td><td><logic:empty name="entry" property="viewLink"><bean:write name="entry" property="name"/></logic:empty><erland-common:beanlink name="entry" property="viewLink" style="bold-link"><bean:write name="entry" property="name"/></erland-common:beanlink></td></tr>
        </logic:iterate>
        </table>
        <erland-common:beanlink name="containerPB" property="inventoryLink" style="bold-link">
            <bean:message key="diary.container.view.whole-inventory"/>
        </erland-common:beanlink>
    </td></tr>

    <logic:notEmpty name="containerPB" property="gallery">
        <tr><td>&nbsp</td></tr>
        <tr>
        <td colspan="2">
        <erland-common:beanlink style="containerpage-button" name="containerPB" property="galleryLink">
            <bean:message key="diary.container.gallery"/>
        </erland-common:beanlink>
        </td>
        </tr>
    </logic:notEmpty>
    <logic:notEmpty name="containerPB" property="link">
        <tr>
        <td colspan="2">
        <logic:empty name="containerPB" property="linkSource">
            <erland-common:beanlink style="containerpage-button" name="containerPB" property="link" target="_blank">
                <bean:message key="diary.container.more-information"/>
            </erland-common:beanlink>
        </logic:empty>
        <logic:notEmpty name="containerPB" property="linkSource">
            <erland-common:beanlink style="containerpage-button" name="containerPB" property="link" target="_blank">
                <bean:message key="diary.container.more-information-with-source"/> <bean:write name="containerPB" property="linkSource"/>
            </erland-common:beanlink>
        </logic:notEmpty>
        </td>
        </tr>
    </logic:notEmpty>
</table>
