<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.gallery.Gallery,
                                erland.webapp.diary.gallery.ViewGalleryInterface,
                                erland.webapp.diary.HTMLEncoder,
                                erland.webapp.diary.gallery.ViewGalleryEntriesInterface,
                                erland.webapp.diary.gallery.GalleryEntry,
                                erland.webapp.gallery.gallery.picture.ViewPicturesInterface,
                                erland.webapp.gallery.gallery.picture.Picture"%>


<%
    CommandInterface galleryCmd = (CommandInterface) request.getAttribute("gallerycmd");
    if(galleryCmd instanceof ViewGalleryInterface) {
        Gallery gallery = ((ViewGalleryInterface)galleryCmd).getGallery();
        if(gallery!=null) {
            %>
            <a href="portal?do=newgallery&id=<%=gallery.getId()%>" class="bold-link">Uppdatera</a>
            <a href="portal?do=removegallery&id=<%=gallery.getId()%>" class="bold-link" onClick="return confirm('Är du säker på att du vill ta bort denna ?')">Ta bort</a>
            <br>

            <table class="no-border">
            <tr><td colspan="3"><p class="title"><%=gallery.getTitle()%></p></td></tr>
            <tr><td colspan="3"><p class="normal"><%=HTMLEncoder.encode(gallery.getDescription())%></p></td></tr>
            <jsp:include page="viewgalleryexternalpicturepart.jsp">
                <jsp:param name="writable" value="true"/>
            </jsp:include>
            </table>
            <%
        }
    }
%>