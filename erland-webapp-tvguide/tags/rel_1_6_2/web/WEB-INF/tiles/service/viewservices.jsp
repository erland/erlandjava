<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="tvguide.services.view.windowtitle"/></div>
<table class="propertypage-body">
    <logic:iterate name="servicesPB" id="service">
        <tr>
        <td nowrap><bean:write name="service" property="name"/></td>
        <td nowrap><bean:write name="service" property="serviceClass"/></td>
        <td nowrap><erland-common:beanlink name="service" property="updateLink" style="propertypage-button"><bean:message key="tvguide.service.button.edit"/></erland-common:beanlink></td>
        <td nowrap><erland-common:beanlink name="service" property="removeLink" style="propertypage-button" onClickMessageKey="tvguide.service.button.delete.are-you-sure"><bean:message key="tvguide.service.button.delete"/></erland-common:beanlink></td>
        <tr>
    </logic:iterate>
</table>
