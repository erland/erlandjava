<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<p class="normal">
<erland-common:expandhtml><bean:write name="accountPB" property="welcomeText"/></erland-common:expandhtml>
</p>