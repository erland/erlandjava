<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:beanlink name="chapterPB" property="updateLink" style="propertypage-button"><bean:message key="help.chapter.edit.button"/></erland-common:beanlink>
<erland-common:beanlink name="chapterPB" property="removeLink" style="propertypage-button"  onClickMessageKey="help.chapter.remove.button.are-you-sure"> - <bean:message key="help.chapter.remove.button"/></erland-common:beanlink>
<erland-common:beanlink name="chapterPB" property="newAttributeLink" style="propertypage-button"> - <bean:message key="help.chapter.attribute.new.button"/></erland-common:beanlink>
<div class="helppage-title"><bean:write name="chapterPB" property="title"/></div><br>
<table class="helppage">
<logic:notEmpty name="chapterPB" property="header">
    <tr class="helppage-header-row"><td class="helppage-header-cell">
    <erland-common:expandhtml><bean:write name="chapterPB" property="header"/></erland-common:expandhtml>
    </td></tr>
</logic:notEmpty>
<tr class="helppage-body-row"><td class="helppage-body-cell">
<table class="helppage-body">
<logic:iterate name="chapterPB" property="attributes" id="attribute">
    <tr class="helppage-attribute-row">
    <td valign="top" class="helppage-attribute-name"><bean:write name="attribute" property="name"/></td>
    <td valign="top" class="helppage-attribute-description"><erland-common:expandhtml><bean:write name="attribute" property="description"/></erland-common:expandhtml></td>
    <logic:notEmpty name="attribute" property="updateLink">
        <td valign="top" class="helppage-attribute-button-cell"><erland-common:beanlink name="attribute" property="updateLink" style="helppage-button"><bean:message key="help.chapter.attribute.edit.button"/></erland-common:beanlink></td>
    </logic:notEmpty>
    <logic:notEmpty name="attribute" property="removeLink">
        <td valign="top" class="helppage-attribute-button-cell"><erland-common:beanlink name="attribute" property="removeLink" style="helppage-button" onClickMessageKey="help.chapter.attribute.remove.button.are-you-sure"><bean:message key="help.chapter.attribute.remove.button"/></erland-common:beanlink></td>
    </logic:notEmpty>
    </tr>
</logic:iterate>
</table>
</td></tr>
<logic:notEmpty name="chapterPB" property="footer">
    <tr class="helppage-footer-row"><td class="helppage-footer-cell">
    <erland-common:expandhtml><bean:write name="chapterPB" property="footer"/></erland-common:expandhtml>
    </td></tr>
</logic:notEmpty>
