<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="speciespage-body">
    <logic:iterate name="speciesPB" id="entry">
        <tr>
        <td><erland-common:beanlink name="entry" property="viewLink" style="speciespage-button"><bean:write name="entry" property="name"/></erland-common:beanlink></td>
        </tr>
    </logic:iterate>
</table>
