<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="cdcollection.media.disc.edit.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="media.disc.edit" target="_blank"><bean:message key="cdcollection.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editdisc" method="POST">
    <table class="propertypage-body">
    <html:hidden property="mediaIdDisplay"/>
    <html:hidden property="idDisplay"/>
    <tr><td><bean:message key="cdcollection.media.disc.edit.title"/></td><td>
    <html:text property="title" size="60"/>
    </td></tr>
    <tr><td><bean:message key="cdcollection.media.disc.edit.artist"/></td><td>
    <html:text property="artist"/>
    </td></tr>
    <tr><td><bean:message key="cdcollection.media.disc.edit.unique-disc-id"/></td><td>
    <html:text property="uniqueDiscId"/>
    </td></tr>
    <tr><td><bean:message key="cdcollection.media.disc.edit.year"/></td><td>
    <html:text property="yearDisplay"/>
    </td></tr>
    <tr><td><bean:message key="cdcollection.media.disc.edit.track-artist-pattern"/></td><td>
    <html:text property="trackArtistPattern"/>
    </td></tr>
    <tr><td><bean:message key="cdcollection.media.disc.edit.track-title-pattern"/></td><td>
    <html:text property="trackTitlePattern"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="cdcollection.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
