<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="homepage.section.edit.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="section.edit" target="_blank"><bean:message key="homepage.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editsection" method="POST">
    <logic:notEmpty name="editSectionFB" property="idDisplay">
        <html:hidden property="idDisplay"/>
    </logic:notEmpty>
    <logic:notEmpty name="editSectionFB" property="parentDisplay">
        <html:hidden property="parentDisplay"/>
    </logic:notEmpty>
    <table class="propertypage-body">
    <tr><td><bean:message key="homepage.section.edit.name"/></td><td>
    <html:text property="name"/>
    </td></tr>
    <tr><td><bean:message key="homepage.section.edit.name-english"/></td><td>
    <html:text property="nameEnglish"/>
    </td></tr>
    <tr><td><bean:message key="homepage.section.edit.title"/></td><td>
    <html:text property="title"/> 
    </td></tr>
    <tr><td><bean:message key="homepage.section.edit.title-english"/></td><td>
    <html:text property="titleEnglish"/>
    </td></tr>
    <tr><td><bean:message key="homepage.section.edit.directlink"/></td><td>
    <html:text property="directLink"/>
    </td></tr>
    <tr><td><bean:message key="homepage.section.edit.directlink-english"/></td><td>
    <html:text property="directLinkEnglish"/>
    </td></tr>
    <tr><td><bean:message key="homepage.section.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="15" />
    </td></tr>
    <tr><td><bean:message key="homepage.section.edit.description-english"/></td><td>
    <html:textarea property="descriptionEnglish" cols="80" rows="15" />
    </td></tr>
    <tr><td><bean:message key="homepage.section.edit.service"/></td><td>
    <html:select property="serviceDisplay" size="1">
        <html:option value="" key="homepage.section.edit.service.none"/>
        <html:options collection="servicesPB" property="idDisplay" labelProperty="name" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="homepage.section.edit.service-parameters"/></td><td>
    <html:text property="serviceParameters"/>
    </td></tr>
    <tr><td><bean:message key="homepage.section.edit.order-no"/></td><td>
    <html:text property="orderNoDisplay"/>
    </td></tr>
    <tr><td><bean:message key="homepage.section.edit.ipaddr"/></td><td>
    <html:text property="ipAddr"/>
    </td></tr>
    <tr><td><bean:message key="homepage.section.edit.hosts"/></td><td>
    <html:text property="hosts"/>
    </td></tr>
    <tr><td><bean:message key="homepage.section.edit.official"/></td><td>
    <html:checkbox property="officialDisplay" value="true"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="homepage.buttons.save"/></html:submit>
    </td></tr>
    </table>
</html:form>
