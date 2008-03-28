<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="datacollection.collection.edit.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="collection.edit" target="_blank"><bean:message key="datacollection.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editcollection" method="POST">
    <table class="propertypage-body">
    <html:hidden property="username"/>
    <html:hidden property="idDisplay"/>
    <tr><td><bean:message key="datacollection.collection.edit.application"/></td><td>
    <html:text property="application" size="60"/>
    </td></tr>
    <tr><td><bean:message key="datacollection.collection.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="5"/>
    </td></tr>
    <tr><td><bean:message key="datacollection.collection.edit.title"/></td><td>
    <html:text property="title" size="60"/>
    </td></tr>
    <tr><td><bean:message key="datacollection.collection.edit.official"/></td><td>
    <html:checkbox property="officialDisplay" value="true"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="datacollection.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
