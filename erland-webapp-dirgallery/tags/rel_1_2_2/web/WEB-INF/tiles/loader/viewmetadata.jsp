<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<logic:notEmpty name="metadataPB">
        <table class="singlepicturepage-footer">
        <logic:iterate name="metadataPB" property="items" id="metadata" length="1">
            <tr><td><p class="singlepicturepage-footer-title"><bean:message key="dirgallery.image.metadata.title"/></td></tr>
        </logic:iterate>
        <tr><td>
        <erland-common:tablegrid name="metadataPB" property="items" id="metadata" tableStyle="singlepicturepage-footer-metadata" cols="2" columnIterations="3">
            <erland-common:tablegridcolumn column="0"><bean:write name="metadata" property="description"/>
            </erland-common:tablegridcolumn>
            <erland-common:tablegridcolumn column="1"> : <bean:write name="metadata" property="value"/></erland-common:tablegridcolumn>
            <erland-common:tablegridcolumn cellWidth="50" column="2">&nbsp;</erland-common:tablegridcolumn>
        </erland-common:tablegrid>
        </td></tr>
        </table>
        <erland-common:beanlink name="metadataPB" property="showSelectedLink" style="singlepicturepage-button">
            <br><bean:message key="dirgallery.image.metadata.show-selected"/>
        </erland-common:beanlink>
        <erland-common:beanlink name="metadataPB" property="showAllLink" style="singlepicturepage-button">
            <br><bean:message key="dirgallery.image.metadata.show-all"/>
        </erland-common:beanlink>
        <erland-common:beanlink name="metadataPB" property="hideAllLink" style="singlepicturepage-button">
            <br><bean:message key="dirgallery.image.metadata.hide-all"/>
        </erland-common:beanlink>
</logic:notEmpty>
