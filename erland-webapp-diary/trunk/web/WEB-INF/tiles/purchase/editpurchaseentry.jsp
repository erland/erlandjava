<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<jsp:include page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editpurchaseentry" method="POST">
    <table>
    <logic:notEmpty name="editPurchaseEntryFB" property="id">
        <html:hidden property="id"/>
    </logic:notEmpty>
    </td></tr>
    <tr><td><bean:message key="diary.purchase.edit.date"/></td><td>
    <html:text property="dateDisplay"/>
    </td></tr>
    <tr><td><bean:message key="diary.purchase.edit.store"/></td><td>
    <html:text property="store"/>
    </td></tr>
    <tr><td><bean:message key="diary.purchase.edit.category"/></td><td>
    <html:select property="categoryDisplay" size="1">
        <html:options collection="purchaseEntryCategoriesPB" property="idDisplay" labelProperty="description" />
    </html:select>
    <tr><td><bean:message key="diary.purchase.edit.description"/></td><td>
    <html:text property="description"/>
    </td></tr>
    <tr><td><bean:message key="diary.purchase.edit.price"/></td><td>
    <html:text property="priceDisplay"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="diary.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
