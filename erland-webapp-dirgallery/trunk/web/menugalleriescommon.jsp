<%@ page import="erland.webapp.common.CommandInterface,
                 erland.webapp.dirgallery.gallery.ViewGalleriesInterface,
                 erland.webapp.dirgallery.gallery.GalleryInterface"%>
<%
CommandInterface cmd = (CommandInterface) request.getSession().getAttribute("menugalleries");
if(cmd instanceof ViewGalleriesInterface) {
    GalleryInterface[] galleries = ((ViewGalleriesInterface)cmd).getGalleries();
    String user = request.getParameter("user");
    String command = request.getParameter("searchcmd");
    for (int i = 0; i < galleries.length; i++) {
        GalleryInterface gallery = galleries[i];
        String title = gallery.getMenuName();
        if(title==null || title.length()==0) {
            title = gallery.getTitle();
        }
        %>
        <tr>
        <td class="sub-menu"></td>
        <td>
        <a href="portal?do=<%=command%>&gallery=<%=gallery.getId()%><%=user!=null?"&user="+user:""%>" class="bold-link"><%=title%></a>
        </td>
        </tr>
        <%
    }
}
%>
