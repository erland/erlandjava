<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<media>
    <id><bean:write name="mediaPB" property="idDisplay"/></id>
    <artist><bean:write name="mediaPB" property="artist"/></artist>
    <title><bean:write name="mediaPB" property="title"/></title>
    <cover><bean:write name="mediaPB" property="coverLink"/></cover>
    <discs>
    <logic:iterate id="disc" name="mediaPB" property="discs">
        <disc>
            <id><bean:write name="disc" property="idDisplay"/></id>
            <logic:notEmpty name="disc" property="artist">
                <artist><bean:write name="disc" property="artist"/></artist>
            </logic:notEmpty>
            <logic:notEmpty name="disc" property="title">
                <title><bean:write name="disc" property="title"/></title>
            </logic:notEmpty>
            <tracks>
            <logic:iterate id="track" name="disc" property="tracks">
                <track>
                    <no><bean:write name="track" property="trackNoDisplay"/></no>
                    <logic:notEmpty name="disc" property="title">
                        <artist><bean:write name="track" property="artist"/></artist>
                    </logic:notEmpty>
                    <title><bean:write name="track" property="title"/></title>
                    <length><bean:write name="track" property="lengthDisplay"/></length>
                    <lengthInMinutes><bean:write name="track" property="lengthInMinutes"/></lengthInMinutes>
                </track>
            </logic:iterate>
            </tracks>
        </disc>
    </logic:iterate>
    </discs>
</media>
