<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="entrypage-body">
    <tr><td colspan="2">
    <erland-common:beanlink name="collectionPB" property="updateLink" style="entrypage-button"><bean:message key="datacollection.collection.button.edit"/></erland-common:beanlink>
    <erland-common:beanlink name="collectionPB" property="removeLink" style="entrypage-button" onClickMessageKey="datacollection.collection.button.remove.are-you-sure"><bean:message key="datacollection.collection.button.remove"/></erland-common:beanlink>
    <erland-common:beanlink name="collectionPB" property="newEntryLink" style="entrypage-button"><bean:message key="datacollection.collection.button.new-entry"/></erland-common:beanlink>
    </td></tr>
    <tr><td colspan="2" class="entrypage-title"><bean:write name="collectionPB" property="title"/></td></tr>
    <tr><td colspan="2" class="entrypage-description"><bean:write name="collectionPB" property="description"/></td></tr>
    <logic:iterate id="entry" name="collectionPB" property="entries">
        <tr>
            <td><erland-common:beanlink name="entry" property="viewLink" style="entrypage-button"><bean:write name="entry" property="title"/></erland-common:beanlink></td>
            <td><erland-common:beanlink name="entry" property="removeLink" style="entrypage-button" onClickMessageKey="datacollection.collection.button.remove-entry.are-you-sure"><bean:message key="datacollection.collection.button.remove-entry"/></erland-common:beanlink></td>
        </tr>
    </logic:iterate>
</table>
