<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<p class="normal"><bean:message key="gallery.gallery.import.edit.description01"/> <a class="bold-link" href="http://www.photools.com/" target="_blank">IMatch</a>.
<p class="normal"><bean:message key="gallery.gallery.import.edit.description02"/>
<p class="normal"><bean:message key="gallery.gallery.import.edit.description03"/>
<br>
<jsp:include page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/importpictures" method="POST">
    <html:hidden property="gallery"/>
    <table>
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
    <tr><td><bean:message key="gallery.gallery.import.edit.cutlongpicturetitles"/></td><td>
    <html:checkbox property="cutLongPictureTitles" value="true"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit titleKey="gallery.buttons.save"/>
    </td></tr>
<table>
</html:form>
<p class="normal"><bean:message key="gallery.gallery.import.edit.warning.many-files"/></p>

