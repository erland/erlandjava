<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/edituseraccount" method="POST">
    <table class="propertypage-body">
    <html:hidden property="username"/>
    <tr><td><bean:message key="dirgallery.account.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="5"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.account.edit.welcome-text"/></td><td>
    <html:textarea property="welcomeText" cols="80" rows="15"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.account.edit.logo"/></td><td>
    <html:text property="logo" size="80"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.account.edit.copyright-text"/></td><td>
    <html:text property="copyrightText" size="30"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.account.edit.default-gallery"/></td><td>
    <html:select property="defaultGalleryDisplay" size="1">
        <html:option value="" key="dirgallery.account.edit.default-gallery.none"/>
        <html:options collection="galleriesPB" property="idDisplay" labelProperty="title" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="dirgallery.account.edit.official"/></td><td>
    <html:checkbox property="official" value="true"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="dirgallery.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
