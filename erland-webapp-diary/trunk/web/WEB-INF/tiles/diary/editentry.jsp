<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editdiaryentry" method="POST">
    <table>
    <html:hidden property="diary"/>
    <html:hidden property="dateDisplay"/>
    <tr><td><bean:message key="diary.diary.entry.edit.date"/></td><td>
    <bean:write name="editDiaryEntryFB" property="dateDisplay"/>
    </td></tr>
    <tr><td><bean:message key="diary.diary.entry.edit.title"/></td><td>
    <html:text property="title"/>
    </td></tr>
    <tr><td><bean:message key="diary.diary.entry.edit.description"/></td><td>
    <html:textarea property="description" cols="60" rows="15"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="diary.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
