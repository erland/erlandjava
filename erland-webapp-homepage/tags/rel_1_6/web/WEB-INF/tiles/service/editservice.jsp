<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="homepage.service.edit.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="service.edit" target="_blank"><bean:message key="homepage.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editservice" method="POST">
    <logic:notEmpty name="editServiceFB" property="idDisplay">
        <html:hidden property="idDisplay"/>
    </logic:notEmpty>
    <table class="propertypage-body">
    <tr><td><bean:message key="homepage.service.edit.name"/></td><td>
    <html:text property="name"/>
    </td></tr>
    <tr><td><bean:message key="homepage.service.edit.name-english"/></td><td>
    <html:text property="nameEnglish"/>
    </td></tr>
    <tr><td><bean:message key="homepage.service.edit.service-class"/></td><td>
    <html:text property="serviceClass"/>
    </td></tr>
    <tr><td><bean:message key="homepage.service.edit.service-data"/></td><td>
    <html:text property="serviceData"/>
    </td></tr>
    <tr><td><bean:message key="homepage.service.edit.customized-service-data"/></td><td>
    <html:text property="customizedServiceData"/>
    </td></tr>
    <tr><td><bean:message key="homepage.service.edit.transformer-class"/></td><td>
    <html:text property="transformerClass"/>
    </td></tr>
    <tr><td><bean:message key="homepage.service.edit.transformer-data"/></td><td>
    <html:text property="transformerData"/>
    </td></tr>
    <tr><td><bean:message key="homepage.service.edit.customized-transformer-data"/></td><td>
    <html:text property="customizedTransformerData"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="homepage.buttons.save"/></html:submit>
    </td></tr>
    </table>
</html:form>
