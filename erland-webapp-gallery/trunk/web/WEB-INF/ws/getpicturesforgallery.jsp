<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<gallery>
    <title><bean:write name="galleryPB" property="title"/></title>
    <description><bean:write name="galleryPB" property="description"/></description>
    <link><bean:write name="picturesPB" property="pageLink"/></link>
    <pictures>
    <logic:iterate id="picture" name="picturesPB" property="pictures">
        <picture>
            <id><bean:write name="picture" property="idDisplay"/></id>
            <title><bean:write name="picture" property="title"/></title>
            <description><bean:write name="picture" property="description"/></description>
            <thumbnail><bean:write name="picture" property="image"/></thumbnail>
            <image><bean:write name="picture" property="link"/></image>
            <link><bean:write name="picture" property="currentLink"/></link>
        </picture>
    </logic:iterate>
    </pictures>
</gallery>
