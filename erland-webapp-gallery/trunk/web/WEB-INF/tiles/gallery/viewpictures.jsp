<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table width="600" class="no-border">
    <logic:notEmpty name="galleryPB">
        <tr><td><p class="title"><bean:write name="galleryPB" property="title"/></p></td></tr>
        <tr><td><p class="normal"><erland-common:expandhtml><bean:write name="galleryPB" property="description"/></erland-common:expandhtml></p></td></tr>
        <tr>
        <td>
        <erland-common:beanlink style="bold-link" name="picturesPB" property="searchLink">
        <bean:message key="gallery.picture.search"/>
        </erland-common:beanlink>
        </td>
        </tr>
        </logic:present>
    </logic:notEmpty>
</table>

<table width="600" class="no-border">
    <tr>
    <td align="left"><erland-common:beanlink style="bold-link" name="picturesPB" property="prevLink">&lt; <bean:message key="gallery.gallery.picture.previous"/></erland-common:beanlink></td>
    <td></td>
    <td align="right"><erland-common:beanlink style="bold-link" name="picturesPB" property="nextLink"><bean:message key="gallery.gallery.picture.next"/> &gt;</erland-common:beanlink></td>
    </tr>


    <logic:iterate name="picturesPB" property="pictures" id="picture" indexId="picNo" >
        <bean:define id="modPicNo" value="<%=String.valueOf(picNo.intValue()%3)%>" type="String"/>
        <logic:equal name="modPicNo" value="0">
            <logic:notEqual name="picNo" value="0">
                </tr>
            </logic:notEqual>
            <tr>
        </logic:equal>
        <td align="center">
        <erland-common:beanlink style="bold-link" name="picture" property="updateLink"><bean:message key="gallery.gallery.picture.modify"/></erland-common:beanlink>
        <erland-common:beanlink style="bold-link" name="picture" property="removeLink" onClickMessageKey="gallery.gallery.picture.remove.are-you-sure"><bean:message key="gallery.gallery.picture.remove"/><br></erland-common:beanlink>
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
            <erland-common:beanimage name="picture" property="image" border="0"/><br><div align="center"><bean:write name="picture" property="title"/></div>
        </erland-common:beanlink>
        </td>
    </logic:iterate>
    </tr>
</table>
