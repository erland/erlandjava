<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:beanlink name="applicationVersionPB" property="updateLink" style="propertypage-button"><bean:message key="help.application.version.edit.button"/></erland-common:beanlink>
<erland-common:beanlink name="applicationVersionPB" property="removeLink" style="propertypage-button"  onClickMessageKey="help.application.version.remove.button.are-you-sure"> - <bean:message key="help.application.version.remove.button"/></erland-common:beanlink>
<table class="propertypage-body">
<tr><td><bean:message key="help.application.version.edit.name"/></td><td>
<bean:write name="applicationVersionPB" property="application"/>
</td></tr>
<tr><td><bean:message key="help.application.version.edit.version"/></td><td>
<bean:write name="applicationVersionPB" property="version"/>
</td></tr>
<tr><td><bean:message key="help.application.version.edit.orderno"/></td><td>
<bean:write name="applicationVersionPB" property="orderNoDisplay"/>
</td></tr>
</table>
