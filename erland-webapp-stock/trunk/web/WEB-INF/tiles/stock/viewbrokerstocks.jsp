<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<bean:define id="stockDiagramPB" name="brokerStockFB" toScope="request"/>
<tiles:insert name="/WEB-INF/tiles/stock/viewstockdiagram.jsp"/>

<table>
<logic:iterate name="brokersPB" id="broker" indexId="brokerNo" >
    <tr><td nowrap>
    <p class="title"><bean:write name="broker" property="description"/></p>
    </td></tr>
    <html:form action="/viewbrokerstocks" method="POST">
        <html:hidden name="broker" property="broker"/>
        <tr><td nowrap>
        <html:select property="stock">
            <html:optionsCollection name="broker" property="stocks" label="description" value="stock"/>
        </html:select>
        <a class="bold-link" href=" " onClick="document.forms[<bean:write name="brokerNo"/>].submit();return false;">Diagram</a>
        </td></tr>
    </html:form>
    <tr><td><p class="normal">&nbsp</p></td></tr>
</logic:iterate>
</table>
