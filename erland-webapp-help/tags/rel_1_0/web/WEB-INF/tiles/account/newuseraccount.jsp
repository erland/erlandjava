<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<p class="bold"><bean:message key="help.account.new.title"/></p>
<p class="normal"><bean:message key="help.account.new.mandatory-text"/></p>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/guest/newuseraccount" method="POST">
    <table class="propertypage-body">
    <tr><td><bean:message key="help.account.new.username"/></td><td>
    <html:text property="username"/>
    </td></tr>
    <tr><td><bean:message key="help.account.new.firstname"/></td><td>
    <html:text property="firstName"/>
    </td></tr>
    <tr><td><bean:message key="help.account.new.lastname"/></td><td>
    <html:text property="lastName"/>
    </td></tr>
    <tr><td><bean:message key="help.account.new.mail"/></td><td>
    <html:text property="mail"/>
    </td></tr>
    <tr><td><bean:message key="help.account.new.password1"/></td><td>
    <html:password property="password1"/>
    </td></tr>
    <tr><td><bean:message key="help.account.new.password2"/></td><td>
    <html:password property="password2"/>
    </td></tr>
    <tr><td></td><td>
    <input type="submit" value="<bean:message key="help.account.new.register"/>">
    <input type="button" value="<bean:message key="help.account.new.cancel"/>" onClick="window.location='<html:rewrite page="/do/index"/>'">
    </td></tr>
    <table>
</html:form>
