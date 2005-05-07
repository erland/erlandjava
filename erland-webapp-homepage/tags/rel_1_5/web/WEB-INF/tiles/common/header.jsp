<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<table width="100%" class="header-body">
<tr><td width="100%">
<center>
    <table width="100%" class="header-body-inner">
        <logic:present name="accountPB">
            <logic:equal name="accountPB" property="showLogoDisplay" value="true"> 
                <tr class="header-logo"><td class="header-logo" width="100%" align="center">
                <erland-common:beanlink name="accountPB" property="logoLink" style="header-logo" target="_blank">
                    <erland-common:beanimage name="accountPB" property="logo" border="0"/>
                    <logic:empty name="accountPB" property="logo">
                        <a class="header-logo" href="<html:rewrite page="/do/logout"/>"><img src="<%=request.getContextPath()%>/images/logo.gif" border="0"></img></a>
                    </logic:empty>
                </erland-common:beanlink>
                </td></tr>
                <logic:empty name="accountPB" property="logoLink">
                    <tr class="header-logo"><td class="header-logo" width="100%" align="center"><erland-common:beanimage name="accountPB" property="logo" border="0"/></td></tr>
                </logic:empty>
                <logic:empty name="accountPB" property="logo">
                    <tr class="header-logo"><td class="header-logo" width="100%" align="center"><a class="header-logo" href="<html:rewrite page="/do/logout"/>"><img src="<%=request.getContextPath()%>/images/logo.gif" border="0"></img></a></td></tr>
                </logic:empty>
            </logic:equal>
            <logic:notEmpty name="accountPB" property="headerText">
                <tr class="header-text"><td class="header-text" align="center"><div class="header-text"><bean:write name="accountPB" property="headerText"/></div></td></tr>
            </logic:notEmpty>
        </logic:present>
        <logic:notPresent name="accountPB">
            <tr class="header-logo"><td class="header-logo" width="100%" align="center"><a class="header-logoo" href="<html:rewrite page="/do/logout"/>"><img src="<%=request.getContextPath()%>/images/logo.gif" border="0"></img></a></td></tr>
            <tr class="header-text"><td class="header-text" align="center"><div class="header-text"><bean:message key="homepage.welcome.title"/></div></td></tr>
        </logic:notPresent>
    </table>
</center>
</td><td>
<erland-common:beanlink name="accountPB" property="nativeLink" style="bold-link"><bean:message key="homepage.home.native-link"/></erland-common:beanlink>
<br><erland-common:beanlink name="accountPB" property="englishLink" style="bold-link"><bean:message key="homepage.home.english-link"/></erland-common:beanlink>
</td></tr></table>

