<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="tvguide.channel.edit.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="channel.edit" target="_blank"><bean:message key="tvguide.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editchannel" method="POST">
    <logic:notEmpty name="editChannelFB" property="idDisplay">
        <html:hidden property="idDisplay"/>
    </logic:notEmpty>
    <table class="propertypage-body">
    <tr><td><bean:message key="tvguide.channel.edit.tag"/></td><td>
    <html:text property="tag"/>
    </td></tr>
    <tr><td><bean:message key="tvguide.channel.edit.name"/></td><td>
    <html:text property="name"/>
    </td></tr>
    <tr><td><bean:message key="tvguide.channel.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="15" />
    </td></tr>
    <tr><td><bean:message key="tvguide.channel.edit.link"/></td><td>
    <html:text property="link" size="80"/>
    </td></tr>
    <tr><td><bean:message key="tvguide.channel.edit.logo"/></td><td>
    <html:text property="logo" size="80"/>
    </td></tr>
    <tr><td><bean:message key="tvguide.channel.edit.service"/></td><td>
    <html:select property="serviceDisplay" size="1">
        <html:option value="" key="tvguide.channel.edit.service.none"/>
        <html:options collection="servicesPB" property="idDisplay" labelProperty="name" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="tvguide.channel.edit.serviceparameters"/></td><td>
    <html:text property="serviceParameters" size="80"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="tvguide.buttons.save"/></html:submit>
    </td></tr>
</html:form>
