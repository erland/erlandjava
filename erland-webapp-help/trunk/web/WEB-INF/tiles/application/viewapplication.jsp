<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:beanlink name="applicationPB" property="updateLink" style="propertypage-button"><bean:message key="help.application.edit.button"/></erland-common:beanlink>
<erland-common:beanlink name="applicationPB" property="removeLink" style="propertypage-button"  onClickMessageKey="help.application.remove.button.are-you-sure"> - <bean:message key="help.application.remove.button"/></erland-common:beanlink>
<table class="propertypage-body">
<tr><td><bean:message key="help.application.edit.name"/></td><td>
<bean:write name="applicationPB" property="name"/>
</td></tr>
<tr><td><bean:message key="help.application.edit.title-english"/></td><td>
<bean:write name="applicationPB" property="titleEnglish"/>
</td></tr>
<tr><td><bean:message key="help.application.edit.title-native"/></td><td>
<bean:write name="applicationPB" property="titleNative"/>
</td></tr>
</table>
