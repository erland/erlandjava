<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<center>
    <a class="header-logo" href="<html:rewrite page="/do/logout"/>"><erland-common:beanimage name="applicationPB" property="logo" border="0"/></a>
    <div class="header-text"><bean:message key="help.welcome.title"/></div>
</center>
