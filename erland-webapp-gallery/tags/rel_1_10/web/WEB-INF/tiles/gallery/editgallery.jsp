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
    <table class="propertypage-body">
    <tr><td><bean:message key="gallery.gallery.edit.title"/></td><td>
    <html:text property="title"/> 
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.title-english"/></td><td>
    <html:text property="titleEnglish"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="15" />
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.description-english"/></td><td>
    <html:textarea property="descriptionEnglish" cols="80" rows="15" />
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
    <tr><td><bean:message key="gallery.gallery.edit.antialias"/></td><td>
    <html:checkbox property="antialiasDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.thumbnail-antialias"/></td><td>
    <html:checkbox property="thumbnailAntialiasDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.compression"/></td><td>
    <html:text property="compressionDisplay"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.thumbnail-compression"/></td><td>
    <html:text property="thumbnailCompressionDisplay"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.showresolutionlinks"/></td><td>
    <html:checkbox property="showResolutionLinksDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.default-resolution"/></td><td>
    <html:select property="defaultResolution" size="1">
        <html:option value="" key="gallery.gallery.edit.default-resolution.none"/>
        <html:options collection="resolutionsPB" property="id" labelProperty="id" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.stylesheet"/></td><td>
    <html:text property="stylesheet"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.sort-order"/></td><td>
    <html:select property="sortOrder" size="1">
        <html:option value="bydatedesc" key="gallery.gallery.edit.sort-order.bydatedesc"/>
        <html:option value="bydateasc" key="gallery.gallery.edit.sort-order.bydateasc"/>
        <html:option value="byordernodesc" key="gallery.gallery.edit.sort-order.byordernodesc"/>
        <html:option value="byordernoasc" key="gallery.gallery.edit.sort-order.byordernoasc"/>
    </html:select>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.thumbnailwidth"/></td><td>
    <html:text property="thumbnailWidthDisplay"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.thumbnailheight"/></td><td>
    <html:text property="thumbnailHeightDisplay"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.noofrows"/></td><td>
    <html:text property="noOfRowsDisplay"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.noofcols"/></td><td>
    <html:text property="noOfColsDisplay"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.allow-search"/></td><td>
    <html:checkbox property="allowSearchDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.cutlongpicturetitles"/></td><td>
    <html:checkbox property="cutLongPictureTitlesDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.useshortpicturenames"/></td><td>
    <html:checkbox property="useShortPictureNamesDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.showpicturetitle"/></td><td>
    <html:checkbox property="showPictureTitleDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.thumbnail-picture-title"/></td><td>
    <html:textarea property="thumbnailPictureTitle" cols="60" rows="2" />
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.picture-title"/></td><td>
    <html:textarea property="pictureTitle" cols="60" rows="2" />
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.thumbnail-row-1"/></td><td>
    <html:textarea property="thumbnailRow1" cols="60" rows="2" />
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.thumbnail-row-2"/></td><td>
    <html:textarea property="thumbnailRow2" cols="60" rows="2" />
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.thumbnail-row-3"/></td><td>
    <html:textarea property="thumbnailRow3" cols="60" rows="2" />
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.copyright-text"/></td><td>
    <html:text property="copyrightText"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.official"/></td><td>
    <html:checkbox property="official" value="true"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.skin"/></td><td>
    <html:select property="skin" size="1">
        <html:option value="" key="gallery.gallery.edit.skin.none"/>
        <html:options collection="skinsPB" property="id" labelProperty="id" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.edit.referenced-gallery"/></td><td>
    <html:select property="referencedGallery" size="1">
        <html:option value="" key="gallery.gallery.edit.referenced-gallery.none"/>
        <html:options collection="galleriesPB" property="idDisplay" labelProperty="title" />
    </html:select>
    </td></tr>
    <logic:equal name="galleryFB" property="referencedGallery" value="0">
        <tr><td><bean:message key="gallery.gallery.edit.official-category"/></td><td>
        <html:select property="officialCategory" size="1">
            <html:option value="" key="gallery.gallery.edit.official-category.none"/>
            <html:options collection="categoriesPB" property="categoryDisplay" labelProperty="name" />
        </html:select>
        </td></tr>
        <tr><td><bean:message key="gallery.gallery.edit.officialguest-category"/></td><td>
        <html:select property="officialGuestCategory" size="1">
            <html:option value="" key="gallery.gallery.edit.officialguest-category.none"/>
            <html:options collection="categoriesPB" property="categoryDisplay" labelProperty="name" />
        </html:select>
        </td></tr>
        <tr><td><bean:message key="gallery.gallery.edit.forcepictureupdate"/></td><td>
        <html:checkbox property="forcePictureUpdateDisplay" value="true"/>
        </td></tr>
    </logic:equal>
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
