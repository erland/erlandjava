<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<table class="no-border" align="center">
    <logic:iterate name="applicationsPB" id="item">
        <tr><td>
        <a class="bold-link" href="/download/do/viewapplication?name=<bean:write name="item" property="name"/>">
            <logic:notEmpty name="item" property="logo" >
                <img src="/download/do/viewapplicationlogo?name=<bean:write name="item" property="name"/>" border="0"></img>
            </logic:notEmpty>
            <logic:empty name="item" property="logo">
                <p class="bold"><bean:write name="item" property="title"/></p>
            </logic:empty>
        </a>
        </td></tr>
    </logic:iterate>
</table>