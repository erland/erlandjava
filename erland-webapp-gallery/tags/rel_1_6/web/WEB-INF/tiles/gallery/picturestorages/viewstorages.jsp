<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table border="0">
<logic:iterate name="storagesPB" id="storage">
    <tr>
    <td><bean:write name="storage" property="idDisplay"/></td>
    <td><bean:write name="storage" property="name"/></td>
    <td><bean:write name="storage" property="path"/></td>
    <td><a href="<html:rewrite page="/do/user/viewpicturestorage"/>?idDisplay=<bean:write name="storage" property="idDisplay"/>" class="bold-link"><bean:message key="gallery.picturestorage.menu.edit"/></a></td>
    <td><a href="<html:rewrite page="/do/user/removepicturestorage"/>?idDisplay=<bean:write name="storage" property="idDisplay"/>" class="bold-link" onClick="return confirm('<bean:message key="gallery.picturestorage.menu.delete.are-you-sure"/>')"><bean:message key="gallery.picturestorage.menu.delete"/></a></td>
    </tr>
</logic:iterate>
</table>
