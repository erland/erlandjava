<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<jsp:include page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editinventoryentry" method="POST">
    <table>
    <logic:notEmpty name="editInventoryEntryFB" property="id">
        <html:hidden property="id"/>
    </logic:notEmpty>
    <html:hidden property="typeDisplay"/>
    <tr><td><bean:message key="diary.inventory.edit.name"/></td><td>
    <html:text property="name"/>
    </td></tr>
    <tr><td><bean:message key="diary.inventory.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="15"/>
    </td></tr>
    <tr><td><bean:message key="diary.inventory.edit.image"/></td><td>
    <html:text property="image"/>
    </td></tr>
    <tr><td><bean:message key="diary.inventory.edit.largeimage"/></td><td>
    <html:text property="largeImage"/>
    </td></tr>
    <tr><td><bean:message key="diary.inventory.edit.link"/></td><td>
    <html:text property="link"/>
    </td></tr>
    <tr><td><bean:message key="diary.inventory.edit.gallery"/></td><td>
    <html:select property="galleryDisplay" size="1">
        <html:option value="" key="diary.inventory.edit.gallery.none"/>
        <html:options collection="galleriesPB" property="idDisplay" labelProperty="title" />
    </html:select>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="diary.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
