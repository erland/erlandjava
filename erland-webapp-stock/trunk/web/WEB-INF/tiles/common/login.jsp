<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<%@ page session="true" %>
<p class="normal">Logga in för att titta på dina aktiediagram eller för att uppdatera dina aktieinnehav<br>
Om du vill ha tillgång till aktiediagramsidan kan du höra av dig till <a class="bold-link" href="mailto:erland.i@telia.com">mig</a><br><br>

<form name="loginForm" action="j_security_check" method="POST">
<table>
<tr><td><bean:message key="stock.login.username"/></td><td>
<input type="text" name="j_username" value="">
</td></tr>
<tr><td><bean:message key="stock.login.password"/></td><td>
<input type="password" name="j_password" value="">
</td></tr>
<tr><td></td><td>
<input type="submit" value="<bean:message key="stock.login.button"/>">
</td></tr>
</table>
</form>
