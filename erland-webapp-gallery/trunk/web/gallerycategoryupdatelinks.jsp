<%@ page import="erland.webapp.gallery.act.gallery.ViewGalleryInterface,
                 erland.webapp.gallery.entity.gallery.category.Category,
                 erland.webapp.gallery.act.gallery.category.ViewCategoryInterface,
                 erland.webapp.common.CommandInterface,
                 erland.webapp.gallery.entity.gallery.Gallery,
                 erland.webapp.gallery.entity.gallery.GalleryInterface"%>
<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGalleryInterface) {
        GalleryInterface gallery = ((ViewGalleryInterface)cmd).getGallery();
        if(gallery!=null) {
            %>
            <a href="portal?do=newgallery&id=<%=gallery.getId()%>" class="bold-link">Uppdatera</a>
            <a href="portal?do=removegallery&id=<%=gallery.getId()%>" class="bold-link" onClick="return confirm('Är du säker på att du vill ta bort detta ?')">Ta bort</a>
            <%
            if(gallery.getReferencedGallery().intValue()==0) {
                %>
                <a href="portal?do=newpicture&gallery=<%=gallery.getId()%>" class="bold-link">Lägg till bild</a>
                <a href="portal?do=chooseimport&gallery=<%=gallery.getId()%>" class="bold-link">Importera</a>
                <%
            }
            if(cmd instanceof ViewGalleryInterface) {
                Category category = ((ViewCategoryInterface)cmd).getCategory();
                if(category!=null) {
                    %>
                    <a href="portal?do=updatecategory&gallery=<%=gallery.getId()%>&category=<%=category.getCategory()%>" class="bold-link">Uppdatera kategori</a>
                    <%
                }
            }
            %>
            <br>
            <%
        }
    }
%>

