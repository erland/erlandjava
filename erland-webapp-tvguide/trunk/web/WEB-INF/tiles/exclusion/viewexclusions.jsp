<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="tvguide.exclusions.view.windowtitle"/></div>
<table class="propertypage-body">
    <logic:iterate name="exclusionsPB" id="exclusion">
        <tr>
        <td nowrap><erland-common:beanlink name="exclusion" property="updateLink" style="propertypage-button"><bean:write name="exclusion" property="name"/></erland-common:beanlink></td><td><erland-common:beanlink name="exclusion" property="removeLink" style="propertypage-button" onClickMessageKey="tvguide.exclusion.button.delete.are-you-sure"><bean:message key="tvguide.exclusion.button.delete"/></erland-common:beanlink></td>
        </tr>
    </logic:iterate>
</table>
