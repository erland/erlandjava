<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<pictures>
    <logic:iterate id="picture" name="picturesPB" property="pictures">
        <picture>
            <id><bean:write name="picture" property="idDisplay"/></id>
            <title><bean:write name="picture" property="title"/></title>
            <description><bean:write name="picture" property="description"/></description>
            <thumbnail><bean:write name="picture" property="image"/></thumbnail>
            <image><bean:write name="picture" property="link"/></image>
        </picture>
    </logic:iterate>
</pictures>
