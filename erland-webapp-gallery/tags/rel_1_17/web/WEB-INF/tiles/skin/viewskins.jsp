<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="propertypage-body">
<logic:iterate name="skinsPB" id="skin">
    <tr>
    <td><bean:write name="skin" property="id"/></td>
    <td><a href="<html:rewrite page="/do/user/viewskin"/>?id=<bean:write name="skin" property="id"/>" class="propertypage-button"><bean:message key="gallery.skin.menu.edit"/></a></td>
    <td><a href="<html:rewrite page="/do/user/removeskin"/>?id=<bean:write name="skin" property="id"/>" class="propertypage-button" onClick="return confirm('<bean:message key="gallery.skin.menu.delete.are-you-sure"/>')"><bean:message key="gallery.skin.menu.delete"/></a></td>
    </tr>
</logic:iterate>
</table>
