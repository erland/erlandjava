<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="gallery.tasks.edit.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="tasks.edit" target="_blank"><bean:message key="gallery.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/edittasks" method="POST">
    <table class="propertypage-body">
    <tr><td><bean:message key="gallery.tasks.edit.backgroundimport"/></td><td>
    <html:checkbox property="importPictures" value="true"/>
    </td></tr>
    <tr><td><bean:message key="gallery.tasks.edit.backgroundexternalimport"/></td><td>
    <html:checkbox property="externalImportPictures" value="true" />
    </td></tr>
    <tr><td><bean:message key="gallery.tasks.edit.backgroundthumbnailgeneration"/></td><td>
    <html:checkbox property="thumbnailGeneration" value="true" />
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="gallery.buttons.save"/></html:submit>
    </td></tr>
</html:form>
