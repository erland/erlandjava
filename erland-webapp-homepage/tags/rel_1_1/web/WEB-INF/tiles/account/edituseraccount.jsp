<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="homepage.account.edit.title"/></div>
<erland-common:helplink style="propertypage-button" context="account.edit" target="_blank"><bean:message key="homepage.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/edituseraccount" method="POST">
    <table class="propertypage-body">
    <html:hidden property="username"/>
    <tr><td><bean:message key="homepage.account.edit.header-text"/></td><td>
    <html:text property="headerText" size="80"/>
    </td></tr>
    <tr><td><bean:message key="homepage.account.edit.header-text-english"/></td><td>
    <html:text property="headerTextEnglish" size="80"/>
    </td></tr>
    <tr><td><bean:message key="homepage.account.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="5"/>
    </td></tr>
    <tr><td><bean:message key="homepage.account.edit.description-english"/></td><td>
    <html:textarea property="descriptionEnglish" cols="80" rows="5"/>
    </td></tr>
    <tr><td><bean:message key="homepage.account.edit.welcome-text"/></td><td>
    <html:textarea property="welcomeText" cols="80" rows="15"/>
    </td></tr>
    <tr><td><bean:message key="homepage.account.edit.welcome-text-english"/></td><td>
    <html:textarea property="welcomeTextEnglish" cols="80" rows="15"/>
    </td></tr>
    <tr><td><bean:message key="homepage.account.edit.logo-link"/></td><td>
    <html:text property="logoLink" size="80"/>
    </td></tr>
    <tr><td><bean:message key="homepage.account.edit.logo"/></td><td>
    <html:text property="logo" size="80"/>
    </td></tr>
    <tr><td><bean:message key="homepage.account.edit.logo-english"/></td><td>
    <html:text property="logoEnglish" size="80"/>
    </td></tr>
    <tr><td><bean:message key="homepage.account.edit.show-logo"/></td><td>
    <html:checkbox property="showLogoDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="homepage.account.edit.default-section"/></td><td>
    <html:select property="defaultSectionDisplay" size="1">
        <html:option value="" key="homepage.account.edit.default-section.none"/>
        <html:options collection="sectionsPB" property="idDisplay" labelProperty="name" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="homepage.account.edit.copyright-text"/></td><td>
    <html:text property="copyrightText" size="30"/>
    </td></tr>
    <tr><td><bean:message key="homepage.account.edit.stylesheet"/></td><td>
    <html:text property="stylesheet" size="60"/>
    </td></tr>
    <tr><td><bean:message key="homepage.account.edit.skin"/></td><td>
    <html:select property="skin" size="1">
        <html:option value="" key="homepage.account.edit.skin.none"/>
        <html:options collection="skinsPB" property="id" labelProperty="id" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="homepage.account.edit.official"/></td><td>
    <html:checkbox property="officialDisplay" value="true"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="homepage.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
