<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="help.application.edit.title"/></div>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editapplication" method="POST">
    <table class="propertypage-body">
    <html:hidden property="username"/>
    <tr><td><bean:message key="help.application.edit.name"/></td><td>
    <html:text property="name"/>
    </td></tr>
    <tr><td><bean:message key="help.application.edit.title-english"/></td><td>
    <html:text property="titleEnglish"/>
    </td></tr>
    <tr><td><bean:message key="help.application.edit.title-native"/></td><td>
    <html:text property="titleNative"/>
    </td></tr>
    <tr><td><bean:message key="help.application.edit.logo"/></td><td>
    <html:text property="logo" size="60" />
    </td></tr>
    <tr><td><bean:message key="help.application.edit.official"/></td><td>
    <html:checkbox property="official" value="true"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="help.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
