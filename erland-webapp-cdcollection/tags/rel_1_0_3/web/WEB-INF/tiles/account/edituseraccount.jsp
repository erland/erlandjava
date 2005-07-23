<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="cdcollection.account.edit.title"/></div>
<erland-common:helplink style="propertypage-button" context="account.edit" target="_blank"><bean:message key="cdcollection.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/edituseraccount" method="POST">
    <table class="propertypage-body">
    <html:hidden property="username"/>
    <tr><td><bean:message key="cdcollection.account.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="5"/>
    </td></tr>
    <tr><td><bean:message key="cdcollection.account.edit.description-english"/></td><td>
    <html:textarea property="descriptionEnglish" cols="80" rows="5"/>
    </td></tr>
    <tr><td><bean:message key="cdcollection.account.edit.welcome-text"/></td><td>
    <html:textarea property="welcomeText" cols="80" rows="15"/>
    </td></tr>
    <tr><td><bean:message key="cdcollection.account.edit.welcome-text-english"/></td><td>
    <html:textarea property="welcomeTextEnglish" cols="80" rows="15"/>
    </td></tr>
    <tr><td><bean:message key="cdcollection.account.edit.logo"/></td><td>
    <html:text property="logo" size="80"/>
    </td></tr>
    <tr><td><bean:message key="cdcollection.account.edit.stylesheet"/></td><td>
    <html:text property="stylesheet" size="60"/>
    </td></tr>
    <tr><td><bean:message key="cdcollection.account.edit.default-collection"/></td><td>
    <html:select property="defaultCollectionDisplay" size="1">
        <html:option value="" key="cdcollection.account.edit.default-collection.none"/>
        <html:options collection="galleriesPB" property="idDisplay" labelProperty="title" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="cdcollection.account.edit.official"/></td><td>
    <html:checkbox property="officialDisplay" value="true"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="cdcollection.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
