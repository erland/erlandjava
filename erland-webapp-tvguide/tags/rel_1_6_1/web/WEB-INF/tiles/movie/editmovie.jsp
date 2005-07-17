<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="tvguide.movie.edit.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="movie.edit" target="_blank"><bean:message key="tvguide.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editmovie" method="POST">
    <table class="propertypage-body">
    <logic:notEmpty name="editMovieFB" property="title">
        <html:hidden property="title"/>
        <tr><td><bean:message key="tvguide.movie.edit.title"/></td><td><bean:write name="editMovieFB" property="title"/></td></tr>
    </logic:notEmpty>
    <tr><td><bean:message key="tvguide.movie.edit.link"/></td><td>
    <html:text property="link"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="tvguide.buttons.save"/></html:submit>
    </td></tr>
</html:form>
