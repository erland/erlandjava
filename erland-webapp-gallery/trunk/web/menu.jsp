<%@ page session="true" import="erland.webapp.usermgmt.User,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.common.MenuCommand,
                                erland.webapp.gallery.gallery.ViewGalleriesInterface"%>
<jsp:useBean id="user" scope="session" class="User" />
<table class="no-border">
<tr>
<td class="left-margin"></td>
<td colspan="3">
<a href="portal?do=searchgalleries" class="bold-link">Galleries</a>
</td>
</tr>
<%
    CommandInterface cmd = (CommandInterface) request.getSession().getAttribute("menugalleries");
    if(cmd instanceof ViewGalleriesInterface) {
        %>
        <tr>
        <td class="left-margin"></td>
        <td colspan="3"><jsp:include page="menugalleries.jsp"/></td>
        </tr>
        <%
    }
%>
<tr>
<td class="left-margin"></td>
<td colspan="3">
<a href="portal?do=searchstorages" class="bold-link">Storages</a>
</td>
</tr>
<tr>
<td class="left-margin"></td>
<td colspan="3">
<a href="portal?do=searchguestaccounts" class="bold-link">Guest users</a>
</td>
</tr>
<tr>
<td class="left-margin"></td>
<td colspan="3">
<a href="portal?do=viewuseraccount" class="bold-link">Preferences</a>
</td>
</tr>
<%
if(user.hasRole("manager")) {
    %>
    <tr>
    <td class="left-margin"></td>
    <td colspan="3">
    <a href="portal?do=searchuseraccounts" class="bold-link">Users</a>
    </td>
    </tr>
    <%
}
%>
<tr>
<td class="left-margin"></td>
<td><a class="bold-link" href="portal?do=logout">Logout</a></td>
</tr>
</table>
