<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<entry>
    <id><bean:write name="entryPB" property="idDisplay"/></id>
    <uniqueid><bean:write name="entryPB" property="uniqueEntryId"/></uniqueid>
    <title><bean:write name="entryPB" property="title"/></title>
    <description><bean:write name="entryPB" property="description"/></description>
    <logic:notEmpty name="entryPB" property="lastChanged">
    <lastchanged><bean:write name="entryPB" property="lastChangedDisplay"/></lastchanged>
    </logic:notEmpty>
    <datas>
    <logic:iterate id="data" name="entryPB" property="datas">
        <data>
            <id><bean:write name="data" property="idDisplay"/></id>
            <type><bean:write name="data" property="type"/></type>
            <content><bean:write name="data" property="content"/></content>
        </data>
    </logic:iterate>
    </datas>
</entry>
