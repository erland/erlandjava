<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<logic:present name="accountPB">
    <table class="no-border">
    <tr><td><erland-common:expandhtml><bean:write name="accountPB" property="welcomeText"/></erland-common:expandhtml></td></tr>
    </table>
</logic:present>
