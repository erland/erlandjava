<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<center>
    <a class="header-logo" href="<html:rewrite page="/do/logout"/>"><img src="<%=request.getContextPath()%>/images/logo.gif" border="0"></img></a>
    <div class="header-text"><bean:message key="cdcollection.welcome.title"/></div>
</center>

