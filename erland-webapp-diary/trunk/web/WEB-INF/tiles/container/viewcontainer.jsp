<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<table class="no-border">
    <tr>
    <td colspan="2">
    <erland-common:beanlink name="containerPB" property="updateLink" style="bold-link">
        <bean:message key="diary.container.buttons.edit"/>
    </erland-common:beanlink>
    <erland-common:beanlink name="containerPB" property="deleteLink" style="bold-link" onClickMessageKey="diary.container.buttons.delete.are-you-sure">
        <bean:message key="diary.container.buttons.delete"/>
    </erland-common:beanlink>
    </td>
    </tr>
    <tr>
    <td colspan="2">
    <p class="title"><bean:write name="containerPB" property="name"/></p>
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
        <p class="normal"><erland-common:expandhtml><bean:write name="containerPB" property="description"/></erland-common:expandhtml></p>
        </td>
        </tr>
    </logic:notEmpty>

    <tr><td>&nbsp</td></tr>
    <tr><td colspan="2"><div class="bold"><bean:message key="diary.container.view.inventory"/></div></td></tr>
    <tr><td colspan="2">
        <table class="no-border">
        <logic:iterate name="inventorySummaryEntriesPB" id="entry">
            <tr><td><bean:write name="entry" property="numberOf"/> st</td><td><bean:write name="entry" property="name"/></td></tr>
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
        <erland-common:beanlink style="bold-link" name="containerPB" property="galleryLink">
            <bean:message key="diary.container.gallery"/>
        </erland-common:beanlink>
        </td>
        </tr>
    </logic:notEmpty>
    <logic:notEmpty name="containerPB" property="link">
        <tr>
        <td colspan="2">
        <erland-common:beanlink style="bold-link" name="containerPB" property="link" target="_blank">
            <bean:message key="diary.container.more-information"/>
        </erland-common:beanlink>
        </td>
        </tr>
    </logic:notEmpty>
</table>
