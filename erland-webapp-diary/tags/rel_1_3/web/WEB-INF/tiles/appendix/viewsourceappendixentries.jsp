<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table border="0">
    <logic:iterate name="appendixEntriesPB" id="entry">
        <tr>
        <td colspan="2"><bean:write name="entry" property="name"/></td>
        </tr>
        <tr>
        <td colspan="2"><bean:write name="entry" property="description"/></td>
        </tr>
        <tr>
        <td><a href="<html:rewrite page="/do/user/viewsourceappendixentry"/>?id=<bean:write name="entry" property="id"/>" class="bold-link"><bean:message key="diary.sourceappendix.button.edit"/></a></td>
        <td><a href="<html:rewrite page="/do/user/removesourceappendixentry"/>?id=<bean:write name="entry" property="id"/>" class="bold-link" onClick="return confirm('<bean:message key="diary.sourceappendix.button.delete.are-you-sure"/>')"><bean:message key="diary.sourceappendix.button.delete"/></a></td>
        <tr>
    </logic:iterate>
</table>
