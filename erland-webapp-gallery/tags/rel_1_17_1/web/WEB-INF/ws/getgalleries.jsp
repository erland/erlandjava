<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<galleries>
    <logic:iterate id="gallery" name="galleriesPB">
        <gallery>
            <id><bean:write name="gallery" property="idDisplay"/></id>
            <title><bean:write name="gallery" property="title"/></title>
            <description><bean:write name="gallery" property="description"/></description>
        </gallery>
    </logic:iterate>
</galleries>
