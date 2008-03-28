<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="entrypage-body">
    <tr><td colspan="4">
    <erland-common:beanlink name="entryPB" property="updateLink" style="entrypage-button"><bean:message key="datacollection.entry.button.edit"/></erland-common:beanlink>
    <erland-common:beanlink name="entryPB" property="removeLink" style="entrypage-button" onClickMessageKey="datacollection.entry.button.remove.are-you-sure"><bean:message key="datacollection.entry.button.remove"/></erland-common:beanlink>
    <erland-common:beanlink name="entryPB" property="newDataLink" style="entrypage-button"><bean:message key="datacollection.entry.button.new-data"/></erland-common:beanlink>
    </td></tr>
    <tr><td class="entrypage-entrytitle" colspan="2" nowrap><bean:write name="entryPB" property="title"/></td></tr>
    <tr><td colspan="2"><bean:write name="entryPB" property="description"/></td></tr>
    <tr><td>&nbsp;</td></tr>
    <logic:notEmpty name="entryPB" property="lastChanged">
    <tr><td colspan="2"><bean:message key="datacollection.entry.view.lastchanged"/>: <bean:write name="entryPB" property="lastChangedDisplay"/></td></tr>
    <tr><td>&nbsp;</td></tr>
    </logic:notEmpty>
    <logic:iterate id="data" name="entryPB" property="datas">
        <tr><td width="10%" nowrap><bean:write name="data" property="type"/></td><td><erland-common:beanlink name="data" property="updateLink" style="entrypage-button"><bean:message key="datacollection.entry.button.update-data"/></erland-common:beanlink> <erland-common:beanlink name="data" property="viewLink" style="entrypage-button" target="_blank"><bean:message key="datacollection.entry.button.view-data"/></erland-common:beanlink> <erland-common:beanlink name="data" property="downloadLink" style="entrypage-button" target="_blank"><bean:message key="datacollection.entry.button.download-data"/></erland-common:beanlink> <erland-common:beanlink name="data" property="removeLink" style="entrypage-button" onClickMessageKey="datacollection.entry.button.remove-data.are-you-sure"><bean:message key="datacollection.entry.button.remove-data"/></erland-common:beanlink></td></tr>
        <tr><td>&nbsp;</td></tr>
    </logic:iterate>
    <logic:notEmpty name="entryPB" property="historyEntries">
        <tr><td>&nbsp;</td></tr>
        <tr><td class="entrypage-historytitle" colspan="2"><bean:message key="datacollection.entry.view.historyentries"/></td></tr>
        <logic:iterate id="historyEntry" name="entryPB" property="historyEntries">
            <tr><td width="10%" nowrap><bean:write name="historyEntry" property="lastChangedDisplay"/></td><td><erland-common:beanlink name="historyEntry" property="historyLink" style="entrypage-button"><bean:message key="datacollection.entry.button.view-history"/></erland-common:beanlink></td></tr>
        </logic:iterate>
    </logic:notEmpty>
</table>
