<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<erland-common:beanlink name="diaryEntryPB" property="updateLink" style="bold-link"><bean:message key="diary.diary.button.edit"/></erland-common:beanlink>
<erland-common:beanlink name="diaryEntryPB" property="removeLink" style="bold-link" onClickMessageKey="diary.diary.button.delete.are-you-sure"><bean:message key="diary.diary.button.delete"/><br></erland-common:beanlink>
<table>
<tr>
    <td><p class="title"><bean:write name="diaryEntryPB" property="dateDisplay"/> <bean:write name="diaryEntryPB" property="title"/></p></td>
</tr>
<tr>
    <td><p class="normal"><erland-common:expandhtml><bean:write name="diaryEntryPB" property="description"/></erland-common:expandhtml></p></td>
</tr>
</table>
