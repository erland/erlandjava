<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="propertypage-body">
    <tr>
        <td class="propertypage-tableheader"><bean:message key="issuetracking.issue.edit.id"/></td>
        <td class="propertypage-tableheader"><bean:message key="issuetracking.issue.edit.type"/></td>
        <td class="propertypage-tableheader"><bean:message key="issuetracking.issueevent.edit.state"/></td>
        <td class="propertypage-tableheader"><bean:message key="issuetracking.issue.edit.submittedby"/></td>
        <td class="propertypage-tableheader"><bean:message key="issuetracking.issue.list.assignedto"/></td>
        <td class="propertypage-tableheader"><bean:message key="issuetracking.issue.edit.application"/></td>
        <td class="propertypage-tableheader"><bean:message key="issuetracking.issue.edit.title"/></td>
    </tr>
<logic:iterate name="issuesPB" id="issue">
    <tr>
    <td>
    <bean:write name="issue" property="id" />
    </td>
    <td>
    <bean:message name="issue" property="typeTextKey" />
    </td>
    <td>
    <bean:message name="issue" property="stateTextKey" />
    </td>
    <logic:notEmpty name="issue" property="username">
        <td>
        <bean:write name="issue" property="username" />
        </td>
    </logic:notEmpty>
    <logic:empty name="issue" property="username">
        <logic:notEmpty name="issue" property="realname">
            <td>
            <bean:write name="issue" property="realname" />
            </td>
        </logic:notEmpty>
        <logic:empty name="issue" property="realname">
            <td>
            <bean:write name="issue" property="mailDisplay" />
            </td>
        </logic:empty>
    </logic:empty>
    <td>
    <bean:write name="issue" property="assignedTo" />
    </td>
    <td nowrap>
    <bean:write name="issue" property="applicationTitle" />
    </td>
    <td nowrap>
    <erland-common:beanlink name="issue" property="viewLink" style="propertypage-button"><bean:write name="issue" property="title" /></erland-common:beanlink>
    </td>
    </tr>
</logic:iterate>
<table>
