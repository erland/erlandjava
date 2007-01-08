<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/guest/editissue" method="POST">
    <table class="propertypage-body">
    <tr><td><bean:message key="issuetracking.issue.edit.type"/></td><td>
    <html:select property="typeDisplay" size="1">
        <erland-common:htmloptions collection="typesPB" property="id" labelKeyProperty="titleKey" />
    </html:select>
    <tr><td><bean:message key="issuetracking.issue.edit.application"/></td><td>
    <html:select property="application" size="1">
        <html:options collection="applicationsPB" property="name" labelProperty="title" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="issuetracking.issue.edit.mail"/></td><td>
    <html:text property="mail" />
    </td></tr>
    <tr><td><bean:message key="issuetracking.issue.edit.realname"/></td><td>
    <html:text property="realname" />
    </td></tr>
    <tr><td><bean:message key="issuetracking.issue.edit.version"/></td><td>
    <html:text property="version" />
    </td></tr>
    <tr><td><bean:message key="issuetracking.issue.edit.title"/></td><td>
    <html:text property="title" />
    </td></tr>
    <tr><td><bean:message key="issuetracking.account.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="5"/>
    </td></tr>
    <tr><td><bean:message key="issuetracking.issue.edit.externalreference"/></td><td>
    <html:text property="externalReference" />
    </td></tr>
    <tr><td><bean:message key="issuetracking.issue.edit.requestedrandomkey"/></td><td>
    <html:hidden property="requestedRandomKey" /><bean:write name="editIssueFB" property="requestedRandomKey" />
    </td></tr>
    <tr><td><bean:message key="issuetracking.issue.edit.randomkey"/></td><td>
    <html:text property="randomKey" />
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="issuetracking.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
