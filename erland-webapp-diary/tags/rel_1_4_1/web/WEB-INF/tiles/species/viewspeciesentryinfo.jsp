<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<table class="speciespage-entry-body">
    <tr>
    <td colspan="2">
    <erland-common:beanlink name="speciesEntryPB" property="updateLink" style="speciespage-button">
        <bean:message key="diary.species.buttons.edit"/>
    </erland-common:beanlink>
    <erland-common:beanlink name="speciesEntryPB" property="deleteLink" style="speciespage-button" onClickMessageKey="diary.species.buttons.delete.are-you-sure">
        <bean:message key="diary.species.buttons.delete"/>
    </erland-common:beanlink>
    </td>
    </tr>
    <tr>
    <td colspan="2">
    <p class="speciespage-entry-title">
    <bean:write name="speciesEntryPB" property="name"/>
    </p>
    </td>
    </tr>
    <logic:notEmpty name="speciesEntryPB" property="latinName">
        <tr>
        <td colspan="2">
        <p class="speciespage-entry-title">
        (<bean:write name="speciesEntryPB" property="latinName"/>)
        </p>
        </td>
        </tr>
    </logic:notEmpty>
    <logic:notEmpty name="speciesEntryPB" property="image">
        <tr><td>&nbsp</td></tr>
        <tr>
        <td colspan="2">
        <erland-common:beanlink name="speciesEntryPB" property="largeImage" target="_blank">
            <erland-common:beanimage name="speciesEntryPB" property="image" border="0" />
        </erland-common:beanlink>
        </td>
        </tr>
    </logic:notEmpty>
    <logic:notEmpty name="speciesEntryPB" property="description">
        <tr><td>&nbsp</td></tr>
        <tr>
        <td colspan="2">
        <p class="speciespage-entry-description"><erland-common:expandhtml><bean:write name="speciesEntryPB" property="description"/></erland-common:expandhtml></p>
        </td>
        </tr>
    </logic:notEmpty>
    <tr><td>&nbsp</td></tr>

    <logic:notEmpty name="speciesEntryPB" property="gallery">
        <tr><td>&nbsp</td></tr>
        <tr>
        <td colspan="2">
        <erland-common:beanlink style="speciespage-button" name="speciesEntryPB" property="galleryLink">
            <bean:message key="diary.species.gallery"/>
        </erland-common:beanlink>
        </td>
        </tr>
    </logic:notEmpty>
    <logic:notEmpty name="speciesEntryPB" property="link">
        <tr>
        <td colspan="2">
        <logic:empty name="speciesEntryPB" property="linkSource">
            <erland-common:beanlink style="speciespage-button" name="speciesEntryPB" property="link" target="_blank">
                <bean:message key="diary.species.more-information"/>
            </erland-common:beanlink>
        </logic:empty>
        <logic:notEmpty name="speciesEntryPB" property="linkSource">
            <erland-common:beanlink style="speciespage-button" name="speciesEntryPB" property="link" target="_blank">
                <bean:message key="diary.species.more-information-with-source"/> <bean:write name="speciesEntryPB" property="linkSource"/>
            </erland-common:beanlink>
        </logic:notEmpty>
        </td>
        </tr>
    </logic:notEmpty>
</table>
