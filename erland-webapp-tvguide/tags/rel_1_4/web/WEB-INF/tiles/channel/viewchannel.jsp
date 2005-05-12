<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table width="600" class="channelpage-header" width="600">
        <tr valign="top" align="center" class="programpage-header-row">
            <td width="33%"><erland-common:beanlink name="channelPB" property="prevLink" style="channelpage-button"><bean:write name="channelPB" property="prevDateDisplay"/></erland-common:beanlink></td>
            <td width="33%"><erland-common:beanlink name="channelPB" property="currentLink" style="channelpage-button-selected"><bean:write name="channelPB" property="currentDateDisplay"/></erland-common:beanlink></td>
            <td width="33%"><erland-common:beanlink name="channelPB" property="nextLink" style="channelpage-button"><bean:write name="channelPB" property="nextDateDisplay"/></erland-common:beanlink></td>
        </tr>
</table>

<table width="600" class="channelpage-body" width="600">
    <tr><td colspan="2"><erland-common:beanlink name="channelPB" property="updateLink" style="channelpage-button"><bean:message key="tvguide.channel.button.edit"/></erland-common:beanlink>
    <erland-common:beanlink name="channelPB" property="removeLink" style="channelpage-button" onClickMessageKey="tvguide.channel.button.delete.are-you-sure"><bean:message key="tvguide.channel.button.delete"/></erland-common:beanlink></td></tr>
    <tr><td colspan="2">
        <table>
            <tr valign="top" align="left">
            <logic:notEmpty name="channelPB" property="logo">
                <td><erland-common:beanimage style="channelpage-logo" name="channelPB" property="logo"/></td>
            </logic:notEmpty>
            <td width="100%" valig="top" align="left">
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
        <tr valign="top" align="left" class="channelpage-program-row<logic:equal name="program" property="startedDisplay" value="true">-started</logic:equal>">
            <td><b><bean:write name="program" property="startTimeDisplay"/></b>
                <logic:notEqual name="program" property="reviewDisplay" value="0">
                    <br><img src="<%=request.getContextPath()%>/images/review<bean:write name="program" property="reviewDisplay"/>.gif" border="0"></img>
                </logic:notEqual>
            </td>
            <td width="100%">
                <b><bean:write name="program" property="name"/></b>
                &nbsp;&nbsp;&nbsp;
                <erland-common:beanlink name="program" property="reviewLink" style="channelpage-button" target="_blank"><bean:message key="tvguide.programs.more-information"/></erland-common:beanlink>
                &nbsp;&nbsp;&nbsp;
                <erland-common:beanlink name="program" property="newSubscriptionLink" style="channelpage-button"><bean:message key="tvguide.channel.addsubscription"/></erland-common:beanlink><br>
                <erland-common:expandhtml><bean:write name="program" property="description"/></erland-common:expandhtml>
            </td>
        </tr>
    </logic:iterate>
</table>
