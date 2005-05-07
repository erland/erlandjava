<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table width="600" class="sectionpage-body">
    <tr><td><erland-common:beanlink name="sectionPB" property="updateLink" style="sectionpage-button"><bean:message key="homepage.section.button.edit"/></erland-common:beanlink>
    <erland-common:beanlink name="sectionPB" property="removeLink" style="sectionpage-button" onClickMessageKey="homepage.section.button.delete.are-you-sure"><bean:message key="homepage.section.button.delete"/></erland-common:beanlink></td></tr>
    <logic:notEmpty name="sectionPB" property="directLink">
        <tr><td><bean:message key="homepage.section.button.redirect"/><erland-common:beanlink name="sectionPB" property="directLink" style="sectionpage-button"><bean:write name="sectionPB" property="directLink"/></erland-common:beanlink></td></tr>
    </logic:notEmpty>
    <logic:notEmpty name="sectionPB" property="title">
        <tr><td><p class="sectionpage-title"><bean:write name="sectionPB" property="title"/></p></td></tr>
    </logic:notEmpty>
    <logic:notEmpty name="sectionPB" property="description">
        <tr><td><p class="sectionpage-description"><erland-common:expandhtml><bean:write name="sectionPB" property="description"/></erland-common:expandhtml></p></td></tr>
    </logic:notEmpty>
    <logic:notEmpty name="sectionPB" property="serviceResult">
        <tr><td><bean:write filter="false" name="sectionPB" property="serviceResult"/></td></tr>
    </logic:notEmpty>
</table>
