<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<jsp:include page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editdiary" method="POST">
    <table>
    <logic:notEmpty name="editDiaryFB" property="id">
        <html:hidden property="id"/>
    </logic:notEmpty>
    <tr><td><bean:message key="diary.diary.edit.title"/></td><td>
    <html:text property="title"/>
    </td></tr>
    <tr><td><bean:message key="diary.diary.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="15"/>
    </td></tr>
    <tr><td><bean:message key="diary.diary.edit.official"/></td><td>
    <html:checkbox property="official"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="diary.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
