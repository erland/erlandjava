<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<table class="no-border">
<tr>
<td class="left-margin"></td>
<td><a class="bold-link" href="<html:rewrite page="/do/viewbrokerstocks"/>"><bean:message key="stock.menu.brokerstocks"/></a></td>
</tr>
<tr>
<td class="left-margin"></td>
<td nowrap><a class="bold-link" href="<html:rewrite page="/do/expandcollapsemenu"/>?menuName=mainmenu&menuItemId=3">Mina aktier</a></td>
</tr>
<erland-common:menuitem name="mainmenu" id="3">
<tr>
<td class="left-margin"></td>
<td><jsp:include page="menuaccountmgmt.jsp"/></td>
</tr>
</erland-common:menuitem>
<tr>
<td class="left-margin"></td>
<td nowrap><a class="bold-link" href="<html:rewrite page="/do/viewaccountdiagramwithoutdiagram"/>">Mina diagram</a></td>
</tr>
<tr>
<td class="left-margin"></td>
<td nowrap><a class="bold-link" href="<html:rewrite page="/do/logout"/>"><bean:message key="stock.menu.logout"/></a></td>
</tr>
</table>
