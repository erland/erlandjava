<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.dirgallery.gallery.ViewGalleriesInterface"%>
<table class="no-border">
    <%
        String user = request.getParameter("user");
    %>
    <tr>
    <td class="left-margin"></td>
    <td colspan="3">
    <a href="portal?do=searchgalleriesguest<%=user!=null?"&user="+user:""%>" class="bold-link">Bildarkiv</a>
    </td>
    </tr>
    <%
        CommandInterface cmd = (CommandInterface) request.getSession().getAttribute("menugalleries");
        if(cmd instanceof ViewGalleriesInterface) {
            %>
            <tr>
            <td class="left-margin"></td>
            <td colspan="3"><jsp:include page="menugalleriesguest.jsp"/></td>
            </tr>
            <%
        }
    %>
</table>
