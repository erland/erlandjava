<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<logic:notEmpty name="applicationsPB">
<table class="no-border" align="left" valign="top">
    <logic:iterate name="applicationsPB" id="item">
    	<tr><td nowrap>
        <a class="bold-link" href="/download/do/viewapplication?name=<bean:write name="item" property="name"/>">
            <bean:write name="item" property="title"/>
        </a>
    	</td></tr>
    </logic:iterate>
</table>
</logic:notEmpty>