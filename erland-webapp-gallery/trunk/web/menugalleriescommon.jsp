<%@ page import="erland.webapp.gallery.gallery.Gallery,
                 erland.webapp.gallery.gallery.ViewGalleriesInterface,
                 erland.webapp.common.CommandInterface,
                 erland.webapp.gallery.gallery.category.ViewCategoriesInterface,
                 erland.webapp.gallery.gallery.category.Category"%>
<%
CommandInterface cmd = (CommandInterface) request.getSession().getAttribute("menugalleries");
if(cmd instanceof ViewGalleriesInterface) {
    Gallery[] galleries = ((ViewGalleriesInterface)cmd).getGalleries();
    String user = request.getParameter("user");
    String command = request.getParameter("searchcategoriescmd");
    for (int i = 0; i < galleries.length; i++) {
        Gallery gallery = galleries[i];
        %>
        <tr>
        <td class="sub-menu"></td>
        <td>
        <a href="portal?do=<%=command%>&gallery=<%=gallery.getId()%><%=user!=null?"&user="+user:""%>&start=0&max=9" class="bold-link"><%=gallery.getTitle()%></a>
        </td>
        </tr>
        <%
        CommandInterface menucategoriescmd = (CommandInterface) request.getSession().getAttribute("menucategories");
        if(menucategoriescmd instanceof ViewCategoriesInterface) {
            Category[] categories = ((ViewCategoriesInterface)menucategoriescmd).getCategories();
            if(categories.length>0 && categories[0].getGallery().equals(gallery.getId())) {
                %>
                <tr>
                <td class="left-margin"></td>
                <td colspan="8">
                <jsp:include page="menucategories.jsp">
                    <jsp:param name="indent" value="0"/>
                </jsp:include>
                </td>
                </tr>
                <%
            }
        }
    }
}
%>
