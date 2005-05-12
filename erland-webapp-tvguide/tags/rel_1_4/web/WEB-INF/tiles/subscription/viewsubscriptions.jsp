<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="tvguide.subscriptions.view.windowtitle"/></div>
<table class="propertypage-body">
    <logic:iterate name="subscriptionsPB" id="subscription">
        <tr>
        <td nowrap><erland-common:beanlink name="subscription" property="viewLink" style="propertypage-button"><bean:write name="subscription" property="name"/></erland-common:beanlink></td>
        <tr>
    </logic:iterate>
</table>
