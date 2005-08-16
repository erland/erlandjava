<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="mediapage-body">
    <tr><td colspan="2">
    <erland-common:beanlink name="collectionPB" property="updateLink" style="mediapage-button"><bean:message key="cdcollection.collection.button.edit"/></erland-common:beanlink>
    <erland-common:beanlink name="collectionPB" property="removeLink" style="mediapage-button" onClickMessageKey="cdcollection.collection.button.remove.are-you-sure"><bean:message key="cdcollection.media.button.remove"/></erland-common:beanlink>
    <erland-common:beanlink name="collectionPB" property="newMediaLink" style="mediapage-button"><bean:message key="cdcollection.collection.button.new-media"/></erland-common:beanlink>
    </td></tr>
    <tr><td colspan="2" class="mediapage-title"><bean:write name="collectionPB" property="title"/></td></tr>
    <tr><td colspan="2" class="mediapage-description"><bean:write name="collectionPB" property="description"/></td></tr>
    <logic:iterate id="media" name="collectionPB" property="medias">
        <tr>
            <td><erland-common:beanimage name="media" property="coverLink"/></td>
            <td><erland-common:beanlink name="media" property="viewLink" style="mediapage-button"><bean:write name="media" property="artist"/> : <bean:write name="media" property="title"/></erland-common:beanlink></td>
            <td><erland-common:beanlink name="media" property="removeLink" style="mediapage-button" onClickMessageKey="cdcollection.collection.button.remove-media.are-you-sure"><bean:message key="cdcollection.collection.button.remove-media"/></erland-common:beanlink></td>
        </tr>
    </logic:iterate>
<table>
