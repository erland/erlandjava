<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table border="0">
    <tr>
    <td>
        <erland-common:beanimage name="picturePB" property="image" border="0"/>
    </td>
    </tr>
    <tr><td>
    <p class="title"><bean:write name="picturePB" property="title"/></p>
    <p class="normal"><bean:write name="picturePB" property="description"/></p>
    </td></tr>
    <tr><td>
    <tiles:insert page="viewmetadata.jsp"/>
    </td></tr>
</table>
