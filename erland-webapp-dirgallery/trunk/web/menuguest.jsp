<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.dirgallery.gallery.ViewGalleriesInterface"%>
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
</table>
