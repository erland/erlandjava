<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="tvguide.programs.search.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="programs.search" target="_blank"><bean:message key="tvguide.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/searchprogramsadvanced" method="POST">
    <table class="propertypage-body">
    <tr><td><bean:message key="tvguide.programs.search.name"/></td><td>
    <html:text property="name"/>
    </td></tr>
    <tr><td><bean:message key="tvguide.programs.search.credit"/></td><td>
    <html:text property="credit"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="tvguide.buttons.search"/></html:submit>
    </td></tr>
</html:form>
