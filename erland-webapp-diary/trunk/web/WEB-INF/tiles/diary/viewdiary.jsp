<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:beanlink name="diaryPB" property="updateLink" style="bold-link"><bean:message key="diary.diary.button.edit"/></erland-common:beanlink>
<erland-common:beanlink name="diaryPB" property="removeLink" style="bold-link" onClickMessageKey="diary.diary.button.delete.are-you-sure"><bean:message key="diary.diary.button.delete"/></erland-common:beanlink>
<br>
<table width="600" class="no-border">
    <tr><td><p class="title"><bean:write name="diaryPB" property="title"/></p></td></tr>
    <tr><td><p class="normal"><erland-common:expandhtml><bean:write name="diaryPB" property="description"/></erland-common:expandhtml></p></td></tr>
</table>
<br>
<table width="300" class="no-border">
    <logic:iterate name="diaryEntriesPB" id="entry">
        <tr>
        <td>
        <bean:write name="entry" property="dateDisplay"/> <erland-common:beanlink name="entry" property="viewLink" style="bold-link"><bean:write name="entry" property="title"/></erland-common:beanlink>
        </td>
        </tr>
    </logic:iterate>
</table>
