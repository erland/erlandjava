<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editgallery" method="POST">
    <logic:notEmpty name="galleryFB" property="id">
        <html:hidden property="id"/>
    </logic:notEmpty> 
    <table>
    <tr><td><bean:message key="gallery.gallery.edit.title"/></td><td>
    <html:text property="title"/> 
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="15" />
    </td></tr>
    <logic:notEmpty name="galleryFB" property="id">
        <tr><td><bean:message key="gallery.gallery.edit.top-category"/></td><td>
        <html:select property="topCategory" size="1">
            <html:option value="" key="gallery.gallery.edit.top-category.none"/>
            <html:options collection="categoriesPB" property="categoryDisplay" labelProperty="name" />
        </html:select>
        </td></tr>
    </logic:notEmpty>
    <tr><td><bean:message key="gallery.gallery.edit.max-width"/></td><td>
    <html:text property="maxWidthDisplay"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.default-resolution"/></td><td>
    <html:select property="defaultResolution" size="1">
        <html:option value="" key="gallery.gallery.edit.default-resolution.none"/>
        <html:options collection="resolutionsPB" property="id" labelProperty="id" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.official"/></td><td>
    <html:checkbox property="official" value="true"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.referenced-gallery"/></td><td>
    <html:select property="referencedGallery" size="1">
        <html:option value="" key="gallery.gallery.edit.referenced-gallery.none"/>
        <html:options collection="galleriesPB" property="idDisplay" labelProperty="title" />
    </html:select>
    </td></tr>
    <logic:notEmpty name="galleryFB" property="referencedGallery">
        <logic:notEqual name="galleryFB" property="referencedGallery" value="0">
            <tr><td><bean:message key="gallery.gallery.edit.required-categories"/></td><td>
            <html:select property="categories" size="10" multiple="true">
                <html:options collection="categoriesPB" property="categoryDisplay" labelProperty="name" />
            </html:select>
            </td></tr>
        </logic:notEqual>
    </logic:notEmpty>
    <tr><td></td><td>
    <html:submit><bean:message key="gallery.buttons.save"/></html:submit>
    </td></tr>
</html:form>
