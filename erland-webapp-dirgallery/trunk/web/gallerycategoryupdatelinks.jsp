<%@ page import="erland.webapp.common.CommandInterface,
                 erland.webapp.dirgallery.gallery.ViewGalleryInterface,
                 erland.webapp.dirgallery.gallery.GalleryInterface"%>
<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGalleryInterface) {
        GalleryInterface gallery = ((ViewGalleryInterface)cmd).getGallery();
        if(gallery!=null) {
            %>
            <a href="portal?do=newgallery&id=<%=gallery.getId()%>" class="bold-link">Uppdatera</a>
            <a href="portal?do=removegallery&id=<%=gallery.getId()%>" class="bold-link" onClick="return confirm('Är du säker på att du vill ta bort detta galleri ?')">Ta bort</a>
            <br>
            <%
        }
    }
%>

