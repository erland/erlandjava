<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="gallery.gallery.quicksetup.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="gallery.edit" target="_blank"><bean:message key="gallery.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/quicksetup2" method="POST">
    <html:hidden property="galleryDisplay"/>
    <html:hidden property="publishCategoriesDisplay"/>
    <table class="propertypage-body">
    <tr><td><bean:message key="gallery.gallery.quicksetup.top-category"/></td><td>
    <html:select property="topCategoryDisplay" size="1">
        <html:option value="" key="gallery.gallery.quicksetup.top-category.none"/>
        <html:options collection="categoriesPB" property="categoryDisplay" labelProperty="name" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.quicksetup.hidden-categories"/></td><td>
    <html:select property="hiddenCategoriesDisplay" size="10" multiple="true">
        <html:option value="" key="gallery.gallery.quicksetup.hidden-categories.none"/>
        <html:options collection="categoriesPB" property="categoryDisplay" labelProperty="name" />
    </html:select>
    </td></tr>
    <tr><td></td><td align="right">
    <html:submit><bean:message key="gallery.buttons.next"/></html:submit>
    </td></tr>
</html:form>
