<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="singlepicturepage-body">
    <tr>
    <td>
        <erland-common:beanimage name="picturePB" property="image" border="0"/>
    </td>
    </tr>
    <tr><td>
    <logic:notEmpty name="picturePB" property="title">
        <p class="singlepicturepage-title"><bean:write name="picturePB" property="title"/></p>
    </logic:notEmpty>
    <p class="singlepicturepage-description"><bean:write name="picturePB" property="description"/></p>
    </td></tr>
    <logic:notEmpty name="metadataPB">
    <tr><td>
        <table class="singlepicturepage-footer">
        <tr><td><p class="singlepicturepage-footer-title"><bean:message key="gallery.image.metadata.title"/></td></tr>
        <tr><td>
        <erland-common:tablegrid name="metadataPB" property="items" id="metadata" tableStyle="singlepicturepage-footer-metadata" cols="2" columnIterations="3">
            <erland-common:tablegridcolumn column="0"><bean:write name="metadata" property="description"/>
            </erland-common:tablegridcolumn>
            <erland-common:tablegridcolumn column="1"> : <bean:write name="metadata" property="value"/></erland-common:tablegridcolumn>
            <erland-common:tablegridcolumn cellWidth="50" column="2">&nbsp;</erland-common:tablegridcolumn>
        </erland-common:tablegrid>
        </td></tr>
        </table>
        <logic:equal name="metadataPB" property="showAll" value="true">
            <bean:define id="showSelectedLink" name="metadataPB" property="showSelectedLink" type="String"/>
            <br><a class="singlepicturepage-button" href="<html:rewrite page="<%=showSelectedLink%>"/>"/><bean:message key="gallery.image.metadata.show-selected"/></a>
        </logic:equal>
        <logic:notEqual name="metadataPB" property="showAll" value="true">
            <bean:define id="showAllLink" name="metadataPB" property="showAllLink" type="String"/>
            <br><a class="singlepicturepage-button" href="<html:rewrite page="<%=showAllLink%>"/>"/><bean:message key="gallery.image.metadata.show-all"/></a>
        </logic:notEqual>
    </td></tr>
    </logic:notEmpty>
</table>
