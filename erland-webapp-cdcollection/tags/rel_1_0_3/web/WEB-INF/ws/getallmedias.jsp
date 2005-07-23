<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<medias>
<logic:iterate name="mediasPB" id="media" >
    <media>
        <id><bean:write name="media" property="idDisplay"/></id>
        <artist><bean:write name="media" property="artist"/></artist>
        <title><bean:write name="media" property="title"/></title>
        <cover><bean:write name="media" property="coverLink"/></cover>
    </media>
</logic:iterate>
</medias>
