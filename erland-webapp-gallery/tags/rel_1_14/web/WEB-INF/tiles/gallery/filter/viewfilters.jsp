<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="propertypage-body">
<logic:iterate name="filtersPB" id="filter">
    <tr>
    <td><bean:message key="gallery.gallery.filter.edit.name"/></td>
    <td><bean:write name="filter" property="name"/></td>
    <td><a href="<html:rewrite page="/do/user/viewfilter"/>?idDisplay=<bean:write name="filter" property="idDisplay"/>" class="propertypage-button"><bean:message key="gallery.gallery.filter.menu.edit"/></a></td>
    <td><a href="<html:rewrite page="/do/user/removefilter"/>?idDisplay=<bean:write name="filter" property="idDisplay"/>" class="propertypage-button" onClick="return confirm('<bean:message key="gallery.gallery.filter.menu.delete.are-you-sure"/>')"><bean:message key="gallery.gallery.filter.menu.delete"/></a></td>
    </tr>
    <tr>
    <td><bean:message key="gallery.gallery.filter.edit.description"/></td>
    <td colspan="3"><erland-common:expandhtml><bean:write name="filter" property="description"/></erland-common:expandhtml></td>
    </tr>
    <tr>
    <td><bean:message key="gallery.gallery.filter.edit.cls"/></td>
    <td colspan="3"><bean:write name="filter" property="cls"/></td>
    </tr>
    <tr>
    <td><bean:message key="gallery.gallery.filter.edit.parameters"/></td>
    <td colspan="3"><bean:write name="filter" property="parameters"/></td>
    </tr>
    <tr><td>&nbsp;</td></tr>
</logic:iterate>
</table>
