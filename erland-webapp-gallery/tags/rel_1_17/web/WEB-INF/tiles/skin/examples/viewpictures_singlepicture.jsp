<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table width="600" class="picturepage-header">
    <tr><td><p class="picturepage-title"><bean:write name="galleryPB" property="title"/></p></td></tr>
    <tr><td><p class="picturepage-description"><erland-common:expandhtml><bean:write name="galleryPB" property="description"/></erland-common:expandhtml></p></td></tr>
    <tr>
    <td>
    <erland-common:beanlink style="picturepage-button" name="picturesPB" property="searchLink" parameters="max=">
    <bean:message key="gallery.picture.search"/>
    </erland-common:beanlink>
    </td>
    </tr>
</table>

<table width="600" class="picturepage-body">
    <tr class="picturepage-navigation-row">
    <td class="picturepage-navigation-cell" width="33%" align="left"><erland-common:beanlink style="picturepage-button" name="picturesPB" property="prevLink">&lt; <bean:message key="gallery.gallery.picture.previous"/></erland-common:beanlink></td>
    <%
        if(request.getParameter("thumbnailwidth")==null||request.getParameter("thumbnailwidth").length()==0) {
            %>
            <td class="picturepage-navigation-cell" width="33%" align="center">&nbsp;</td>
            <%
        }else {
            %>
            <td class="picturepage-navigation-cell" width="33%" align="center"><erland-common:beanlink style="picturepage-button" name="picturesPB" property="currentLink" parameters="max="><bean:message key="gallery.gallery.picture.up"/></erland-common:beanlink></td>
            <%
        }
    %>
    <td class="picturepage-navigation-cell" width="33%" align="right"><erland-common:beanlink style="picturepage-button" name="picturesPB" property="nextLink"><bean:message key="gallery.gallery.picture.next"/> &gt;</erland-common:beanlink></td>
    </tr>
    <tr class="picturepage-pictures-row"><td class="picturepage-pictures-cell" width="100%" colspan="3" valign="top">
    <erland-common:tablegrid name="picturesPB" property="pictures" id="picture" valign="top" align="center" nameRowsCols="galleryPB" colsProperty="noOfCols" rowsProperty="noOfRows" tableStyle="picturepage-pictures-body" cellStyle="picturepage-picture-cell" rowStyle="picturepage-picture-row"  width="100%">
        <erland-common:tablegridrow row="0" valign="bottom">
            <erland-common:beanlink style="picturepage-button" name="picture" property="updateLink"><bean:message key="gallery.gallery.picture.modify"/></erland-common:beanlink>
            <erland-common:beanlink style="picturepage-button" name="picture" property="removeLink" onClickMessageKey="gallery.gallery.picture.remove.are-you-sure"><bean:message key="gallery.gallery.picture.remove"/><br></erland-common:beanlink>
            <logic:notEmpty name="picture" property="resolutionLinks">
                <logic:iterate name="picture" property="resolutionLinks" id="resolution">
                    <erland-common:beanlink style="picturepage-resolution" name="resolution" property="link" target="_blank" propertyTitle="description"><bean:write name="resolution" property="name"/></erland-common:beanlink>
                </logic:iterate>
                <br>
            </logic:notEmpty>
            <%
                if(request.getParameter("thumbnailwidth")==null||request.getParameter("thumbnailwidth").length()==0) {
            %>
                <erland-common:beanlink style="picturepage-picture-link" name="picture" property="currentLink" propertyTitle="description" parameters="thumbnailwidth=500&thumbnailheight=500&max=1">
                    <erland-common:beanimage style="picturepage-picture-thumbnail" name="picture" property="image" border="0"/>
                    <logic:notEmpty name="picture" property="title">
                        <div class="picturepage-picture-title" align="center"><erland-common:expandhtml><bean:write name="picture" property="title"/></erland-common:expandhtml></div>
                    </logic:notEmpty>
                </erland-common:beanlink>
            <%
                }else {
            %>
                <erland-common:beanlink style="picturepage-picture-link" name="picture" property="link" target="_blank" propertyTitle="description">
                    <erland-common:beanimage style="picturepage-picture-thumbnail" name="picture" property="image" border="0"/>
                    <logic:notEmpty name="picture" property="title">
                        <div class="picturepage-picture-title" align="center"><erland-common:expandhtml><bean:write name="picture" property="title"/></erland-common:expandhtml></div>
                    </logic:notEmpty>
                </erland-common:beanlink>
            <%
                }
            %>
        </erland-common:tablegridrow>
        <erland-common:tablegridrow row="1" valign="top" >
            <div class="picturepage-picture-info-row1" align="center" valign="top"><erland-common:expandhtml><bean:write name="picture" property="row1Info"/></erland-common:expandhtml></div>
        </erland-common:tablegridrow>
        <erland-common:tablegridrow row="2" valign="top">
            <div class="picturepage-picture-info-row2" align="center" valign="top"><erland-common:expandhtml><bean:write name="picture" property="row2Info"/></erland-common:expandhtml></div>
        </erland-common:tablegridrow>
        <erland-common:tablegridrow row="3" valign="bottom">
            <div class="picturepage-picture-info-row3" align="center" valign="top"><erland-common:expandhtml><bean:write name="picture" property="row3Info"/></erland-common:expandhtml></div>
        </erland-common:tablegridrow>
    </erland-common:tablegrid>
    </td></tr>
</table>
