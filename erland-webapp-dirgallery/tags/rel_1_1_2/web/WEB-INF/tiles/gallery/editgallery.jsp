<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<jsp:include page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editgallery" method="POST">
    <table>
    <logic:notEmpty name="editGalleryFB" property="id">
        <html:hidden property="id"/>
    </logic:notEmpty>
    <tr><td><bean:message key="dirgallery.gallery.edit.title"/></td><td>
    <html:text property="title" size="30"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.menu-name"/></td><td>
    <html:text property="menuName" size="30"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="5"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.directory"/></td><td>
    <html:text property="directory" size="80"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.ordernumber"/></td><td>
    <html:text property="orderNumber" size="30"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.include-sub-directories"/></td><td>
    <html:checkbox property="includeSubDirectoriesDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.type-of-files"/></td><td>
    <html:select property="typeOfFiles" size="1">
        <html:option value="0" key="dirgallery.gallery.edit.type-of-files.pictures"/>
        <html:option value="1" key="dirgallery.gallery.edit.type-of-files.movies"/>
    </html:select>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.original-downloadable"/></td><td>
    <html:checkbox property="originalDownloadableDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.official"/></td><td>
    <html:checkbox property="officialDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.show-logo-in-gallery-page"/></td><td>
    <html:checkbox property="showLogoInGalleryPageDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.show-picture-names"/></td><td>
    <html:checkbox property="showPictureNamesDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.use-short-picture-names"/></td><td>
    <html:checkbox property="useShortPictureNamesDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.max-picture-name-length"/></td><td>
    <html:text property="maxPictureNameLength"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.show-picture-name-in-tooltip"/></td><td>
    <html:checkbox property="showPictureNameInTooltipDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.show-comment-below-picture"/></td><td>
    <html:checkbox property="showCommentBelowPictureDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.show-filesize-below-picture"/></td><td>
    <html:checkbox property="showFileSizeBelowPictureDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.thumbnail-width"/></td><td>
    <html:text property="thumbnailWidth"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.no-of-thumbnails-per-row"/></td><td>
    <html:text property="numberOfThumbnailsPerRow"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.max-no-of-thumbnail-rows"/></td><td>
    <html:text property="maxNumberOfThumbnailRows"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.no-of-movie-thumbnail-columns"/></td><td>
    <html:text property="numberOfMovieThumbnailColumns"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.no-of-movie-thumbnail-rows"/></td><td>
    <html:text property="numberOfMovieThumbnailRows"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.thumbnail-compression"/></td><td>
    <html:text property="thumbnailCompression"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.use-thumbnail-cache"/></td><td>
    <html:checkbox property="useThumbnailCacheDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.logo"/></td><td>
    <html:text property="logo"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.logo-link"/></td><td>
    <html:text property="logoLink"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.use-logo-separator"/></td><td>
    <html:checkbox property="useLogoSeparatorDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.logo-separator-color"/></td><td>
    <html:text property="logoSeparatorColor"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.logo-separator-height"/></td><td>
    <html:text property="logoSeparatorHeight"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.show-download-links"/></td><td>
    <html:checkbox property="showDownloadLinksDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.default-resolution"/></td><td>
    <html:select property="defaultResolution" size="1">
        <html:option value="" key="dirgallery.gallery.edit.default-resolution.none"/>
        <html:options collection="resolutionsPB" property="id" labelProperty="id" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.max-width"/></td><td>
    <html:text property="maxWidth"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.use-tooltip"/></td><td>
    <html:checkbox property="useTooltipDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.gallery.edit.friend-galleries"/></td><td>
    <html:select property="friendGalleries" size="10" multiple="true" >
        <html:options collection="galleriesPB" property="idDisplay" labelProperty="title" />
    </html:select>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="dirgallery.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
