<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/edituseraccount" method="POST">
    <table class="propertypage-body">
    <html:hidden property="username"/>
    <tr><td><bean:message key="issuetracking.account.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="5"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="issuetracking.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
