<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editgalleryfilter" method="POST">
    <table class="propertypage-body">
    <html:hidden property="id"/>
    <html:hidden property="gallery"/>
    <tr><td><bean:message key="gallery.gallery.galleryfilter.edit.filter"/></td><td>
    <html:select property="filter" size="1">
        <html:option value="" />
        <html:options collection="filtersPB" property="idDisplay" labelProperty="name" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.galleryfilter.edit.orderno"/></td><td>
    <html:text property="orderNo" />
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.galleryfilter.edit.parameters"/></td><td>
    <html:text property="parameters" size="80"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="gallery.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
