<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<logic:notEmpty name="accountValuePB" >
    <p class="diagrampage-diagram-title">
    <bean:define name="accountValuePB" property="dateDisplay" id="date" type="String"/>
    <bean:define name="accountValuePB" property="valueDisplay" id="value" type="String"/>
    <bean:define name="accountValuePB" property="purchaseValueDisplay" id="purchaseValue" type="String"/>
    <bean:define name="accountValuePB" property="noOfStocksDisplay" id="noOfStocks" type="String"/>
    <bean:define name="accountValuePB" property="currentRateDisplay" id="currentRate" type="String"/>
    <bean:define name="accountValuePB" property="totalStatisticDisplay" id="totalStatistic" type="String"/>
    <logic:empty name="noOfStocks">
        <bean:message key="stock.account.diagram.value-description" arg0='<%=date%>' arg1='<%=value%>' arg2='<%=purchaseValue%>' />
    </logic:empty>
    <logic:notEmpty name="noOfStocks">
        <bean:message key="stock.account.diagram.value-description-extended" arg0='<%=date%>' arg1='<%=value%>' arg2='<%=purchaseValue%>' arg3='<%=noOfStocks%>' arg4='<%=currentRate%>'/>
    </logic:notEmpty>
    <logic:notEmpty name="totalStatistic">
        <br><bean:message key="stock.account.diagram.statistic" arg0='<%=totalStatistic%>'/><br>
    </logic:notEmpty>
    </p>
    <br>
    <img src="<html:rewrite page="/do/accountdiagram"/>?broker=<bean:write name="accountDiagramFB" property="broker"/>&stock=<bean:write name="accountDiagramFB" property="stock"/>&startDateDisplay=<bean:write name="accountDiagramFB" property="startDateDisplay"/>&endDateDisplay=<bean:write name="accountDiagramFB" property="endDateDisplay"/>"></img>
    <br>
    <table class="diagrampage-statistic" valign="top">
    <tr valign="top"><td valign="top">
    <table class="diagrampage-statistic">
    <tr class="diagrampage-statistic-header"><td><bean:message key="stock.account.diagram.statistic.year"/></td><td><bean:message key="stock.account.diagram.statistic.purchase"/></td><td><bean:message key="stock.account.diagram.statistic.value"/></td><td><bean:message key="stock.account.diagram.statistic.percent"/></td></tr>
    <logic:iterate id="year" name="accountValuePB" property="statisticsPerYear">
        <tr><td align="left"><bean:write name="year" property="yearDisplay"/></td><td align="right"><bean:write name="year" property="purchaseDisplay"/> kr</td><td align="right"><bean:write name="year" property="valueDisplay"/> kr</td><td align="right"><bean:write name="year" property="percentDisplay"/> %</td></tr>
    </logic:iterate>
    </table>
    </td><td valign="top">
    <table class="diagrampage-statistic">
    <tr class="diagrampage-statistic-header"><td></td><td><bean:message key="stock.account.diagram.statistic.stock.purchase"/></td><td><bean:message key="stock.account.diagram.statistic.stock.value"/></td><td><bean:message key="stock.account.diagram.statistic.stock.increasedvalue"/></td><td><bean:message key="stock.account.diagram.statistic.stock.percent"/></td><td><bean:message key="stock.account.diagram.statistic.stock.percent.thisyear"/></td></tr>
    <logic:iterate id="stock" name="accountValuePB" property="statisticsPerStock">
        <tr><td align="left"><b><bean:write name="stock" property="stock"/></b>&nbsp;&nbsp;&nbsp;</td><td align="right"><bean:write name="stock" property="purchaseDisplay"/> kr&nbsp;&nbsp;&nbsp;</td><td align="right"><bean:write name="stock" property="valueDisplay"/> kr</td><td align="right"><bean:write name="stock" property="increasedValueDisplay"/> kr</td><td align="right"><bean:write name="stock" property="percentDisplay"/> %</td><td align="right"><bean:write name="stock" property="percentThisYearDisplay"/> %</td></tr>
    </logic:iterate>
    <tr><td align="left"><b><bean:message key="stock.account.diagram.statistic.stock.name.total"/></b>&nbsp;&nbsp;&nbsp;</td><td align="right"><b><bean:write name="accountValuePB" property="purchaseValueDisplay"/> kr</b>&nbsp;&nbsp;&nbsp;</td><td align="right"><b><bean:write name="accountValuePB" property="valueDisplay"/> kr</b></td><td align="right"><b><bean:write name="accountValuePB" property="increasedValueDisplay"/> kr</b></td><td align="right"><b><bean:write name="accountValuePB" property="totalStatisticDisplay"/> %</b></td><td align="right"><b><bean:write name="accountValuePB" property="totalStatisticThisYearDisplay"/> %</b></td></tr>
    </table>
    </td></tr>
    </table>
</logic:notEmpty>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<table class="diagrampage-body">
<form name="showAccount" action="portal" method="POST">
        <tr><td><bean:message key="stock.account.diagram.first-date"/></td><td>
        <input type="text" name="startDateDisplay" value="<bean:write name="accountDiagramFB" property="startDateDisplay"/>">
        </td></tr>
        <tr><td><bean:message key="stock.account.diagram.last-date"/></td><td>
        <input type="text" name="endDateDisplay" value="<bean:write name="accountDiagramFB" property="endDateDisplay"/>">
        </td></tr>
        <tr><td colspan="2" nowrap>
        <a href="javascript: window.location='<html:rewrite page="/do/viewaccountdiagram"/>?startDateDisplay='+document.showAccount.startDateDisplay.value+'&endDateDisplay='+document.showAccount.endDateDisplay.value" class="diagrampage-button"><bean:message key="stock.account.diagram.view-all"/></a>
        </td></tr>
        <logic:iterate name="brokersPB" id="broker">
            <tr><td colspan="2" nowrap>
            <a href="javascript: window.location='<html:rewrite page="/do/viewaccountdiagram"/>?startDateDisplay='+document.showAccount.startDateDisplay.value+'&endDateDisplay='+document.showAccount.endDateDisplay.value+'&broker=<bean:write name="broker" property="id"/>'" class="diagrampage-button"><bean:write name="broker" property="description"/> <bean:message key="stock.account.diagram.all"/></a>
            </td></tr>
        </logic:iterate>
        <logic:iterate name="accountStocksPB" id="stock" >
            <tr><td colspan="2" nowrap>
            <a class="diagrampage-button" href="javascript: window.location='<html:rewrite page="/do/viewaccountdiagram"/>?startDateDisplay='+document.showAccount.startDateDisplay.value+'&endDateDisplay='+document.showAccount.endDateDisplay.value+'&broker=<bean:write name="stock" property="broker"/>&stock=<bean:write name="stock" property="stock"/>'"><bean:write name="stock" property="brokerDescription"/> <bean:write name="stock" property="stockDescription"/></a>
            </td></tr>
        </logic:iterate>
</form>
</table>
