<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<logic:notEmpty name="applicationPB" property="logo" >
    <img src="<%=request.getContextPath()%>/do/viewapplicationlogo?name=<bean:write name="applicationPB" property="name"/>"></img>
</logic:notEmpty>
<logic:empty name="applicationPB" property="logo">
    <p class="propertypage-title"><bean:write name="applicationPB" property="title"/></p>
</logic:empty>

<p class="propertypage-description"><erland-common:expandhtml><bean:write name="applicationPB" property="description"/></erland-common:expandhtml>

<table class="propertypage-body">
    <logic:iterate name="applicationversionsPB" id="item">
        <tr>
        <td valign="top" nowrap><bean:write name="item" property="version"/></td>
        <td width="20">&nbsp;</td>
        <td valign="top" nowrap><bean:write name="item" property="dateDisplay"/></td>
        <td width="20">&nbsp;</td>
        <td valign="top">
        <logic:iterate name="item" property="files" id="file">
        <erland-common:beanlink style="propertypage-button" name="file" property="url"><bean:write name="file" property="filename"/></erland-common:beanlink>&nbsp;&nbsp;&nbsp;
        </logic:iterate>
        <br>
        <erland-common:expandhtml><bean:write name="item" property="description"/></erland-common:expandhtml>
        </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
    </logic:iterate>
</table>
