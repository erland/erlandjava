<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<jsp:include page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editinventoryentryevent" method="POST">
    <table>
    <html:hidden property="id"/>
    <logic:notEmpty name="editInventoryEntryEventFB" property="eventId">
        <html:hidden property="eventId"/>
    </logic:notEmpty>
    <tr><td><bean:message key="diary.inventory.event.edit.description"/></td><td>
    <html:select property="descriptionDisplay" size="1">
        <html:options collection="inventoryEntryEventTypesPB" property="idDisplay" labelProperty="description" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="diary.inventory.event.edit.date"/></td><td>
    <html:text property="dateDisplay"/>
    </td></tr>
    <tr><td><bean:message key="diary.inventory.event.edit.size"/></td><td>
    <html:text property="sizeDisplay"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="diary.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
