<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.gallery.ViewGalleriesInterface"%>
<table class="no-border">
    <%
        String user = request.getParameter("user");
    %>
    <%
        CommandInterface cmd = (CommandInterface) request.getSession().getAttribute("menugalleries");
        if(cmd instanceof ViewGalleriesInterface) {
            %>
            <tr>
            <td class="left-margin"></td>
            <td><jsp:include page="menugalleriesguest.jsp"/></td>
            </tr>
            <%
        }
    %>
    <%
    if(request.getSession().getAttribute("guestuser")==null) {
        %>
        <tr><td class="left-margin">&nbsp;</td></tr>
        <tr>
        <td class="left-margin"></td>
        <td>
        <a href="portal?do=loginasguest<%=user!=null?"&user="+user:""%>" class="bold-link">Visa privata bilder</a>
        </td>
        </tr>
        <%
    }else {
        %>
        <tr><td class="left-margin">&nbsp;</td></tr>
        <tr>
        <td class="left-margin"></td>
        <td>
        <a href="portal?do=logoutguest<%=user!=null?"&user="+user:""%>" class="bold-link">Göm privata bilder</a>
        </td>
        </tr>
        <%
    }
    %>
</table>
