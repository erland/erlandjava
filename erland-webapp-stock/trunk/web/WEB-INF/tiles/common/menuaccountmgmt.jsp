<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<table class="no-border">
<tr>
<td class="sub-menu"></td>
<td nowrap><a class="bold-link" href="<html:rewrite page="/do/viewaccountpermanententries"/>"><bean:message key="stock.menu.permanent"/></a></td>
</tr>
<tr>
<td class="sub-menu"></td>
<td nowrap><a class="bold-link" href="<html:rewrite page="/do/viewaccountpurchaseonceentries"/>"><bean:message key="stock.menu.purchaseonce"/></a></td>
</tr>
<tr>
<td class="sub-menu"></td>
<td nowrap><a class="bold-link" href="<html:rewrite page="/do/viewaccountcontinouslyentries"/>"><bean:message key="stock.menu.continously"/></a></td>
</tr>
</table>
