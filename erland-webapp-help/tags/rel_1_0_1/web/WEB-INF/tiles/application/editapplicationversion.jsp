<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="help.application.version.edit.title"/></div>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editapplicationversion" method="POST">
    <table class="propertypage-body">
    <html:hidden property="application"/>
    <tr><td><bean:message key="help.application.version.edit.version"/></td><td>
    <html:text property="version"/>
    </td></tr>
    <tr><td><bean:message key="help.application.version.edit.orderno"/></td><td>
    <html:text property="orderNoDisplay"/>
    </td></tr>
    <tr><td><bean:message key="help.application.version.edit.previous-version"/></td><td>
    <html:text property="previousVersion"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="help.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
