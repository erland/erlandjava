<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<p class="bold"><bean:message key="dirgallery.account.new.title"/></p>
<p class="normal"><bean:message key="dirgallery.account.new.mandatory-text"/></p>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/newuseraccount" method="POST">
    <table>
    <tr><td><bean:message key="dirgallery.account.new.username"/></td><td>
    <html:text property="username"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.account.new.firstname"/></td><td>
    <html:text property="firstName"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.account.new.lastname"/></td><td>
    <html:text property="lastName"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.account.new.password1"/></td><td>
    <html:password property="password1"/>
    </td></tr>
    <tr><td><bean:message key="dirgallery.account.new.password2"/></td><td>
    <html:password property="password2"/>
    </td></tr>
    <tr><td></td><td>
    <input type="hidden" name="description" value="<bean:message key="dirgallery.account.new.description-initial"/>">
    <input type="hidden" name="welcomeText" value="<bean:message key="dirgallery.account.new.welcome-text-initial"/>">
    <input type="submit" value="<bean:message key="dirgallery.account.new.register"/>">
    <input type="button" value="<bean:message key="dirgallery.account.new.cancel"/>" onClick="window.location='<html:rewrite page="/do/index"/>'">
    </td></tr>
    <table>
</html:form>
