<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="no-border">
<erland-common:beanlink style="bold-link" name="issuePB" property="updateLink"><bean:message key="issuetracking.issue.update"/></erland-common:beanlink>
<tr><td colspan="2">
<p class="bold"><bean:write name="issuePB" property="title" /></p>
</td></tr>
<tr><td><bean:message key="issuetracking.issue.edit.id"/></td><td>
<bean:write name="issuePB" property="id" />
</td></tr>
<tr><td><bean:message key="issuetracking.issue.edit.application"/></td><td>
<bean:write name="issuePB" property="applicationTitle" />
</td></tr>
<tr><td><bean:message key="issuetracking.issue.edit.version"/></td><td>
<bean:write name="issuePB" property="version" />
</td></tr>
<tr><td><bean:message key="issuetracking.issue.edit.description"/></td><td>
<erland-common:expandhtml><bean:write name="issuePB" property="description" /></erland-common:expandhtml>
</td></tr>
<logic:notEmpty name="issuePB" property="username">
    <tr><td><bean:message key="issuetracking.issue.edit.submittedby"/></td><td>
    <bean:write name="issuePB" property="username" />
    </td></tr>
</logic:notEmpty>
<logic:empty name="issuePB" property="username">
    <tr><td><bean:message key="issuetracking.issue.edit.submittedby"/></td><td>
    <bean:write name="issuePB" property="mail" />
    </td></tr>
</logic:empty>
<tr><td><p class="bold"><bean:message key="issuetracking.issue.edit.eventlog"/></p></td>
</tr>
<logic:iterate name="issuePB" property="events" id="event">
    <tr><td></td><td><bean:write name="event" property="dateDisplay" />&nbsp;
    <bean:message name="event" property="stateTextKey" />
    </td></tr>
    <tr><td></td><td>
    <erland-common:expandhtml><bean:write name="event" property="description" /></erland-common:expandhtml>
    </td></tr>
    <tr><td>&nbsp;</td></tr>
</logic:iterate>
<table>
