<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<p class="propertypage-description"><bean:message key="gallery.guestaccount.description"/></p>
<br>
<a href="<html:rewrite page="/do/user/newguestaccount"/>" class="propertypage-button"><bean:message key="gallery.guestaccount.menu.add"/></a>
<table class="propertypage-body">
<logic:iterate name="guestAccountsPB" id="account">
    <tr>
    <td><bean:write name="account" property="guestUser"/></td>
    <td><a href="<html:rewrite page="/do/user/removeguestaccount"/>?guestUser=<bean:write name="account" property="guestUser"/>" class="propertypage-button" onClick="return confirm('<bean:message key="gallery.guestaccount.menu.delete.are-you-sure"/>')"><bean:message key="gallery.guestaccount.menu.delete"/></a></td>
    </tr>
</logic:iterate>
</table>
