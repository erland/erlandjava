<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="gallery.tasks.status.windowtitle"/></div>
<br>
<table class="propertypage-body">
<logic:iterate name="tasksPB" id="task">
    <tr>
    <td><bean:write name="task" property="user"/></td>
    <td><bean:message name="task" property="typeKey"/></td>
    <td><bean:write name="task" property="description"/></td>
    </tr>
</logic:iterate>
</table>
