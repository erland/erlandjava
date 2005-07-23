<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<collections>
<logic:iterate name="collectionsPB" id="item" >
    <collection>
        <id><bean:write name="item" property="idDisplay"/></id>
        <title><bean:write name="item" property="title"/></title>
        <description><bean:write name="item" property="description"/></description>
    </collection>
</logic:iterate>
</collections>
