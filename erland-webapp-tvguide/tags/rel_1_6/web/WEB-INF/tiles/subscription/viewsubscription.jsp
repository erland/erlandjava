<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table width="800" class="subscriptionpage-body">
    <tr><td colspan="3"><erland-common:beanlink name="subscriptionPB" property="updateLink" style="subscriptionpage-button"><bean:message key="tvguide.subscription.button.edit"/></erland-common:beanlink>
    <erland-common:beanlink name="subscriptionPB" property="removeLink" style="subscriptionpage-button" onClickMessageKey="tvguide.subscription.button.delete.are-you-sure"><bean:message key="tvguide.subscription.button.delete"/></erland-common:beanlink>
    <erland-common:beanlink name="subscriptionPB" property="removeAndExcludeLink" style="subscriptionpage-button" onClickMessageKey="tvguide.subscription.button.deleteandexclude.are-you-sure"><bean:message key="tvguide.subscription.button.deleteandexclude"/></erland-common:beanlink></td></tr>
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
                <erland-common:beanlink name="program" property="searchByNameLink" style="subscriptionpage-programtitle"><bean:write name="program" property="name"/></erland-common:beanlink>
                <erland-common:beanlink name="program" property="reviewLink" style="subscriptionpage-button" target="_blank"> <html:img page="/images/logo_imdb.gif" titleKey="tvguide.programs.more-information" border="0" /></erland-common:beanlink>
                &nbsp;&nbsp;&nbsp;
                <erland-common:beanlink name="program" property="updateReviewLink" style="subscriptionpage-button"><bean:message key="tvguide.programs.update-review"/></erland-common:beanlink>
                <erland-common:beanlink name="program" property="newExclusionLink" style="subscriptionpage-button">&nbsp;&nbsp;<bean:message key="tvguide.programs.addexclusion"/></erland-common:beanlink>
                <br>
                <erland-common:expandhtml><bean:write name="program" property="description"/></erland-common:expandhtml>
            </td>
            <td><table class="subscriptionpage-coverinformation"><tr align="left" valign="top"><td><erland-common:beanlink name="program" property="reviewLink" style="programpage-button" target="_blank"><erland-common:beanimage name="program" property="coverLink" border="0" /></erland-common:beanlink>
            <td nowrap>
            <logic:notEmpty name="program" property="category"><b><bean:write name="program" property="category"/></b><br></logic:notEmpty>
            <logic:iterate id="credit" name="program" property="credits" length="5">
                <erland-common:beanlink name="credit" property="link" style="programpage-button" target="_blank"><html:img page="/images/logo_imdb.gif" titleKey="tvguide.programs.more-information" border="0" /></erland-common:beanlink>
                <erland-common:beanlink name="credit" property="searchByCreditLink" style="programpage-button"><bean:write name="credit" property="name"/></erland-common:beanlink>
                <logic:equal name="credit" property="category" value="director"> (<bean:message key="tvguide.programs.credit.director"/>)</logic:equal>
                <br>
            </logic:iterate>
            </td></tr></table></td>
        </tr>
    </logic:iterate>
</table>
