<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="gallery.guestaccount.edit.title"/></div>
<erland-common:helplink style="propertypage-button" context="guestaccount.edit" target="_blank"><bean:message key="gallery.help.button"/></erland-common:helplink>
<p class="propertypage-description"><bean:message key="gallery.guestaccount.add.description"/></p>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editguestaccount" method="POST">
    <table class="propertypage-body">
    <tr><td><bean:message key="gallery.guestaccount.edit.username"/></td><td>
    <html:text property="guestUser" />
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="gallery.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>