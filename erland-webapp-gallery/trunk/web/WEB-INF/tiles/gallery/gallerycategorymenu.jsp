<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<logic:notEmpty name="galleryPB">
    <a href="<html:rewrite page="/do/user/viewgallery"/>?id=<bean:write name="galleryPB" property="id"/>" class="bold-link"><bean:message key="gallery.gallery.modify"/></a>
    <a href="<html:rewrite page="/do/user/deletegallery"/>?id=<bean:write name="galleryPB" property="id"/>" class="bold-link" onClick="return confirm('<bean:message key="gallery.gallery.delete.are-you-sure"/>')"><bean:message key="gallery.buttons.delete"/></a>
    <logic:equal name="galleryPB" property="virtual" value="true">
        <a href="<html:rewrite page="/do/newpicture"/>?gallery=<bean:write name="galleryPB" property="id"/>" class="bold-link"><bean:message key="gallery.gallery.picture.add"/></a>
        <a href="<html:rewrite page="/do/importpictures"/>?gallery=<bean:write name="galleryPB" property="id"/>" class="bold-link"><bean:message key="gallery.gallery.picture.import"/></a>
    </logic:equal>
    <logic:notEmpty name="categoryFB">
        <a href="<html:rewrite page="/do/viewcategory"/>?gallery=<bean:write name="galleryPB" property="id"/>&category=<bean:write name="categoryFB" property="category"/>" class="bold-link"><bean:message key="gallery.gallery.category.modify"/></a>
    </logic:notEmpty>
    <br>
</logic:notEmpty>
<tiles:insert page="/WEB-INF/tiles/gallery/viewpictures.jsp"/>
