<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<logic:notEmpty name="applicationPB" property="logo" >
    <img src="/download/do/viewapplicationlogo?name=<bean:write name="applicationPB" property="name"/>"></img>
</logic:notEmpty>
<logic:empty name="applicationPB" property="logo">
    <p class="title"><bean:write name="applicationPB" property="title"/></p>
</logic:empty>

<p class="normal"><erland-common:expandhtml><bean:write name="applicationPB" property="description"/></erland-common:expandhtml>

<table class="no-border">
    <logic:iterate name="applicationversionsPB" id="item">
        <tr>
        <td valign="top" nowrap><bean:write name="item" property="version"/></td>
        <td width="20">&nbsp;</td>
        <td valign="top" nowrap><bean:write name="item" property="dateDisplay"/></td>
        <td width="20">&nbsp;</td>
        <td valign="top"><a class="bold-link" href="/download/do/downloadapplication?name=<bean:write name="item" property="name"/>&filename=<bean:write name="item" property="filename"/>"><bean:write name="item" property="filename"/></a><br>
        <erland-common:expandhtml><bean:write name="item" property="description"/></erland-common:expandhtml>
        </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
    </logic:iterate>
</table>
