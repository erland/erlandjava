<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/edituseraccount" method="POST">
    <table>
    <html:hidden property="username"/>
    <tr><td><bean:message key="diary.account.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="5"/>
    </td></tr>
    <tr><td><bean:message key="diary.account.edit.welcome-text"/></td><td>
    <html:textarea property="welcomeText" cols="80" rows="15"/>
    </td></tr>
    <tr><td><bean:message key="diary.account.edit.logo"/></td><td>
    <html:text property="logo" size="80"/>
    </td></tr>
    <tr><td><bean:message key="diary.account.edit.default-diary"/></td><td>
    <html:select property="defaultDiaryDisplay" size="1">
        <html:options collection="diariesPB" property="idDisplay" labelProperty="title" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="diary.account.edit.official"/></td><td>
    <html:checkbox property="official" />
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="diary.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
