<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<logic:notEmpty name="galleryPB">
    <a href="<html:rewrite page="/do/user/viewgallery"/>?id=<bean:write name="galleryPB" property="id"/>" class="picturepage-button"><bean:message key="gallery.gallery.modify"/></a>&nbsp;-&nbsp;
    <a href="<html:rewrite page="/do/user/removegallery"/>?id=<bean:write name="galleryPB" property="id"/>" class="picturepage-button" onClick="return confirm('<bean:message key="gallery.gallery.delete.are-you-sure"/>')"><bean:message key="gallery.gallery.delete"/></a>&nbsp;-&nbsp;
    <a href="<html:rewrite page="/do/user/viewgalleryfilters"/>?gallery=<bean:write name="galleryPB" property="id"/>" class="picturepage-button"><bean:message key="gallery.gallery.view-filters"/></a>&nbsp;-&nbsp;
    <a href="<html:rewrite page="/do/user/clearcachegallery"/>?id=<bean:write name="galleryPB" property="id"/>" class="picturepage-button"><bean:message key="gallery.gallery.clear-cache"/></a>
    <logic:notEqual name="galleryPB" property="virtualDisplay" value="true">
        &nbsp;-&nbsp;<a href="<html:rewrite page="/do/user/newpicture"/>?gallery=<bean:write name="galleryPB" property="id"/>" class="picturepage-button"><bean:message key="gallery.gallery.add-picture"/></a>
        &nbsp;-&nbsp;<a href="<html:rewrite page="/do/user/newimportpictures"/>?gallery=<bean:write name="galleryPB" property="id"/>" class="picturepage-button"><bean:message key="gallery.gallery.import-pictures"/></a>
        &nbsp;-&nbsp;<a href="<html:rewrite page="/do/user/quicksetup"/>?galleryDisplay=<bean:write name="galleryPB" property="id"/>" class="picturepage-button"><bean:message key="gallery.gallery.quicksetup"/></a>
    </logic:notEqual>
    <logic:notEmpty name="selectPictureFB">
        <logic:notEmpty name="selectPictureFB" property="category">
            &nbsp;-&nbsp;<a href="<html:rewrite page="/do/user/viewcategory"/>?gallery=<bean:write name="galleryPB" property="id"/>&category=<bean:write name="selectPictureFB" property="category"/>" class="picturepage-button"><bean:message key="gallery.gallery.modify-category"/></a>
        </logic:notEmpty>
    </logic:notEmpty>
    <br>
</logic:notEmpty>
<tiles:insert page="/WEB-INF/tiles/gallery/viewpictures.jsp"/>
