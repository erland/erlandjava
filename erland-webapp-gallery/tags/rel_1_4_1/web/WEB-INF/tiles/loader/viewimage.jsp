<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table border="0">
    <tr>
    <td>
        <bean:define id="image" name="picturePB" property="image" type="String"/>
        <img src="<html:rewrite page="<%=image%>"/>" border="0"></img>
    </td>
    </tr>
    <tr><td>
    <p class="title"><bean:write name="picturePB" property="title"/></p>
    <p class="normal"><bean:write name="picturePB" property="description"/></p>
    </td></tr>
    <tr><td>
    <jsp:include page="viewmetadata.jsp"/>
    </td></tr>
</table>
