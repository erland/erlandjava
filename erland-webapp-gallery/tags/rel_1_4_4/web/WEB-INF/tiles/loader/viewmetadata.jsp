<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<logic:notEmpty name="metadataPB">
        <table>
        <tr><td><p class="bold"><bean:message key="gallery.image.metadata.title"/></td></tr>
        <logic:iterate name="metadataPB" property="items" id="metadata">
            <tr><td><bean:write name="metadata" property="description"/></td><td>&nbsp:&nbsp</td><td><bean:write name="metadata" property="value"/></td></tr>
        </logic:iterate>
        </table>
        <logic:equal name="metadataPB" property="showAll" value="true">
            <bean:define id="showSelectedLink" name="metadataPB" property="showSelectedLink" type="String"/>
            <br><a class="bold-link" href="<html:rewrite page="<%=showSelectedLink%>"/>"/><bean:message key="gallery.image.metadata.show-selected"/></a>
        </logic:equal>
        <logic:notEqual name="metadataPB" property="showAll" value="true">
            <bean:define id="showAllLink" name="metadataPB" property="showAllLink" type="String"/>
            <br><a class="bold-link" href="<html:rewrite page="<%=showAllLink%>"/>"/><bean:message key="gallery.image.metadata.show-all"/></a>
        </logic:notEqual>
</logic:notEmpty>
