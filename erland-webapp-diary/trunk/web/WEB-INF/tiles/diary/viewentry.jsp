<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<erland-common:beanlink name="diaryEntryPB" property="updateLink" style="diaryentrypage-button"><bean:message key="diary.diary.button.edit"/></erland-common:beanlink>
<erland-common:beanlink name="diaryEntryPB" property="removeLink" style="diaryentrypage-button" onClickMessageKey="diary.diary.button.delete.are-you-sure"><bean:message key="diary.diary.button.delete"/><br></erland-common:beanlink>
<table class="diaryentrypage-body">
<tr>
    <td><p class="diaryentrypage-title"><bean:write name="diaryEntryPB" property="dateDisplay"/> <bean:write name="diaryEntryPB" property="title"/></p></td>
</tr>
<tr>
    <td><p class="diaryentrypage-description"><erland-common:expandhtml><bean:write name="diaryEntryPB" property="description"/></erland-common:expandhtml></p></td>
</tr>
</table>
