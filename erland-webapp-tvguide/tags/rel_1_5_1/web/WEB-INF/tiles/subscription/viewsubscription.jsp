<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table width="600" class="subscriptionpage-body" width="600">
    <tr><td colspan="3"><erland-common:beanlink name="subscriptionPB" property="updateLink" style="subscriptionpage-button"><bean:message key="tvguide.subscription.button.edit"/></erland-common:beanlink>
    <erland-common:beanlink name="subscriptionPB" property="removeLink" style="subscriptionpage-button" onClickMessageKey="tvguide.subscription.button.delete.are-you-sure"><bean:message key="tvguide.subscription.button.delete"/></erland-common:beanlink></td></tr>
    <tr><td colspan="3">
        <div class="subscriptionpage-title"><bean:write name="subscriptionPB" property="name"/></div>
    </td></tr>
    <logic:iterate id="program" name="subscriptionPB" property="programs">
        <tr valign="top" align="left" class="subscriptionpage-program-row<logic:equal name="program" property="startedDisplay" value="true">-started</logic:equal><logic:equal name="program" property="startSameDayDisplay" value="false">-otherday</logic:equal>">
            <td><erland-common:beanimage name="program" property="channelLogo" height="25" /></td>
            <td nowrap><b><bean:write name="program" property="startDateDisplay"/><br><bean:write name="program" property="startTimeDisplay"/></b> - <bean:write name="program" property="stopTimeDisplay"/>
                <logic:notEqual name="program" property="reviewDisplay" value="0">
                    <br><img src="<%=request.getContextPath()%>/images/review<bean:write name="program" property="reviewDisplay"/>.gif" border="0"></img>
                </logic:notEqual>
            </td>
            <td width="100%">
                <b><bean:write name="program" property="name"/></b><br>
                <erland-common:expandhtml><bean:write name="program" property="description"/></erland-common:expandhtml>
            </td>
        </tr>
    </logic:iterate>
</table>
