<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<p class="propertypage-title"><bean:message key="download.latest-releases"/></p>

<table class="propertypage-body" align="center">
    <logic:iterate name="applicationversionsPB" id="item" length="10" >
        <tr>
        <td valign="top" nowrap><a class="propertypage-button" href="<%=request.getContextPath()%>/do/viewapplication?name=<bean:write name="item" property="name"/>"><bean:write name="item" property="title"/></a> <bean:write name="item" property="version"/></td>
        <td width="20">&nbsp;</td>
        <td valign="top" nowrap><bean:write name="item" property="dateDisplay"/></td>
        <td width="20">&nbsp;</td>
        <td valign="top">
        <logic:iterate name="item" property="files" id="file">
        <a class="propertypage-button" href="<bean:write name="file" property="url"/>"><bean:write name="file" property="filename"/></a>&nbsp;&nbsp;&nbsp;
        </logic:iterate>
        <br>
        <erland-common:expandhtml><bean:write name="item" property="description"/></erland-common:expandhtml>
        </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
    </logic:iterate>
</table>
