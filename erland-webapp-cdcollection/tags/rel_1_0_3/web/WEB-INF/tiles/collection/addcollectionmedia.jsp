<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="mediapage-body">
    <logic:iterate id="media" name="mediasPB">
        <tr>
            <td><erland-common:beanimage name="media" property="coverLink"/></td>
            <td><erland-common:beanlink name="media" property="viewLink" style="mediapage-button"><bean:write name="media" property="artist"/> : <bean:write name="media" property="title"/></erland-common:beanlink></td>
        </tr>
    </logic:iterate>
<table>
