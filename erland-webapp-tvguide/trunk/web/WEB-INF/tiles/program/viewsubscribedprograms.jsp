<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table width="600" class="programpage-header" width="600">
        <tr valign="top" align="center" class="programpage-header-row">
            <td width="33%"><erland-common:beanlink name="programsPB" property="prevLink" style="programpage-button"><bean:write name="programsPB" property="prevDateDisplay"/></erland-common:beanlink></td>
            <td width="33%"><erland-common:beanlink name="programsPB" property="currentLink" style="programpage-button-selected"><bean:write name="programsPB" property="currentDateDisplay"/></erland-common:beanlink></td>
            <td width="33%"><erland-common:beanlink name="programsPB" property="nextLink" style="programpage-button"><bean:write name="programsPB" property="nextDateDisplay"/></erland-common:beanlink></td>
        </tr>
</table>
<table width="600" class="programpage-body" width="600">
    <logic:iterate id="program" name="programsPB" property="programs">
        <tr valign="top" align="left" class="programpage-program-row<logic:equal name="program" property="startedDisplay" value="true">-started</logic:equal><logic:equal name="program" property="startSameDayDisplay" value="false">-otherday</logic:equal>">
            <td><erland-common:beanimage name="program" property="channelLogo" height="25" /></td>
            <td nowrap><b><bean:write name="program" property="startDateDisplay"/><br><bean:write name="program" property="startTimeDisplay"/></b> - <bean:write name="program" property="stopTimeDisplay"/>
                <logic:notEqual name="program" property="reviewDisplay" value="0">
                    <br><img src="<%=request.getContextPath()%>/images/review<bean:write name="program" property="reviewDisplay"/>.gif" border="0"></img>
                </logic:notEqual>
            </td>
            <td width="100%">
                <b><bean:write name="program" property="name"/></b>
                <logic:notEmpty name="program" property="viewSubscriptionLink">
                    <br>
                </logic:notEmpty>
                <logic:empty name="program" property="viewSubscriptionLink">
                    <logic:notEmpty name="program" property="reviewLink"><br></logic:notEmpty>
                </logic:empty>
                <erland-common:beanlink name="program" property="reviewLink" style="programpage-button" target="_blank"><bean:message key="tvguide.programs.more-information"/></erland-common:beanlink>
                <erland-common:beanlink name="program" property="viewSubscriptionLink" style="programpage-button">&nbsp;&nbsp;<bean:message key="tvguide.programs.viewsubscription"/></erland-common:beanlink>
                <logic:notEmpty name="program" property="viewSubscriptionLink">
                    <br>
                </logic:notEmpty>
                <logic:empty name="program" property="viewSubscriptionLink">
                    <logic:notEmpty name="program" property="reviewLink"><br></logic:notEmpty>
                </logic:empty>
                <erland-common:expandhtml><bean:write name="program" property="description"/></erland-common:expandhtml>
            </td>
        </tr>
    </logic:iterate>
</table>
