<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table border="0" class="singlepicturepage-body">
    <tr>
    <td>
        <erland-common:beanlink name="picturePB" property="pathSmallImage" style="singlepicturepage-button" target="_blank"><bean:message key="dirgallery.image.small-image-link"/></erland-common:beanlink>
        &nbsp;&nbsp;&nbsp;
        <erland-common:beanlink name="picturePB" property="pathLargeImage" style="singlepicturepage-button" target="_blank"><bean:message key="dirgallery.image.large-image-link"/></erland-common:beanlink>
    </td>
    </tr>
    <tr>
    <td>
        <erland-common:beanimage name="picturePB" property="image" border="0"/>
    </td>
    </tr>
    <tr><td>
    <p class="singlepicturepage-title"><bean:write name="picturePB" property="name"/></p>
    <logic:notEmpty name="picturePB" property="comment">
        <p class="singlepicturepage-description"><bean:write name="picturePB" property="comment"/></p>
    </logic:notEmpty>
    </td></tr>
    <tr><td>
    <tiles:insert page="/WEB-INF/tiles/loader/viewmetadata.jsp"/>
    </td></tr>
</table>
