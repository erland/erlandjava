<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<logic:notEmpty name="galleryPB">
<table width="450" class="no-border-tight">
    <tr><td>
    <erland-common:beanlink name="galleryPB" property="updateLink" style="bold-link"><bean:message key="dirgallery.gallery.modify"/></erland-common:beanlink>
    <erland-common:beanlink name="galleryPB" property="deleteLink" style="bold-link" onClickMessageKey="dirgallery.gallery.delete.are-you-sure"><bean:message key="dirgallery.gallery.delete"/></erland-common:beanlink>
    </td></tr>
    <tr><td><p class="title"><bean:write name="galleryPB" property="title"/></p></td></tr>
    <tr><td><p class="normal"><erland-common:expandhtml><bean:write name="galleryPB" property="description"/></erland-common:expandhtml></p></td></tr>
    <tr><td>
    <erland-common:tablegrid name="picturesPB" property="pictures" id="picture" valign="bottom" align="center" nameRowsCols="galleryPB" colsProperty="numberOfThumbnailsPerRow" rowIterations="2" tableStyle="no-border">
        <erland-common:tablegridrow row="0">
            <erland-common:beanlink name="picture" property="updateCommentLink" style="bold-link">
                <bean:message key="dirgallery.gallery.picture.comment"/><br>
            </erland-common:beanlink>
            <logic:notEmpty name="picture" property="resolutionLink">
                <bean:define id="resolutionLink" name="picture" property="resolutionLink" type="String"/>
                <logic:iterate name="picture" property="resolutions" id="resolution">
                    <bean:define id="resolutionWidth" name="resolution" property="widthDisplay" type="String"/>
                    <bean:define id="resolutionDescription" name="resolution" property="description" type="String"/>
                    <a class="bold-link" href="<html:rewrite page="<%=resolutionLink%>"/>&width=<%=resolutionWidth%>" target="_blank" title="<%=resolutionDescription%>"><bean:write name="resolution" property="id"/></a>
                </logic:iterate>
                <br>
            </logic:notEmpty>
            <erland-common:beanlink style="bold-link" name="picture" property="link" target="_blank" propertyTitle="description">
                <erland-common:beanimage name="picture" property="image" border="0"/><br><div align="center"><bean:write name="picture" property="name"/></div>
            </erland-common:beanlink>
        </erland-common:tablegridrow>
        <erland-common:tablegridrow row="1" valign="top" align="left">
            <logic:notEmpty name="picture" property="comment">
                <div class="normal" align="left"><bean:write name="picture" property="comment"/></div>
            </logic:notEmpty>
            <logic:notEmpty name="picture" property="fileSizeText">
                <div class="normal" align="center"><bean:write name="picture" property="fileSizeText"/></div>
            </logic:notEmpty>
        </erland-common:tablegridrow>
    </erland-common:tablegrid>
    </td></tr>
    <tr><td>
    <table width="100%" class="no-border-tight">
    <tr>
    <td align="left" width="50%"><erland-common:beanlink style="bold-link" name="picturesPB" property="prevLink">&lt; <bean:message key="dirgallery.gallery.picture.previous"/></erland-common:beanlink></td>
    <td align="right" width="50%"><erland-common:beanlink style="bold-link" name="picturesPB" property="nextLink"><bean:message key="dirgallery.gallery.picture.next"/> &gt;</erland-common:beanlink></td>
    </tr>
    </table>
    </td></tr>
</table>
</logic:notEmpty>
