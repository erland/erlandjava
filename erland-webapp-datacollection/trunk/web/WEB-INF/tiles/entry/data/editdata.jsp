<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="datacollection.entry.data.edit.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="entry.data.edit" target="_blank"><bean:message key="datacollection.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editdata" method="POST">
    <table class="propertypage-body">
    <html:hidden property="entryIdDisplay"/>
    <html:hidden property="idDisplay"/>
    <tr><td><bean:message key="datacollection.entry.data.edit.type"/></td><td>
    <html:text property="type" size="60"/>
    </td></tr>
    <tr><td><bean:message key="datacollection.entry.data.edit.url"/></td><td>
    <html:text property="url" size="60"/>
    </td></tr>
    <tr><td><bean:message key="datacollection.entry.data.edit.description"/></td><td>
    <html:textarea property="content" cols="60" rows="20"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="datacollection.buttons.save"/></html:submit>
    </td></tr>
    </table>
</html:form>
