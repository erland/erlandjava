<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<entries>
<logic:iterate name="collectionsPB" id="collection" >
    <collection>
        <id><bean:write name="collection" property="idDisplay"/></id>
        <username><bean:write name="collection" property="username"/></username>
        <title><bean:write name="collection" property="title"/></title>
        <description><bean:write name="collection" property="description"/></description>
        <entries>
        <logic:iterate name="collection" property="entries" id="entry" >
            <entry>
                <id><bean:write name="entry" property="idDisplay"/></id>
                <uniqueid><bean:write name="entry" property="uniqueEntryId"/></uniqueid>
                <logic:notEmpty name="entry" property="lastChanged">
                <lastchanged><bean:write name="entry" property="lastChangedDisplay"/></lastchanged>
                </logic:notEmpty>
                <title><bean:write name="entry" property="title"/></title>
                <description><bean:write name="entry" property="description"/></description>
            </entry>
        </logic:iterate>
        </entries>
    </collection>
</logic:iterate>
</entries>
