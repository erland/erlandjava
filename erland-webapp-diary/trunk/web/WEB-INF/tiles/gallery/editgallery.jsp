<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editgallery" method="POST">
    <table>
    <logic:notEmpty name="editGalleryFB" property="id">
        <html:hidden property="id"/>
    </logic:notEmpty>
    <tr><td><bean:message key="diary.gallery.edit.title"/></td><td>
    <html:text property="title"/>
    </td></tr>
    <tr><td><bean:message key="diary.gallery.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="15"/>
    </td></tr>
    <tr><td><bean:message key="diary.gallery.edit.official"/></td><td>
    <html:checkbox property="official"/>
    </td></tr>
    <tr><td><bean:message key="diary.gallery.edit.referenced-gallery"/></td><td>
    <html:select property="galleryDisplay" size="1">
        <html:option value="" key="diary.gallery.edit.referenced-gallery.none"/>
        <html:options collection="externalGalleriesPB" property="idDisplay" labelProperty="title" />
    </html:select>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="diary.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
