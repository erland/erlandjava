<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table width="800" class="programpage-header">
        <logic:notEmpty name="programsPB" property="title">
            <tr valign="top" align="center" class="programpage-header-row">
                <td with="100%" colspan="3" ><div class="programpage-title"><bean:write name="programsPB" property="title"/></div></td>
            </tr>
        </logic:notEmpty>
        <tr valign="top" align="center" class="programpage-header-row">
            <td width="33%"><erland-common:beanlink name="programsPB" property="prevLink" style="programpage-button"><bean:write name="programsPB" property="prevDateDisplay"/></erland-common:beanlink></td>
            <td width="33%"><erland-common:beanlink name="programsPB" property="currentLink" style="programpage-button-selected"><bean:write name="programsPB" property="currentDateDisplay"/></erland-common:beanlink></td>
            <td width="33%"><erland-common:beanlink name="programsPB" property="nextLink" style="programpage-button"><bean:write name="programsPB" property="nextDateDisplay"/></erland-common:beanlink></td>
        </tr>
</table>
<table width="800" class="programpage-body">
    <logic:iterate id="program" name="programsPB" property="programs">
        <tr valign="top" align="left" class="programpage-program-row<logic:equal name="program" property="startedDisplay" value="true">-started</logic:equal><logic:equal name="program" property="startSameDayDisplay" value="false">-otherday</logic:equal>">
            <td><erland-common:beanimage name="program" property="channelLogo" height="25" /></td>
            <td nowrap><b><bean:write name="program" property="startDateDisplay"/><br><bean:write name="program" property="startTimeDisplay"/></b> - <bean:write name="program" property="stopTimeDisplay"/>
                <logic:notEqual name="program" property="reviewDisplay" value="0">
                    <br><img src="<%=request.getContextPath()%>/images/review<bean:write name="program" property="reviewDisplay"/>.gif" border="0"></img>
                </logic:notEqual>
            </td>
            <td width="100%">
                <erland-common:beanlink name="program" property="searchByNameLink" style="programpage-programtitle"><bean:write name="program" property="name"/></erland-common:beanlink>
                <logic:empty name="program" property="searchByNameLink"><font class="programpage-programtitle"><bean:write name="program" property="name"/></font></logic:empty>
                <erland-common:beanlink name="program" property="reviewLink" style="programpage-button" target="_blank"> <html:img page="/images/logo_imdb.gif" titleKey="tvguide.programs.more-information" border="0" /></erland-common:beanlink>
                <logic:notEmpty name="program" property="newSubscriptionLink">
                    <br>
                </logic:notEmpty>
                <erland-common:beanlink name="program" property="newSubscriptionLink" style="programpage-button"><bean:message key="tvguide.favorite.addsubscription"/></erland-common:beanlink>
                <erland-common:beanlink name="program" property="updateReviewLink" style="programpage-button">&nbsp;&nbsp;<bean:message key="tvguide.programs.update-review"/></erland-common:beanlink>
                <erland-common:beanlink name="program" property="newExclusionLink" style="programpage-button">&nbsp;&nbsp;<bean:message key="tvguide.programs.addexclusion"/></erland-common:beanlink>
                <br>
                <erland-common:expandhtml><bean:write name="program" property="description"/></erland-common:expandhtml>
            </td>
            <td><table class="programpage-coverinformation"><tr align="left" valign="top"><td><erland-common:beanlink name="program" property="reviewLink" style="programpage-button" target="_blank"><erland-common:beanimage name="program" property="coverLink" border="0" /></erland-common:beanlink>
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
