<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table width="600" class="channelpage-body">
    <tr><td colspan="2"><erland-common:beanlink name="channelPB" property="updateLink" style="channelpage-button"><bean:message key="tvguide.channel.button.edit"/></erland-common:beanlink>
    <erland-common:beanlink name="channelPB" property="removeLink" style="channelpage-button" onClickMessageKey="tvguide.channel.button.delete.are-you-sure"><bean:message key="tvguide.channel.button.delete"/></erland-common:beanlink></td></tr>
    <tr><td colspan="2">
        <table>
            <tr valign="top" align="left">
            <logic:notEmpty name="channelPB" property="logo">
                <td><erland-common:beanimage style="channelpage-logo" name="channelPB" property="logo"/></td>
            </logic:notEmpty>
            <td valig="top" align="left">
                <logic:notEmpty name="channelPB" property="name">
                    <div class="channelpage-title"><bean:write name="channelPB" property="name"/></div>
                </logic:notEmpty>
                <logic:notEmpty name="channelPB" property="description">
                    <div class="channelpage-description"><erland-common:expandhtml><bean:write name="channelPB" property="description"/></erland-common:expandhtml></div>
                </logic:notEmpty>
            </td>
            </tr>
        </table>
    </td></tr>
    <logic:iterate id="program" name="channelPB" property="programs">
        <tr valign="top" align="left" class="channelpage-program-row">
            <td><b><bean:write name="program" property="startTimeDisplay"/></b></td>
            <td>
                <b><bean:write name="program" property="name"/></b><br>
                <erland-common:expandhtml><bean:write name="program" property="description"/></erland-common:expandhtml>
            </td>
        </tr>
    </logic:iterate>
</table>
