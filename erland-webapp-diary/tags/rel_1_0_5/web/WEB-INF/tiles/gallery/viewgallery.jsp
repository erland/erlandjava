<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:beanlink name="galleryInfoPB" property="updateLink" style="bold-link"><bean:message key="diary.gallery.modify"/></erland-common:beanlink>
<erland-common:beanlink name="galleryInfoPB" property="removeLink" style="bold-link" onClickMessageKey="diary.gallery.delete.are-you-sure"><bean:message key="diary.gallery.delete"/></erland-common:beanlink>
<erland-common:beanlink name="galleryInfoPB" property="newEntryLink" style="bold-link"><bean:message key="diary.gallery.add-picture"/></erland-common:beanlink>
<br>

<table width="600" class="no-border">
    <tr><td><p class="title"><bean:write name="galleryInfoPB" property="title"/></p></td></tr>
    <tr><td><p class="normal"><erland-common:expandhtml><bean:write name="galleryInfoPB" property="description"/></erland-common:expandhtml></p></td></tr>
</table>

<erland-common:tablegrid name="picturesPB" property="pictures" id="picture" valign="bottom" align="center" cols="3" tableStyle="no-border" width="600">
    <erland-common:beanlink style="bold-link" name="picture" property="updateLink"><bean:message key="diary.gallery.picture.modify"/></erland-common:beanlink>
    <erland-common:beanlink style="bold-link" name="picture" property="removeLink" onClickMessageKey="diary.gallery.picture.remove.are-you-sure"><bean:message key="diary.gallery.picture.remove"/><br></erland-common:beanlink>
    <erland-common:beanlink style="bold-link" name="picture" property="link" target="_blank" propertyTitle="description">
        <erland-common:beanimage name="picture" property="image" border="0" height="113"/><br><div align="center"><bean:write name="picture" property="title"/></div>
    </erland-common:beanlink>
</erland-common:tablegrid>
