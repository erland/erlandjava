<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>

<logic:notEmpty name="stockDiagramPB">
    <logic:notEmpty name="stockDiagramPB" property="broker">
        <logic:notEmpty name="stockDiagramPB" property="stock">
            <img src="<html:rewrite page="/do/stockdiagram"/>?broker=<bean:write name="stockDiagramPB" property="broker"/>&stock=<bean:write name="stockDiagramPB" property="stock"/>"></img>
        </logic:notEmpty>
    </logic:notEmpty>
</logic:notEmpty>
