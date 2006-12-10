<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<collection>
    <id><bean:write name="collectionPB" property="idDisplay"/></id>
    <title><bean:write name="collectionPB" property="title"/></title>
    <description><bean:write name="collectionPB" property="description"/></description>
    <entries>
    <logic:iterate id="entry" name="collectionPB" property="entries">
        <entry>
            <id><bean:write name="entry" property="idDisplay"/></id>
            <title><bean:write name="entry" property="title"/></title>
            <description><bean:write name="entry" property="description"/></description>
        </entry>
    </logic:iterate>
    </entries>
</collection>
