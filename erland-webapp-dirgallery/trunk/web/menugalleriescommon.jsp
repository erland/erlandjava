<%@ page import="erland.webapp.common.CommandInterface,
                 erland.webapp.dirgallery.gallery.ViewGalleriesInterface,
                 erland.webapp.dirgallery.gallery.GalleryInterface,
                 erland.webapp.common.ServletParameterHelper"%>
<%
CommandInterface cmd = (CommandInterface) request.getSession().getAttribute("menugalleries");
if(cmd instanceof ViewGalleriesInterface) {
    GalleryInterface[] galleries = ((ViewGalleriesInterface)cmd).getGalleries();
    String user = request.getParameter("user");
    String command = request.getParameter("searchcmd");
    Integer galleryId= ServletParameterHelper.asInteger(request.getParameter("gallery"),null);
    for (int i = 0; i < galleries.length; i++) {
        GalleryInterface gallery = galleries[i];
        String title = gallery.getMenuName();
        if(title==null || title.length()==0) {
            title = gallery.getTitle();
        }
        %>
        <tr>
        <%
        String indentGalleries = request.getParameter("indentgalleries");
        if(indentGalleries!=null && indentGalleries.equalsIgnoreCase("true")) {
            %>
            <td class="sub-menu"></td>
            <%
        }
        %>
        <td nowrap>
        <a href="portal?do=<%=command%>&gallery=<%=gallery.getId()%><%=user!=null?"&user="+user:""%>" class="bold-link<%=(galleryId!=null&&galleryId.equals(gallery.getId()))?"-selected":""%>"><%=title%></a>
        </td>
        </tr>
        <%
    }
}
%>
