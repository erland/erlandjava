<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<collection>
    <id><bean:write name="collectionPB" property="idDisplay"/></id>
    <title><bean:write name="collectionPB" property="title"/></title>
    <description><bean:write name="collectionPB" property="description"/></description>
    <medias>
    <logic:iterate id="media" name="collectionPB" property="medias">
        <media>
            <id><bean:write name="media" property="idDisplay"/></id>
            <artist><bean:write name="media" property="artist"/></artist>
            <title><bean:write name="media" property="title"/></artist>
            <cover><bean:write name="media" property="coverLink"/></cover>
        </media>
    </logic:iterate>
    </medias>
</collection>
