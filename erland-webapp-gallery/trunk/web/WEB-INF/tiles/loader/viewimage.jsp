<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="singlepicturepage-body">
    <tr>
    <td>
        <erland-common:beanimage name="picturePB" property="image" border="0"/>
    </td>
    </tr>
    <tr><td>
    <logic:notEmpty name="picturePB" property="title">
        <p class="singlepicturepage-title"><erland-common:expandhtml><bean:write name="picturePB" property="title"/></erland-common:expandhtml></p>
    </logic:notEmpty>
    <p class="singlepicturepage-description"><bean:write name="picturePB" property="description"/></p>
    </td></tr>
    <tr><td>
    <tiles:insert page="viewmetadata.jsp"/>
    </td></tr>
</table>
