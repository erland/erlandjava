<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="gallery.gallery.quicksetup.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="gallery.quicksetup" target="_blank"><bean:message key="gallery.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/quicksetup3" method="POST">
    <html:hidden property="galleryDisplay"/>
    <html:hidden property="publishCategoriesDisplay"/>
    <logic:notEmpty name="quicksetupGalleryFB" property="hiddenCategoriesDisplay">
        <logic:iterate id="category" name="quicksetupGalleryFB" property="hiddenCategoriesDisplay">
            <bean:define id="currentCategory" name="category" type="String"/>
            <html:hidden property="hiddenCategoriesDisplay" value="<%=currentCategory%>" />
        </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="quicksetupGalleryFB" property="officialCategoryDisplay">
        <html:hidden property="officialCategoryDisplay"/>
    </logic:notEmpty>
    <logic:notEmpty name="quicksetupGalleryFB" property="officialGuestCategoryDisplay">
        <html:hidden property="officialGuestCategoryDisplay"/>
    </logic:notEmpty>
    <logic:notEmpty name="quicksetupGalleryFB" property="topCategoryDisplay">
        <html:hidden property="topCategoryDisplay"/>
    </logic:notEmpty>
    <table class="propertypage-body">
    <logic:notEmpty name="storagesPB">
        <tr><td colspan="2">
            <bean:message key="gallery.gallery.quicksetup.picture-storages.exists"/>
        </td></tr>
        <tr><td colspan="2"><table>
        <tr>
        <td class="propertypage-caption"><bean:message key="gallery.gallery.quicksetup.picture-storages.old-path"/></td>
        <td class="propertypage-caption"><bean:message key="gallery.gallery.quicksetup.picture-storages.new-path"/></td>
        </tr>
        <logic:iterate name="storagesPB" id="storage">
            <tr>
            <td><bean:write name="storage" property="name"/></td>
            <td><bean:write name="storage" property="path"/></td>
            </tr>
        </logic:iterate>
        </table>
        </td></tr>
    </logic:notEmpty>
    <logic:empty name="storagesPB">
        <tr><td colspan="2">
            <bean:message key="gallery.gallery.quicksetup.picture-storages.none"/>
        </td></tr>
    </logic:empty>
    <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
    <tr><td colspan="2">
        <bean:message key="gallery.gallery.quicksetup.finish-information"/>
    </td></tr>
    <tr><td></td><td align="right">
    <html:submit><bean:message key="gallery.buttons.finish"/></html:submit>
    </td></tr>
</html:form>
