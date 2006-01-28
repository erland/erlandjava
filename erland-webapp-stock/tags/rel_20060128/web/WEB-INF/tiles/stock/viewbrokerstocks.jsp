<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<bean:define id="stockDiagramPB" name="brokerStockFB" toScope="request"/>
<tiles:insert name="/WEB-INF/tiles/stock/viewstockdiagram.jsp"/>

<table class="brokerpage-body">
<logic:iterate name="brokersPB" id="broker" indexId="brokerNo" >
    <tr><td nowrap>
    <p class="brokerpage-broker-title"><bean:write name="broker" property="description"/></p>
    </td></tr>
    <html:form action="/viewbrokerstocks" method="POST">
        <html:hidden name="broker" property="broker"/>
        <tr class="brokerpage-broker-diagram"><td class="brokerpage-broker-diagram" nowrap>
        <html:select property="stock">
            <html:optionsCollection name="broker" property="stocks" label="description" value="stock"/>
        </html:select>
        <a class="brokerpage-button" href=" " onClick="document.forms[<bean:write name="brokerNo"/>].submit();return false;"><bean:message key="stock.broker.edit.diagram"/></a>
        </td></tr>
    </html:form>
    <tr><td><p class="brokerpage-broker-separator">&nbsp</p></td></tr>
</logic:iterate>
</table>
