<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table width="600" class="no-border">
    <logic:notEmpty name="galleryPB">
        <tr><td><p class="title"><bean:write name="galleryPB" property="title"/></p></td></tr>
        <tr><td><p class="normal"><erland-common:expandhtml><bean:write name="galleryPB" property="description"/></erland-common:expandhtml></p></td></tr>
        <logic:present name="picturesPB" property="searchLink">
            <bean:define id="searchLink" name="picturesPB" property="searchLink" type="String"/>
            <tr><td><a href="<html:rewrite page="<%=searchLink%>"/>" class="bold-link"><bean:message key="gallery.picture.search"/></a></td></tr>
        </logic:present>
    </logic:notEmpty>
</table>

<table width="600" class="no-border">
    <tr>
    <logic:present name="picturesPB" property="prevLink">
        <bean:define id="prevLink" name="picturesPB" property="prevLink" type="String"/>
        <td align="left"><a class="bold-link" href="<html:rewrite page="<%=prevLink%>" />">&lt; F�reg�ende</a></td>
    </logic:present>
    <logic:notPresent name="picturesPB" property="prevLink">
        <td></td>
    </logic:notPresent>
    <td></td>
    <logic:present name="picturesPB" property="nextLink">
        <bean:define id="nextLink" name="picturesPB" property="nextLink" type="String"/>
        <td align="right"><a class="bold-link" href="<html:rewrite page="<%=nextLink%>"/>">N�sta &gt;</a></td>
    </logic:present>
    <logic:notPresent name="picturesPB" property="nextLink">
        <td></td>
    </logic:notPresent>
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
        <logic:notEmpty name="picture" property="updateLink">
            <bean:define id="updateLink" name="picture" property="updateLink" type="String"/>
            <a class="bold-link" href="<html:rewrite page="<%=updateLink%>"/>"><bean:message key="gallery.gallery.picture.modify"/></a>
            <logic:notEmpty name="picture" property="removeLink">
                <bean:define id="removeLink" name="picture" property="removeLink" type="String"/>
                <a class="bold-link" href="<html:rewrite page="<%=removeLink%>"/>" onClick="return confirm('<bean:message key="gallery.gallery.picture.remove.are-you-sure"/>')"><bean:message key="gallery.gallery.picture.remove"/></a>
            </logic:notEmpty>
            <br>
        </logic:notEmpty>
        <logic:notEmpty name="picture" property="resolutionLink">
            <bean:define id="resolutionLink" name="picture" property="resolutionLink" type="String"/>
            <logic:iterate name="picture" property="resolutions" id="resolution">
                <bean:define id="resolutionWidth" name="resolution" property="widthDisplay" type="String"/>
                <bean:define id="resolutionDescription" name="resolution" property="description" type="String"/>
                <a class="bold-link" href="<html:rewrite page="<%=resolutionLink%>"/>&width=<%=resolutionWidth%>" target="_blank" title="<%=resolutionDescription%>"><bean:write name="resolution" property="id"/></a>
            </logic:iterate>
            <br>
        </logic:notEmpty>
        <bean:define id="link" name="picture" property="link" type="String"/>
        <bean:define id="image" name="picture" property="image" type="String"/>
        <a class="bold-link" href="<html:rewrite page="<%=link%>"/>" target="_blank" title="<bean:write name="picture" property="description"/>"><img src="<html:rewrite page="<%=image%>"/>" border="0"></img><br><div align="center"><bean:write name="picture" property="title"/></div></a>
        </td>
    </logic:iterate>
    </tr>
</table>
