<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<table class="containerpage-body">
<tr>
<td valign="top">
<tiles:insert page="/WEB-INF/tiles/container/viewcontainerinfo.jsp"/>
</td>
<td valign="top">
<tiles:insert page="/WEB-INF/tiles/species/viewspeciesentryinfo.jsp"/>
</td></tr>
</table>
