<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:beanlink style="bold-link" name="galleryFiltersPB" property="newLink"><bean:message key="gallery.gallery.galleryfilter.menu.add"/></erland-common:beanlink>
<table border="0">
<logic:iterate name="galleryFiltersPB" property="filters" id="filter">
    <tr>
    <td><bean:message key="gallery.gallery.galleryfilter.edit.name"/>: </td>
    <td><bean:write name="filter" property="name"/></td>
    <td><a href="<html:rewrite page="/do/user/viewgalleryfilter"/>?gallery=<bean:write name="filter" property="gallery"/>&idDisplay=<bean:write name="filter" property="idDisplay"/>" class="bold-link"><bean:message key="gallery.gallery.filter.menu.edit"/></a></td>
    <td><a href="<html:rewrite page="/do/user/removegalleryfilter"/>?gallery=<bean:write name="filter" property="gallery"/>&idDisplay=<bean:write name="filter" property="idDisplay"/>" class="bold-link" onClick="return confirm('<bean:message key="gallery.gallery.galleryfilter.menu.delete.are-you-sure"/>')"><bean:message key="gallery.gallery.galleryfilter.menu.delete"/></a></td>
    </tr>
    <logic:notEmpty name="filter" property="parameters">
        <tr>
        <td><bean:message key="gallery.gallery.filter.edit.parameters"/></td>
        <td colspan="3"><bean:write name="filter" property="parameters"/></td>
        </tr>
    </logic:notEmpty>
    <tr><td>&nbsp;</td></tr>
</logic:iterate>
</table>
