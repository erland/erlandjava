<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="entrypage-body">
    <logic:iterate id="entry" name="entiresPB">
        <tr>
            <td><erland-common:beanlink name="entry" property="viewLink" style="entrypage-button"><bean:write name="entry" property="title"/></erland-common:beanlink></td>
            <td><erland-common:beanlink name="entry" property="removeLink" style="entrypage-button" onClickMessageKey="datacollection.entry.button.delete.are-you-sure"><bean:message key="datacollection.entry.button.delete"/></erland-common:beanlink></td>
        </tr>
    </logic:iterate>
</table>
