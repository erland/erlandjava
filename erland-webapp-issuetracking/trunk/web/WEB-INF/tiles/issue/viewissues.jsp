<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="propertypage-body">
<logic:iterate name="issuesPB" id="issue">
    <tr>
    <td>
    <bean:write name="issue" property="id" />
    </td>
    <td>
    <bean:message name="issue" property="stateTextKey" />
    </td>
    <td>
    <bean:write name="issue" property="assignedTo" />
    </td>
    <td nowrap>
    <erland-common:beanlink name="issue" property="viewLink" style="propertypage-button"><bean:write name="issue" property="title" /></erland-common:beanlink>
    </td>
    </tr>
</logic:iterate>
<table>
