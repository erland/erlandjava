<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="gallery.gallery.category.edit.title"/></div>
<erland-common:helplink style="propertypage-button" context="gallery.category.edit" target="_blank"><bean:message key="gallery.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editcategory" method="POST">
    <html:hidden property="gallery"/>
    <html:hidden property="category"/>
    <table class="propertypage-body">
        <tr><td><bean:message key="gallery.gallery.category.edit.name"/></td><td>
        <html:text property="name"/>
        </td></tr>
        <tr><td><bean:message key="gallery.gallery.category.edit.name-english"/></td><td>
        <html:text property="nameEnglish"/>
        </td></tr>
        <tr><td><bean:message key="gallery.gallery.category.edit.visible"/></td><td>
        <html:checkbox property="officialVisibleDisplay" value="true"/>
        </td></tr>
        <tr><td colspan="2"><bean:message key="gallery.gallery.category.edit.subcategories-will-be-updated"/>
        </td></tr>
        <tr><td><bean:message key="gallery.gallery.category.edit.official"/></td><td>
        <html:checkbox property="officialDisplay" value="true"/>
        </td></tr>
        <tr><td><bean:message key="gallery.gallery.category.edit.official-guest"/></td><td>
        <html:checkbox property="officialGuestDisplay" value="true"/>
        </td></tr>
        <tr><td><bean:message key="gallery.gallery.category.edit.official-always"/></td><td>
        <html:checkbox property="officialAlwaysDisplay" value="true"/>
        </td></tr>
        <tr><td></td><td>
        <html:submit><bean:message key="gallery.buttons.save"/></html:submit>
        </td></tr>
    <table>
</html:form>
