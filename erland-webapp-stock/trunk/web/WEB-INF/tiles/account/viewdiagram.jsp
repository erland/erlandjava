<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<logic:notEmpty name="accountValuePB" >
    <img src="<html:rewrite page="/do/accountdiagram"/>?broker=<bean:write name="accountDiagramFB" property="broker"/>&stock=<bean:write name="accountDiagramFB" property="stock"/>&startDateDisplay=<bean:write name="accountDiagramFB" property="startDateDisplay"/>&endDateDisplay=<bean:write name="accountDiagramFB" property="endDateDisplay"/>"></img>
    <br>
    <table class="diagrampage-statistic">
    <tr valign="top"><td valign="top">
    <table class="diagrampage-statistic">
    <tr class="diagrampage-statistic-header"><td></td><td><bean:message key="stock.account.diagram.statistic.stock.purchase"/></td><td><bean:message key="stock.account.diagram.statistic.stock.value"/></td><td><bean:message key="stock.account.diagram.statistic.stock.increasedvalue"/></td><td><bean:message key="stock.account.diagram.statistic.stock.percent"/></td><td><bean:message key="stock.account.diagram.statistic.stock.percent.thisyear"/></td></tr>
    <logic:iterate id="stock" name="accountValuePB" property="statisticsPerStock">
        <tr><td align="left"><a class="diagrampage-button" href="javascript: window.location='<html:rewrite page="/do/viewaccountdiagram"/>?startDateDisplay='+document.showAccount.startDateDisplay.value+'&endDateDisplay='+document.showAccount.endDateDisplay.value+'&broker=<bean:write name="stock" property="brokerId"/>&stock=<bean:write name="stock" property="stockId"/>'"><bean:write name="stock" property="stockDescription"/></a>&nbsp;&nbsp;&nbsp;</td><td align="right"><bean:write name="stock" property="purchaseDisplay"/> kr&nbsp;&nbsp;&nbsp;</td><td align="right"><bean:write name="stock" property="valueDisplay"/> kr</td><td align="right"><bean:write name="stock" property="increasedValueDisplay"/> kr</td><td align="right"><bean:write name="stock" property="percentDisplay"/> %</td><td align="right"><bean:write name="stock" property="percentThisYearDisplay"/> %</td></tr>
    </logic:iterate>
    <tr><td align="left"><a class="diagrampage-button" href="javascript: window.location='<html:rewrite page="/do/viewaccountdiagram"/>?startDateDisplay='+document.showAccount.startDateDisplay.value+'&endDateDisplay='+document.showAccount.endDateDisplay.value"><bean:message key="stock.account.diagram.statistic.stock.name.total"/></a>&nbsp;&nbsp;&nbsp;</td><td align="right"><b><bean:write name="accountValuePB" property="purchaseValueDisplay"/> kr</b>&nbsp;&nbsp;&nbsp;</td><td align="right"><b><bean:write name="accountValuePB" property="valueDisplay"/> kr</b></td><td align="right"><b><bean:write name="accountValuePB" property="increasedValueDisplay"/> kr</b></td><td align="right"><b><bean:write name="accountValuePB" property="totalStatisticDisplay"/> %</b></td><td align="right"><b><bean:write name="accountValuePB" property="totalStatisticThisYearDisplay"/> %</b></td></tr>
    </table>
    </td><td valign="top">
        <bean:define name="accountValuePB" property="noOfStocksDisplay" id="noOfStocks" type="String"/>
        <bean:define name="accountValuePB" property="currentRateDisplay" id="currentRate" type="String"/>
        <logic:empty name="accountValuePB" property="stockDescription">
            <b><bean:message key="stock.account.diagram.year-statistic-title-total"/></b>
        </logic:empty>
        <logic:notEmpty name="accountValuePB" property="stockDescription">
            <b><bean:message key="stock.account.diagram.year-statistic-title"/> <bean:write name="accountValuePB" property="stockDescription"/></b>
        </logic:notEmpty>
        <logic:notEmpty name="noOfStocks">
            <br><bean:message key="stock.account.diagram.year-statistic-details" arg0='<%=noOfStocks%>' arg1='<%=currentRate%>'/>
        </logic:notEmpty>
        <table class="diagrampage-statistic">
        <tr class="diagrampage-statistic-header"><td><bean:message key="stock.account.diagram.statistic.year"/></td><td><bean:message key="stock.account.diagram.statistic.purchase"/></td><td><bean:message key="stock.account.diagram.statistic.value"/></td><td><bean:message key="stock.account.diagram.statistic.percent"/></td></tr>
        <logic:iterate id="year" name="accountValuePB" property="statisticsPerYear">
            <tr><td align="left"><bean:write name="year" property="yearDisplay"/></td><td align="right"><bean:write name="year" property="purchaseDisplay"/> kr</td><td align="right"><bean:write name="year" property="valueDisplay"/> kr</td><td align="right"><bean:write name="year" property="percentDisplay"/> %</td></tr>
        </logic:iterate>
        </table>
        <br>
        <table class="diagrampage-statistic">
        <form name="showAccount" action="portal" method="POST">
                <tr><td><bean:message key="stock.account.diagram.first-date"/></td><td>
                <input type="text" name="startDateDisplay" value="<bean:write name="accountDiagramFB" property="startDateDisplay"/>">
                </td></tr>
                <tr><td><bean:message key="stock.account.diagram.last-date"/></td><td>
                <input type="text" name="endDateDisplay" value="<bean:write name="accountDiagramFB" property="endDateDisplay"/>">
                </td></tr>
        </form>
        </table>
    </td></tr>
    </table>
</logic:notEmpty>
<logic:empty name="accountValuePB" >
    <table class="diagrampage-statistic">
    <form name="showAccount" action="portal" method="POST">
            <tr><td><bean:message key="stock.account.diagram.first-date"/></td><td>
            <input type="text" name="startDateDisplay" value="<bean:write name="accountDiagramFB" property="startDateDisplay"/>">
            </td></tr>
            <tr><td><bean:message key="stock.account.diagram.last-date"/></td><td>
            <input type="text" name="endDateDisplay" value="<bean:write name="accountDiagramFB" property="endDateDisplay"/>">
            </td></tr>
            <tr><td></td><td><a href="javascript: window.location='<html:rewrite page="/do/viewaccountdiagram"/>?startDateDisplay='+document.showAccount.startDateDisplay.value+'&endDateDisplay='+document.showAccount.endDateDisplay.value" class="diagrampage-button"><bean:message key="stock.account.diagram.view-all"/></a></td></tr>
    </form>
    </table>
</logic:empty>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
