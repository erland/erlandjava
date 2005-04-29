<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="tvguide.account.edit.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="account.edit" target="_blank"><bean:message key="tvguide.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/edituseraccount" method="POST">
    <table class="propertypage-body">
    <html:hidden property="username"/>
    <tr><td><bean:message key="tvguide.account.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="5"/>
    </td></tr>
    <tr><td><bean:message key="tvguide.account.edit.welcome-text"/></td><td>
    <html:textarea property="welcomeText" cols="80" rows="15"/>
    </td></tr>
    <tr><td><bean:message key="tvguide.account.edit.logo"/></td><td>
    <html:text property="logo" size="80"/>
    </td></tr>
    <tr><td><bean:message key="tvguide.account.edit.stylesheet"/></td><td>
    <html:text property="stylesheet" size="60"/>
    </td></tr>
    <tr><td><bean:message key="tvguide.account.edit.skin"/></td><td>
    <html:select property="skin" size="1">
        <html:option value="" key="tvguide.account.edit.skin.none"/>
        <html:options collection="skinsPB" property="id" labelProperty="id" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="tvguide.account.edit.mailnotification"/></td><td>
    <html:checkbox property="mailNotificationDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="tvguide.account.edit.jabbernotification"/></td><td>
    <html:checkbox property="jabberNotificationDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="tvguide.account.edit.jabberid"/></td><td>
    <html:text property="jabberId" />
    </td></tr>
    <tr><td><bean:message key="tvguide.account.edit.official"/></td><td>
    <html:checkbox property="officialDisplay" value="true"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="tvguide.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
