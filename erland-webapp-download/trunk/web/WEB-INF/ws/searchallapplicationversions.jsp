<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<applications>
<logic:iterate name="applicationversionsPB" id="item" >
    <application>
        <name><bean:write name="item" property="name"/></name>
        <title><bean:write name="item" property="title"/></title>
        <version><bean:write name="item" property="version"/></version>
        <date><bean:write name="item" property="dateDisplay"/></date>
        <url><bean:write name="item" property="applicationLink"/></url>
        <files>
        <logic:iterate name="item" property="files" id="file">
            <file>
                <name><bean:write name="file" property="filename"/></name>
                <url><bean:write name="file" property="url"/></url>
            </file>
        </logic:iterate>
        </files>
        <description><bean:write name="item" property="description"/></description>
    </application>
</logic:iterate>
</applications>