<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table border="0" class="no-border">
    <tr>
    <td>
        <erland-common:beanimage name="picturePB" property="image" border="0"/>
    </td>
    </tr>
    <tr><td>
    <p class="title"><bean:write name="picturePB" property="name"/></p>
    <logic:notEmpty name="picturePB" property="comment">
        <p class="normal"><bean:write name="picturePB" property="comment"/></p>
    </logic:notEmpty>
    </td></tr>
    <tr><td>
    <jsp:include page="viewmetadata.jsp"/>
    </td></tr>
</table>
