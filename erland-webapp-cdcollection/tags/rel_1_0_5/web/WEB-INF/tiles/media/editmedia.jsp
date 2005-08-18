<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="cdcollection.media.edit.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="media.edit" target="_blank"><bean:message key="cdcollection.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editmedia" method="POST">
    <table class="propertypage-body">
    <html:hidden property="idDisplay"/>
    <tr><td><bean:message key="cdcollection.media.edit.title"/></td><td>
    <html:text property="title" size="60"/>
    </td></tr>
    <tr><td><bean:message key="cdcollection.media.edit.artist"/></td><td>
    <html:text property="artist"/>
    </td></tr>
    <tr><td><bean:message key="cdcollection.media.edit.unique-media-id"/></td><td>
    <html:text property="uniqueMediaId"/>
    </td></tr>
    <tr><td><bean:message key="cdcollection.media.edit.year"/></td><td>
    <html:text property="year"/>
    </td></tr>
    <tr><td><bean:message key="cdcollection.media.edit.cover-url"/></td><td>
    <html:text property="coverUrl"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="cdcollection.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
