<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:beanlink name="galleryInfoPB" property="updateLink" style="gallerypage-button"><bean:message key="diary.gallery.modify"/></erland-common:beanlink>
<erland-common:beanlink name="galleryInfoPB" property="removeLink" style="gallerypage-button" onClickMessageKey="diary.gallery.delete.are-you-sure"><bean:message key="diary.gallery.delete"/></erland-common:beanlink>
<erland-common:beanlink name="galleryInfoPB" property="newEntryLink" style="gallerypage-button"><bean:message key="diary.gallery.add-picture"/></erland-common:beanlink>
<br>

<table width="600" class="gallerypage-header">
    <tr><td><p class="gallerypage-title"><bean:write name="galleryInfoPB" property="title"/></p></td></tr>
    <tr><td><p class="gallerypage-description"><erland-common:expandhtml><bean:write name="galleryInfoPB" property="description"/></erland-common:expandhtml></p></td></tr>
</table>

<erland-common:tablegrid name="picturesPB" property="pictures" id="picture" valign="bottom" align="center" cols="3" tableStyle="gallerypage-body" rowStyle="gallerypage-picture-row" cellStyle="gallerypage-picture-cell" width="600">
    <erland-common:beanlink style="gallerypage-picture-button" name="picture" property="updateLink"><bean:message key="diary.gallery.picture.modify"/></erland-common:beanlink>
    <erland-common:beanlink style="gallerypage-picture-button" name="picture" property="removeLink" onClickMessageKey="diary.gallery.picture.remove.are-you-sure"><bean:message key="diary.gallery.picture.remove"/><br></erland-common:beanlink>
    <erland-common:beanlink style="gallerypage-picture-link" name="picture" property="link" target="_blank" propertyTitle="description">
        <erland-common:beanimage name="picture" property="image" border="0"/><br><div class="gallerypage-picture-title" align="center"><bean:write name="picture" property="title"/></div>
    </erland-common:beanlink>
</erland-common:tablegrid>
