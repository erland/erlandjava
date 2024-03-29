<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editappendixentry" method="POST">
    <table class="propertypage-body">
    <logic:notEmpty name="editAppendixFB" property="id">
        <html:hidden property="id"/>
    </logic:notEmpty>
    <tr><td><bean:message key="diary.appendix.edit.name"/></td><td>
    <html:text property="name"/>
    </td></tr>
    <tr><td><bean:message key="diary.appendix.edit.description"/></td><td>
    <html:text property="description"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="diary.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
