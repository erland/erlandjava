<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<table class="propertypage-header">
<tr><td>
<p class="propertypage-description"><bean:message key="gallery.gallery.import.edit.description01"/> <a class="propertypage-link" href="http://www.photools.com/" target="_blank">IMatch</a>.
<p class="propertypage-description"><bean:message key="gallery.gallery.import.edit.description02"/>
<p class="propertypage-description"><bean:message key="gallery.gallery.import.edit.description03"/>
</td></tr>
</table>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/importpictures" method="POST">
    <html:hidden property="gallery"/>
    <table class="propertypage-body">
    <tr><td><bean:message key="gallery.gallery.import.edit.path"/></td><td>
    <html:text property="file" size="80"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.import.edit.clearcategories"/></td><td>
    <html:checkbox property="clearCategories" value="true"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.import.edit.clearpictures"/></td><td>
    <html:checkbox property="clearPictures" value="true"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.import.edit.locallinks"/></td><td>
    <html:checkbox property="localLinks" value="true"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.import.edit.filenameaspicturetitle"/></td><td>
    <html:checkbox property="filenameAsPictureTitle" value="true"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.import.edit.filenameaspicturedescription"/></td><td>
    <html:checkbox property="filenameAsPictureDescription" value="true"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="gallery.buttons.import"/></html:submit>
    </td></tr>
<table>
</html:form>
<p class="propertypage-notice"><bean:message key="gallery.gallery.import.edit.warning.many-files"/></p>

