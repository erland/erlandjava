<%@ page session="true" import="erland.webapp.usermgmt.User,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.common.MenuCommand"%>
<table class="no-border">
<tr>
<td class="left-margin"></td>
<td><a class="bold-link" href="portal?do=datevaluediagramservices">Aktiekurser</a></td>
</tr>
<tr>
<td class="left-margin"></td>
<td nowrap><a class="bold-link" href="portal?do=stockaccountmgmt&menu=accountservices">Mina aktier</a></td>
</tr>
<%
    CommandInterface cmd = (CommandInterface) request.getSession().getAttribute("menucmd");
    if((cmd instanceof MenuCommand) && ((MenuCommand)cmd).isExpanded("accountservices")) {
        %><tr>
        <td class="left-margin"></td>
        <td><jsp:include page="menuaccountmgmt.jsp"/></td>
        </tr>
        <%
    }
%>
<tr>
<td class="left-margin"></td>
<td nowrap><a class="bold-link" href="portal?do=stockaccountservices">Mina diagram</a></td>
</tr>
<tr>
<td class="left-margin"></td>
<td nowrap><a class="bold-link" href="portal?do=logout">Logga ut</a></td>
</tr>
</table>
