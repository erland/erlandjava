<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editissueevent" method="POST">
    <table>
    <html:hidden property="issueId"/>
    <tr><td><bean:message key="issuetracking.issueevent.edit.state"/></td><td>
    <html:select property="state" size="1">
        <erland-common:htmloptions collection="statesPB" property="id" labelKeyProperty="titleKey" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="issuetracking.issueevent.edit.username"/></td><td>
    <html:select property="username" size="1">
        <html:options collection="usersPB" property="username"/>
    </html:select>
    </td></tr>
    <tr><td><bean:message key="issuetracking.issueevent.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="5"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="issuetracking.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
