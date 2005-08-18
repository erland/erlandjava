<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="cdcollection.media.disc.import.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="media.disc.import" target="_blank"><bean:message key="cdcollection.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/importdisc" method="POST">
    <table class="propertypage-body">
    <html:hidden property="mediaIdDisplay"/>
    <tr><td><bean:message key="cdcollection.media.disc.import.category"/></td><td>
    <html:text property="category"/>
    </td></tr>
    <tr><td><bean:message key="cdcollection.media.disc.import.disc-id"/></td><td>
    <html:text property="discId"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="cdcollection.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
