<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editapplication" method="POST">
    <table>
    <html:hidden property="username"/>
    <tr><td><bean:message key="issuetracking.application.edit.name"/></td><td>
    <html:text property="name"/>
    </td></tr>
    <tr><td><bean:message key="issuetracking.application.edit.title-english"/></td><td>
    <html:text property="titleEnglish"/>
    </td></tr>
    <tr><td><bean:message key="issuetracking.application.edit.title-native"/></td><td>
    <html:text property="titleNative"/>
    </td></tr>
    <tr><td><bean:message key="issuetracking.application.edit.logo"/></td><td>
    <html:text property="logo"/>
    </td></tr>
    <tr><td><bean:message key="issuetracking.application.edit.official"/></td><td>
    <html:checkbox property="official" value="true"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="issuetracking.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
