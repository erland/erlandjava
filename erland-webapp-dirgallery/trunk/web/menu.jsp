<%@ page session="true" import="erland.webapp.usermgmt.User,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.common.MenuCommand,
                                erland.webapp.dirgallery.gallery.ViewGalleriesInterface"%>
<jsp:useBean id="user" scope="session" class="User" />
<table class="no-border">
<tr>
<td class="left-margin"></td>
<td nowrap>
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
<td nowrap>
<a href="portal?do=viewuseraccount" class="bold-link">Inst�llningar</a>
</td>
</tr>
<%
if(user.hasRole("manager")) {
    %>
    <tr>
    <td class="left-margin"></td>
    <td nowrap>
    <a href="portal?do=searchuseraccounts" class="bold-link">Anv�ndare</a>
    </td>
    </tr>
    <tr>
    <td class="left-margin"></td>
    <td nowrap>
    <a href="portal?do=registernewuseraccount" class="bold-link">Ny anv�ndare</a>
    </td>
    </tr>
    <%
}
%>
<tr>
<td class="left-margin"></td>
<td nowrap><a class="bold-link" href="portal?do=logout">Logga ut</a></td>
</tr>
</table>