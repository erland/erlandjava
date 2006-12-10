<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="datacollection.entry.edit.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="entry.edit" target="_blank"><bean:message key="datacollection.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editentry" method="POST">
    <table class="propertypage-body">
    <html:hidden property="idDisplay"/>
    <html:hidden property="collectionDisplay"/>
    <tr><td><bean:message key="datacollection.entry.edit.title"/></td><td>
    <html:text property="title" size="60"/>
    </td></tr>
    <tr><td><bean:message key="datacollection.entry.edit.unique-entry-id"/></td><td>
    <html:text property="uniqueEntryId"/>
    </td></tr>
    <tr><td><bean:message key="datacollection.entry.edit.description"/></td><td>
    <html:textarea property="description" cols="60" rows="5"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="datacollection.buttons.save"/></html:submit>
    </td></tr>
    </table>
</html:form>
