<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<logic:notEmpty name="applicationsPB">
    <logic:iterate name="applicationsPB" id="item">
        <a class="bold-link" href="/download/do/viewapplication?name=<bean:write name="item" property="name"/>">
            <bean:write name="item" property="title"/>
        </a><br>
    </logic:iterate>
</logic:notEmpty>