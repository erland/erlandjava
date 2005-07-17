<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="tvguide.exclusion.edit.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="exclusion.edit" target="_blank"><bean:message key="tvguide.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editexclusion" method="POST">
    <logic:notEmpty name="editExclusionFB" property="idDisplay">
        <html:hidden property="idDisplay"/>
    </logic:notEmpty>
    <table class="propertypage-body">
    <tr><td><bean:message key="tvguide.exclusion.edit.name"/></td><td>
    <html:text property="name"/>
    </td></tr>
    <tr><td><bean:message key="tvguide.exclusion.edit.type"/></td><td>
        <html:select property="typeDisplay" size="1">
            <html:option value="0" key="tvguide.exclusion.edit.type.0"/>
            <html:option value="1" key="tvguide.exclusion.edit.type.1"/>
        </html:select>
    </td></tr>
    <tr><td><bean:message key="tvguide.exclusion.edit.pattern"/></td><td>
    <html:text property="pattern"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="tvguide.buttons.save"/></html:submit>
    </td></tr>
</html:form>
