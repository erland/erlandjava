<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="mediapage-body">
    <tr><td colspan="4">
    <erland-common:beanlink name="mediaPB" property="updateLink" style="mediapage-button"><bean:message key="cdcollection.media.button.edit"/></erland-common:beanlink>
    <erland-common:beanlink name="mediaPB" property="removeLink" style="mediapage-button" onClickMessageKey="cdcollection.media.button.remove.are-you-sure"><bean:message key="cdcollection.media.button.remove"/></erland-common:beanlink>
    <erland-common:beanlink name="mediaPB" property="importLink" style="mediapage-button"><bean:message key="cdcollection.media.button.import-media"/></erland-common:beanlink>
    <erland-common:beanlink name="mediaPB" property="newDiscLink" style="mediapage-button"><bean:message key="cdcollection.media.button.new-disc"/></erland-common:beanlink>
    <erland-common:beanlink name="mediaPB" property="importDiscLink" style="mediapage-button"><bean:message key="cdcollection.media.button.import-disc"/></erland-common:beanlink>
    </td></tr>
    <tr><td class="mediapage-mediatitle" colspan="4" nowrap><erland-common:beanimage name="mediaPB" property="coverLink"/><bean:write name="mediaPB" property="artist"/> : <bean:write name="mediaPB" property="title"/></td></tr>
    <tr><td>&nbsp;</td></tr>
    <logic:iterate id="disc" name="mediaPB" property="discs">
        <tr><td class="mediapage-disctitle" colspan="3" nowrap><bean:write name="disc" property="artist"/> <bean:write name="disc" property="title"/></td><td><erland-common:beanlink name="disc" property="updateLink" style="mediapage-button"><bean:message key="cdcollection.media.button.update-disc"/></erland-common:beanlink> <erland-common:beanlink name="disc" property="removeLink" style="mediapage-button" onClickMessageKey="cdcollection.media.button.remove-disc.are-you-sure"><bean:message key="cdcollection.media.button.remove-disc"/></erland-common:beanlink> <erland-common:beanlink name="disc" property="newTrackLink" style="mediapage-button"><bean:message key="cdcollection.media.button.new-track"/></erland-common:beanlink></td></tr>
        <logic:iterate id="track" name="disc" property="tracks">
            <tr class="mediapage-disctrackentry"><td><bean:write name="track" property="artist"/></td><td nowrap><bean:write name="track" property="title"/></td><td><bean:write name="track" property="lengthInMinutes"/></td><td><erland-common:beanlink name="track" property="updateLink" style="mediapage-button"><bean:message key="cdcollection.media.button.update-track"/></erland-common:beanlink> <erland-common:beanlink name="track" property="removeLink" style="mediapage-button" onClickMessageKey="cdcollection.media.button.remove-track.are-you-sure"><bean:message key="cdcollection.media.button.remove-track"/></erland-common:beanlink></td></tr>
        </logic:iterate>
        <tr><td>&nbsp;</td></tr>
    </logic:iterate>
<table>
