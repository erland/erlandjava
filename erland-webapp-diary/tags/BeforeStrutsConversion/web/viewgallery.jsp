<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.gallery.Gallery,
                                erland.webapp.diary.gallery.ViewGalleryInterface,
                                erland.webapp.diary.gallery.ViewGalleryEntriesInterface,
                                erland.webapp.diary.gallery.GalleryEntry,
                                erland.webapp.common.html.HTMLEncoder"%>


<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewGalleryInterface) {
        Gallery gallery = ((ViewGalleryInterface)cmd).getGallery();
        if(gallery!=null) {
            %>
            <a href="portal?do=newgallery&id=<%=gallery.getId()%>" class="bold-link">Uppdatera</a>
            <a href="portal?do=removegallery&id=<%=gallery.getId()%>" class="bold-link" onClick="return confirm('Är du säker på att du vill ta bort denna ?')">Ta bort</a>
            <a href="portal?do=newgalleryentry&gallery=<%=gallery.getId()%>" class="bold-link">Lägg till bild</a>
            <br>

            <table class="no-border">
            <tr><td colspan="3"><p class="title"><%=gallery.getTitle()%></p></td></tr>
            <tr><td colspan="3"><p class="normal"><%=HTMLEncoder.encode(gallery.getDescription())%></p></td></tr>
            <%
            if(cmd instanceof ViewGalleryEntriesInterface) {
                GalleryEntry[] entries = ((ViewGalleryEntriesInterface)cmd).getEntries();
                for (int i = 0; i < entries.length; i++) {
                    GalleryEntry entry = entries[i];
                    if(i%3==0) {
                        %>
                        <tr>
                        <%
                    }
                    %>
                    <td>
                    <a href="portal?do=newgalleryentry&gallery=<%=gallery.getId()%>&id=<%=entry.getId()%>" class="bold-link">Uppdatera</a>
                    <a href="portal?do=removegalleryentry&gallery=<%=gallery.getId()%>&id=<%=entry.getId()%>" class="bold-link" onClick="return confirm('Är du säker på att du vill ta bort denna ?')">Ta bort</a><br>
                    <a class="bold-link" href="<%=entry.getLink()%>" target="_blank" title="<%=entry.getDescription()!=null?entry.getDescription():""%>"><img src="<%=entry.getImage()%>" border="0" width="150"></img><br><div align="center"><%=entry.getTitle()!=null?entry.getTitle():""%></div></a>
                    </td>
                    <%
                    if(i%3==2 || i==entries.length-1) {
                        %>
                        </tr>
                        <%
                    }
                }
            }
            %>
            </table>
            <%
        }
    }
%>