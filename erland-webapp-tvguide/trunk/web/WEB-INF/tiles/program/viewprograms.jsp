<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table width="600" class="programpage-body">
    <logic:iterate id="program" name="programsPB">
        <tr valign="top" align="left" class="programpage-program-row">
            <td><erland-common:beanimage name="program" property="channelLogo" height="25" /></td>
            <td><b><bean:write name="program" property="startTimeDisplay"/></b></td>
            <td>
                <b><bean:write name="program" property="name"/></b><br>
                <erland-common:expandhtml><bean:write name="program" property="description"/></erland-common:expandhtml>
            </td>
        </tr>
    </logic:iterate>
</table>
