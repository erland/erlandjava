<%@ page session="true" import="erland.webapp.usermgmt.User,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.common.MenuCommand,
                                erland.webapp.gallery.act.gallery.ViewGalleriesInterface"%>
<jsp:useBean id="user" scope="session" class="User" />
<table class="no-border">
<tr>
<td class="left-margin"></td>
<td>
<a href="portal?do=searchgalleries" class="bold-link">Bildarkiv</a>
</td>
</tr>
<%
    CommandInterface cmd = (CommandInterface) request.getSession().getAttribute("menugalleries");
    if(cmd instanceof ViewGalleriesInterface) {
        %>
        <tr>
        <td class="left-margin"></td>
        <td><jsp:include page="menugalleries.jsp"/></td>
        </tr>
        <%
    }
%>
<tr>
<td class="left-margin"></td>
<td>
<a href="portal?do=searchstorages" class="bold-link">Lagringsplatser</a>
</td>
</tr>
<tr>
<td class="left-margin"></td>
<td>
<a href="portal?do=searchguestaccounts" class="bold-link">Gästanvändare</a>
</td>
</tr>
<tr>
<td class="left-margin"></td>
<td>
<a href="portal?do=viewuseraccount" class="bold-link">Inställningar</a>
</td>
</tr>
<%
if(user.hasRole("manager")) {
    %>
    <tr>
    <td class="left-margin"></td>
    <td>
    <a href="portal?do=searchuseraccounts" class="bold-link">Användare</a>
    </td>
    </tr>
    <%
}
%>
<tr>
<td class="left-margin"></td>
<td><a class="bold-link" href="portal?do=logout">Logga ut</a></td>
</tr>
</table>
