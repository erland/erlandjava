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

<table width="600" class="no-border">
    <logic:iterate name="picturesPB" property="pictures" id="picture" indexId="picNo" >
        <bean:define id="modPicNo" value="<%=String.valueOf(picNo.intValue()%3)%>" type="String"/>
        <logic:equal name="modPicNo" value="0">
            <logic:notEqual name="picNo" value="0">
                </tr>
            </logic:notEqual>
            <tr>
        </logic:equal>
        <td align="center">
        <erland-common:beanlink style="bold-link" name="picture" property="updateLink"><bean:message key="diary.gallery.picture.modify"/></erland-common:beanlink>
        <erland-common:beanlink style="bold-link" name="picture" property="removeLink" onClickMessageKey="diary.gallery.picture.remove.are-you-sure"><bean:message key="diary.gallery.picture.remove"/><br></erland-common:beanlink>
        <erland-common:beanlink style="bold-link" name="picture" property="link" target="_blank" propertyTitle="description">
            <erland-common:beanimage name="picture" property="image" border="0"/><br><div align="center"><bean:write name="picture" property="title"/></div>
        </erland-common:beanlink>
        </td>
    </logic:iterate>
    </tr>
</table>
