<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<center>
    <table width="100%" class="no-border-tight">
        <logic:present name="galleryPB">
            <tr><td width="100%" align="center">
            <erland-common:beanlink name="galleryPB" property="logoLink" style="bold-link" target="_blank">
                <erland-common:beanimage name="galleryPB" property="logo" border="0"/>
                <logic:empty name="galleryPB" property="logo">
                    <a class="normal" href="<html:rewrite page="/do/logout"/>"><img src="<%=request.getContextPath()%>/images/logo.gif" border="0"></img></a>
                </logic:empty>
            </erland-common:beanlink>
            </td></tr>
            <logic:empty name="galleryPB" property="logoLink">
                <tr><td width="100%" align="center"><erland-common:beanimage name="galleryPB" property="logo" border="0"/></td></tr>
            </logic:empty>
            <logic:empty name="galleryPB" property="logo">
                <tr><td width="100%" align="center"><a class="normal" href="<html:rewrite page="/do/logout"/>"><img src="<%=request.getContextPath()%>/images/logo.gif" border="0"></img></a></td></tr>
            </logic:empty>
            <logic:equal name="galleryPB" property="useLogoSeparatorDisplay" value="true">
                <tr><td width="100%" height="<bean:write name="galleryPB" property="logoSeparatorHeight"/>" valign="top" bgcolor="<bean:write name="galleryPB" property="logoSeparatorColor"/>"></td></tr>
            </logic:equal>
        </logic:present>
        <logic:notPresent name="galleryPB">
            <tr><td width="100%" align="center"><a class="normal" href="<html:rewrite page="/do/logout"/>"><img src="<%=request.getContextPath()%>/images/logo.gif" border="0"></img></a></td></tr>
        </logic:notPresent>
    </table>
</center>

